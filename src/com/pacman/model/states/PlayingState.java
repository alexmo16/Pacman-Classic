package com.pacman.model.states;

import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import com.pacman.model.Game;
import com.pacman.model.objects.GameObject;
import com.pacman.model.objects.consumables.PacDot;
import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.objects.entities.Ghost.Animation;
import com.pacman.model.threads.TimerThread;
import com.pacman.model.world.Direction;
import com.pacman.model.world.Level;
import com.pacman.utils.IObserver;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class PlayingState implements IGameState, IObserver<Direction>
{
    private StatesName name = StatesName.PLAY;

    private Game game;
    private volatile boolean isPacmanDying = false;
    private int intermissionTime = 8; // seconds
    
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
        if (pacdots.size() == 0)
        {
            game.setPacmanWon(true);
            game.setState(game.getStopState());
        }

        if (game.getTimerThread() == null && !game.getPacman().isInvincible())
        {
            game.setTimerThread(new TimerThread(1));
            game.startTimerThread();
        }

        if (!game.getTimerThread().isAlive() && !game.getPacman().isInvincible())
        {
        	 Random random = new Random();
             int randomInt = random.nextInt(4);

             if (!((Ghost) game.getEntities().get(randomInt)).getAlive())
             {
                 ghostSpawn(((Ghost) game.getEntities().get(randomInt)));
                 ((Ghost) game.getEntities().get(randomInt)).setSpawning();
                 ((Ghost) game.getEntities().get(randomInt)).setAlive();
                 game.setTimerThreadNull();
             }
        }
        
        game.notifyPhysics();
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
    public void update(Direction d)
    {

        if (game.getNewDirection() == game.getPacman().getDirection())
        {
            game.setNextTilesDirection(game.getNewDirection());
        }
        game.setNewDirection(d);

    }

    public void killPacman()
    {
    	game.setTimerThreadNull();
        isPacmanDying = true;
        game.stopMusic();
        game.getPacman().looseLive();
        game.playDeathSound(deathSoundListener);
    }
    
	public void ghostSpawn(GameObject obj)
	{
		Ghost g2 = new Ghost(obj.getHitBoxX(), obj.getHitBoxY(), ((Ghost) obj).getType());
		g2.setAuthTiles(game.getCurrentLevel().getAuthTilesGhost(), game.getCurrentLevel().getAuthTilesGhostRoom());
		g2.updatePosition(g2.getDirection());
	}
	
	public void activateEnergizer()
	{
		TimerThread intermissionTimer = game.getIntermissionThread();
		if (intermissionTimer == null || !intermissionTimer.isAlive())
		{
			game.getPacman().setInvincibility(true);
			intermissionTimer = new TimerThread(intermissionTime);
			intermissionTimer.setEndCallback(() -> { endEnergizer(); });
			intermissionTimer.setCallbackAtTime(intermissionTime * 1000 - 3000, () -> { setGhostsAnimation(Animation.BLINKING); });
			intermissionTimer.start();
			setGhostsAnimation(Animation.FRIGHTENED);
			game.setIntermissionThread(intermissionTimer);
		}
	}
	
	private void endEnergizer()
	{
		game.getPacman().setInvincibility(false);
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
