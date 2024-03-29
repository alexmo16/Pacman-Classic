package com.pacman.model.states;

import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import com.pacman.model.Game;
import com.pacman.model.objects.GameObject;
import com.pacman.model.objects.consumables.Consumable;
import com.pacman.model.objects.consumables.ConsumableVisitor;
import com.pacman.model.objects.consumables.Energizer;
import com.pacman.model.objects.consumables.PacDot;
import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.objects.entities.Ghost.Animation;
import com.pacman.model.objects.entities.Pacman;
import com.pacman.model.objects.entities.behaviours.BehaviourFactory;
import com.pacman.model.objects.entities.behaviours.IBehaviour;
import com.pacman.model.objects.entities.behaviours.IBehaviour.behavioursID;
import com.pacman.model.threads.TimerThread;
import com.pacman.model.world.Direction;
import com.pacman.model.world.GhostType;
import com.pacman.model.world.Level;
import com.pacman.utils.IObserver;
import com.pacman.utils.IPublisher.UpdateID;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros
 *          Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis
 *          Ryckebusch-rycl2501
 *
 */
public class PlayingState implements IGameState, IObserver
{
    private StatesName name = StatesName.PLAY;

    private Game game;
    private volatile boolean isPacmanDying = false;
    private int intermissionTime = 8; // seconds

    private String collision = null;
    private String collisionString = null;
    private String collisionConsumable = null;
    private String collisionGhost = null;
    
    private BehaviourFactory ghostBehaviourFactory = new BehaviourFactory();
    
    private class ConsumableCollisionVisitor implements ConsumableVisitor
    {

        @Override
        public void visitEnergizer(Energizer energizer)
        {
            Level level = game.getCurrentLevel();
            level.getEnergizers().remove(energizer);
            Pacman pacman = game.getPacman();
            if (!pacman.isInvincible())
        	{
        		pacman.resetEatenGhosts();
        	}
            game.activateEnergizer();
        }

        @Override
        public void visitPacDot(PacDot pacdot)
        {
            Level level = game.getCurrentLevel();
            level.getPacDots().remove(pacdot);
        }

        @Override
        public void visitDefault(Consumable consumable)
        {
            game.pacmanEatConsummable(consumable);
            game.getConsumables().remove(consumable);
        }
    }

    private LineListener deathSoundListener = new LineListener()
    {
        @Override
        public void update(LineEvent event)
        {
            if (event.getType() == LineEvent.Type.STOP)
            {
                game.setState(game.getPacman().getLives() == 0 ? game.getStopState() : game.getInitState());
                isPacmanDying = false;
            }
        }
    };

    public PlayingState(Game gm)
    {
        if (gm == null)
        {
            return;
        }

        game = gm;

        game.getPacman().registerObserver(this);

        game.getNewDirectionPacman().setHitBox(game.getPacman().getHitBox());
        game.getNextTilesPacman().setHitBox(game.getPacman().getHitBox());

        game.setNextTilesDirection(game.getPacman().getDirection());
        game.setNewDirection(game.getNextTilesDirection());

        game.getNewDirectionPacman().setAuthTiles(game.getCurrentLevel().getAuthTiles());
        game.getNextTilesPacman().setAuthTiles(game.getCurrentLevel().getAuthTiles());
    }

    @Override
    public void update()
    {
        if (isPacmanDying)
        {
            return;
        }

        Level level = game.getCurrentLevel();
        ArrayList<PacDot> pacdots = level.getPacDots();
        pacmanWin(pacdots);
        ghostLeavingSpawn();

        synchronized (game)
        {
            game.notifyPhysics();

            readAllQueues();
            
            collisionGhost();
            collisionConsumable();
            collisionPacman();
            corridorGhost();
        }
    }
    
    private void pacmanWin(ArrayList<PacDot> pacdots)
    {
        if (pacdots.size() == 0)
        {
            game.setPacmanWon(true);
            game.setState(game.getStopState());
        }
    }
    
    private void ghostLeavingSpawn()
    {
        if (game.getTimerThread() == null && !game.getPacman().isInvincible())
        {
            game.setTimerThread(new TimerThread(0.5));
            game.startTimerThread();
        }
        if (!game.getTimerThread().isAlive() && !game.getPacman().isInvincible() && game.getGhosts().size() != 0)
        {
            Random random = new Random();
            int randomInt = random.nextInt(game.getGhosts().size());

            if (!game.getGhosts().get(randomInt).getAlive())
            {
                ghostSpawn(game.getGhosts().get(randomInt));
                game.getGhosts().get(randomInt).setSpawning();
                game.getGhosts().get(randomInt).setAlive();
                game.setTimerThreadNull();
            }
        }
    }
    
    private void readAllQueues()
    {
        collision = game.readCollisionQueue();
        collisionString = game.readCollisionNextPacmanQueue();
        collisionConsumable = game.readCollisionConsumableQueue();
        collisionGhost = game.readCollisionGhostQueue();
    }
    
    private void collisionGhost()
    {
        if (collisionGhost != null && collisionGhost.equals("ghost"))
        {
            game.killPacman();
        } else if (collisionGhost != null && collisionGhost.equals("killGhost"))
        {
            Ghost ghost = game.readGhostQueue();
            if (ghost != null) 
            {
            	game.getPacman().eat(ghost);
                game.killGhost(ghost);
            }
        }
    }
    
    private void collisionConsumable()
    {
        if (collisionConsumable != null && collisionConsumable.equals("consumable"))
        {
            Consumable consumable = game.readConsumableQueue();
            if (consumable != null) 
            {
                ConsumableCollisionVisitor visitor = new ConsumableCollisionVisitor();
                consumable.accept(visitor);
            }
        }
    }
    
    private void collisionPacman()
    {
        if (collision != null && collision.equals("onewall"))
        {
            oneWallStrategy();
        }
        else if (collision != null && collision.equals("nowall"))
        {
            noWallStrategy();
        }
        else if (collision != null && collision.equals("tunnel"))
        {
            tunnelStrategy();
        }
    }

    private void corridorGhost()
    {

    	String stringBlinky = game.readStringBlinkyCorridorQueue();
    	String stringInky = game.readStringInkyCorridorQueue();
    	String stringPinky = game.readStringPinkyCorridorQueue();
    	String stringClyde = game.readStringClydeCorridorQueue();
    	String string = null;
    
    	for(Ghost ghost : game.getGhosts()) 
    	{
    		if (ghost.getType() == GhostType.BLINKY)
    		{
    			string = stringBlinky;
    			game.getStringBlinkyCorridorQueue().clear();
    		}
    		if (ghost.getType() == GhostType.INKY)
    		{
    			string = stringInky;
    			game.getStringInkyCorridorQueue().clear();
    		}
    		if (ghost.getType() == GhostType.PINKY)
    		{
    			string = stringPinky;
    			game.getStringPinkyCorridorQueue().clear();
    		}
    		if (ghost.getType() == GhostType.CLYDE)
    		{
    			string = stringClyde;
    			game.getStringClydeCorridorQueue().clear();
    		}
    		if(string != null)
        	{
    	    	if ( string.equals("corridor") && !ghost.getSameCorridor())
    	    	{
    	    		ghost.setSameCorridor(true);
    	    	}
    	    	else if (string.equals("notcorridor") && ghost.getSameCorridor())
    	    	{
    	    		ghost.setSameCorridor(false);
    	    	}

        	}
    	}    	
    	
    }
    
    public synchronized void setCollision(String string)
    {
        collision = string;
    }

    public synchronized void setCollisionString(String string)
    {
        collisionString = string;
    }

    private synchronized void oneWallStrategy()
    {

        if (collisionString == "void")
        {
            game.getPacman().tunnel(game.getNextTilesDirection());
            game.getPacman().setDirection(game.getNextTilesDirection());
            game.getPacman().setCollision(false, game.getNextTilesDirection());
        }
        if (collisionString == "path")
        {
            game.getPacman().updatePosition(game.getNextTilesDirection());
            game.getPacman().setDirection(game.getNextTilesDirection());
            game.getPacman().setCollision(false, game.getNextTilesDirection());
        } else
        {
            game.getPacman().setCollision(true, game.getNextTilesDirection());
            game.getPacman().setIsTravelling(false);
        }
    }

    private synchronized void noWallStrategy()
    {

        if (!game.getPacman().getIstravelling())
        {
            game.getPacman().updatePosition(game.getNewDirectionPacman().getDirection());
            game.getPacman().setDirection(game.getNewDirectionPacman().getDirection());
            game.setNextTilesDirection(game.getNewDirectionPacman().getDirection());
            game.getPacman().setCollision(false, game.getNewDirectionPacman().getDirection());
        } else
        {
            oneWallStrategy();
        }
    }

    private synchronized void tunnelStrategy()
    {
        game.getPacman().tunnel(game.getPacman().getDirection());
        game.getPacman().setCollision(false, game.getNewDirection());
    }

    @Override
    public StatesName getName()
    {
        return name;
    }

    /**
     * update of the observer
     */
    @Override
    public void updateObservers(UpdateID updateID)
    {
    	if (updateID != UpdateID.CHANGE_DIRECTION) return;
    	
    	Pacman pacman = game.getPacman();
        if (game.getNewDirection() == pacman.getDirection())
        {
            game.setNextTilesDirection(game.getNewDirection());
        }
        game.setNewDirection(pacman.getNextDirection());

    }

    public void killPacman()
    {
        game.setNewDirection(Direction.LEFT);
        game.setNextTilesDirection(Direction.LEFT);
        
        collision = null;
        collisionString = null;
        collisionConsumable = null;
        collisionGhost = null;

        game.setTimerThreadNull();
        isPacmanDying = true;
        game.stopMusic();
        game.getPacman().looseLive();
        game.playDeathSound(deathSoundListener);
    }

    public void ghostSpawn(GameObject obj)
    {
        Ghost ghost = new Ghost(obj.getHitBoxX(), obj.getHitBoxY(), ((Ghost) obj).getType());
        IBehaviour behaviour = ghostBehaviourFactory.createBehaviour(ghost, behavioursID.AMBUSH, game);
        ghost.setBehaviour(behaviour);
        
        ghost.setAuthTiles(game.getCurrentLevel().getAuthTilesGhost(), game.getCurrentLevel().getAuthTilesGhostRoom());
        ghost.updatePosition(ghost.getDirection());
    }

    public void activateEnergizer()
    {
        TimerThread intermissionTimer = game.getIntermissionThread();
        if (intermissionTimer == null || !intermissionTimer.isAlive())
        {
            game.setPacmanXBlinky(0);
    		game.setPacmanYBlinky(0);
    		
    		game.setPacmanXInky(0);
    		game.setPacmanYInky(0);
    		
        	Pacman pacman = game.getPacman();
            pacman.setInvincibility(true);
            intermissionTimer = new TimerThread(intermissionTime);
            intermissionTimer.setEndCallback(() ->
            {
                endEnergizer();
                
            });
            intermissionTimer.setCallbackAtTime(intermissionTime * 1000 - 3000, () ->
            {
                setGhostsAnimation(Animation.BLINKING);
            });
            intermissionTimer.start();
            setGhostsAnimation(Animation.FRIGHTENED);
            game.setIntermissionThread(intermissionTimer);
        }
        else
        {
        	intermissionTimer.stopThread();
        	try 
        	{
				intermissionTimer.join(500);
				if (intermissionTimer.isAlive())
				{
					intermissionTimer.interrupt();
					// If there is an interruption, we call manually endEnergizer, because the thread will not call it.
					endEnergizer();
				}
			} catch (InterruptedException e) 
        	{
				e.printStackTrace();
			}
        	activateEnergizer();
        }        	
    }

    private void endEnergizer()
    {
    	Pacman pacman = game.getPacman();
    	if (pacman == null) return;
    	pacman.setInvincibility(false);
        setGhostsAnimation(Animation.MOVING);
        game.playInGameMusic();
    }

    private void setGhostsAnimation(Animation animation)
    {
        ArrayList<Ghost> ghosts = game.getGhosts();
        for (Ghost ghost : ghosts)
        {
            if (ghost.getAlive())
            {
                ghost.setCurrentAnimation(animation);
            }
        }
    }
}
