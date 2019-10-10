package com.pacman.model.threads;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.pacman.model.Game;
import com.pacman.model.objects.GameObject;
import com.pacman.model.objects.consumables.Consumable;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.objects.entities.Pacman;
import com.pacman.model.objects.entities.behaviours.BehaviourFactory;
import com.pacman.model.objects.entities.behaviours.IBehaviour;
import com.pacman.model.objects.entities.behaviours.IBehaviour.behavioursID;
import com.pacman.model.world.Direction;
import com.pacman.model.world.GhostType;
import com.pacman.model.world.Level;

/**
 * PHYSICTHREAD = CREATED, CREATED = (start ->RUNNING), RUNNING = (wait
 * ->WAITING |stop ->TERMINATED), VERIFY_COLLISIONS = (checkCollisions
 * ->SENDMESSAGE), SENDMESSAGE = (sendMessages->RUNNING), WAITING =
 * (notify->VERIFY_COLLISIONS), TERMINATED = STOP.
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros
 *          Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis
 *          Ryckebusch-rycl2501
 *
 */
public class PhysicsThread extends Thread
{

    private volatile int[] authTiles;
    private Game game;

    private volatile boolean isRunning;
    private BehaviourFactory ghostBehaviourFactory = new BehaviourFactory();

    public PhysicsThread(Game game)
    {
        this.game = game;
        this.isRunning = false;
    }

    /**
     * method used to check if obj hits a wall or goes in the tunnel
     * 
     * @return "path" if there is no collision, "wall" when obj hits a wall, "void"
     *         when obj enters the tunnel
     */
    private String collisionWall(Entity obj)
    {

        int xMin = (int) obj.getHitBox().getMinX();

        int yMin = (int) obj.getHitBox().getMinY();

        int xMax = (int) obj.getHitBox().getMaxX();
        int yMax = (int) obj.getHitBox().getMaxY();

        int mapW = Level.getWidth();
        int mapH = Level.getHeight();

        if ((xMin <= 0 & xMax <= 0) || (xMin >= mapW - 1 & xMax >= mapW - 1) || (yMin <= 0 & yMax <= 0)
                || (yMin >= mapH - 1 & yMax >= mapH - 1))
        {
            return "void";
        }

        else if (isAuth(xMin, yMin, obj) & isAuth(xMin, yMax, obj) & isAuth(xMax, yMin, obj) & isAuth(xMax, yMax, obj))
        {

            return "path";
        }

        return "wall";
    }

    private boolean middleOfATiles(Entity entity)
    {

        double x = entity.getHitBoxX();
        double y = entity.getHitBoxY();

        if (x == (int) x + 0.05 && y == (int) y + 0.05)
        {
            return true;
            // We need to have a margin of error due to the +20% of speed when invincible
            // With this boost of speed, we will skip some Tiles if we don't use a margin
        } else if (x >= (int) x + 0.01 && x <= (int) x + 0.09 && y >= (int) y + 0.01 && y <= (int) y + 0.09)
        {
            entity.setPosition((int) x + 0.05, (int) y + 0.05);
            return true;
        }

        return false;
    }

    /**
     * This method is used to check if the hitbox of obj1 intersect the hit box of
     * obj2 Used to check the collisions between pacman and the other objects of the
     * game, the gums, the pacgums and the ghosts
     * 
     * @return "true" if there is a collision between the 2 objects, "false" if
     *         there is no collision.
     */
    private boolean collisionObj(GameObject obj1, GameObject obj2)
    {
        return obj1.getHitBox().intersects(obj2.getHitBox());
    }

    /**
     * Redirect to the correct strategy
     * 
     * @param collisionString
     */
    public void executeWallStrategy()
    {
        Pacman pacman = game.getPacman();
        if (middleOfATiles(pacman))
        {
            String checkWallCollision = collisionWall(game.getNewDirectionPacman());
            if (checkWallCollision == "void")
            {
                game.addCollisionQueue("tunnel");
            } else if (checkWallCollision == "wall")
            {
                game.addCollisionQueue("onewall");
                game.addCollisionNextPacmanQueue(collisionWall(game.getNextTilesPacman()));
            } else
            {
                game.addCollisionQueue("nowall");
                game.addCollisionNextPacmanQueue(collisionWall(game.getNextTilesPacman()));
            }
        } else
        {
            game.addCollisionQueue("onewall");
            game.addCollisionNextPacmanQueue(collisionWall(game.getNextTilesPacman()));
        }

    }

    /**
     * Check if pacman eats a Gum
     */
    public void checkConsumablesCollision()
    {

        if (middleOfATiles(game.getPacman()))
        {
            Level level = game.getCurrentLevel();
            double xPacman = game.getPacman().getHitBoxX();
            double yPacman = game.getPacman().getHitBoxY();

            Consumable consumable = level.getConsumableAtCoords(xPacman, yPacman);

            if (consumable != null)
            {
                if (collisionObj(game.getPacman(), consumable))
                {
                    game.addCollisionConsumableQueue("consumable");
                    game.addConsumableQueue(consumable);
                }
            }

        }

    }

    public void ghostSpawn(GameObject obj)
    {

        Ghost ghost = new Ghost(obj.getHitBoxX(), obj.getHitBoxY(), ((Ghost) obj).getType());
        IBehaviour behaviour;
        
        if ( !game.getPacman().isInvincible() )
        {
        	 behaviour = ghostBehaviourFactory.createBehaviour(ghost, ((Ghost)obj).getBehaviourID(), game);
        } else 
        {
        	behaviour = ghostBehaviourFactory.createBehaviour(ghost, behavioursID.RANDOM, game);
        }
        
        ghost.setBehaviour(behaviour);
        
        ghost.setAuthTiles(game.getCurrentLevel().getAuthTilesGhost(), game.getCurrentLevel().getAuthTilesGhostRoom());
        ghost.updatePosition(ghost.getDirection());

        if (!((Ghost) obj).getInTheGate() && collisionWall(ghost) != "path")
        {
            if (ghost.getType() == GhostType.BLINKY || ghost.getType() == GhostType.PINKY)
            {
                ((Ghost) obj).updatePosition(Direction.RIGHT);
            } else
            {
                ((Ghost) obj).updatePosition(Direction.LEFT);
            }
        } else if (((Ghost) obj).getInTheGate() && collisionWall(ghost) != "path")
        {
            ((Ghost) obj).setAlive();
            ((Ghost) obj).setNotInTheGate();
            ((Ghost) obj).setNotSpawning();
        } else
        {
            ((Ghost) obj).setInTheGate();
            ((Ghost) obj).updatePosition(Direction.UP);
        }

    }

    public void ghostMove(Entity obj)
    {

        Ghost ghost = new Ghost(((Ghost) obj).getHitBoxX(), ((Ghost) obj).getHitBoxY(), ((Ghost) obj).getType());
        ghost.setSameCorridor(((Ghost)obj).getSameCorridor());
        IBehaviour behaviour;
        
        if ( !game.getPacman().isInvincible() )
        {
        	 behaviour = ghostBehaviourFactory.createBehaviour(ghost, ((Ghost)obj).getBehaviourID(), game);
        } else 
        {
        	behaviour = ghostBehaviourFactory.createBehaviour(ghost, behavioursID.FEAR, game);
        }
        
        ghost.setBehaviour(behaviour);
        
        ghost.setAlive();
        ghost.setAuthTiles(game.getCurrentLevel().getAuthTilesGhost(), game.getCurrentLevel().getAuthTilesGhostRoom());
        ghost.setDirection(((Ghost) obj).getDirection());
        ghost.getNewDirection();
        ghost.updatePosition(ghost.getDirection());
        while (collisionWall(ghost) != "path")
        {
        	ghost.getHitBox().setRect(obj.getHitBox());
        	ghost.setDirection(((Ghost) obj).getDirection());
        	ghost.getNewDirection();
            ghost.updatePosition(ghost.getDirection());

        }
        obj.setDirection(ghost.getDirection());
        obj.updatePosition(ghost.getDirection());

    }

    /**
     * This method check if the maze's tiles at the coordinates given in parameters
     * is a walkable tiles
     * 
     * @return "true" if the the tile is walkable, "false" if the tile is not a
     *         walkable tiles
     */
    private boolean isAuth(int x, int y, Entity entity)
    {
        int[][] tiles = Level.getTiles();

        int[] authTiles = entity.getAuthTiles();
        if (authTiles == null)
        {
            authTiles = this.authTiles;
        }
        for (int i : authTiles)
        {
            if (tiles[x][y] == i)
            {
                if (i == 70 && (int) game.getPacman().getHitBoxX() == x && (int) game.getPacman().getHitBoxY() == y)
                {
                    game.getPacman().setIsTravelling(true);

                }
                return true;
            }
        }
        return false;
    }

    public synchronized void setAuthTiles(int[] auth)
    {
        authTiles = auth;
    }

    public synchronized Ghost collisionGhost()
    {
        double xPacman = game.getPacman().getHitBox().getCenterX();
        double yPacman = game.getPacman().getHitBox().getCenterY();
        Ghost ghost = getNearestGhost(xPacman, yPacman, game.getEntities());
        if (ghost.getAlive() == true)
        {
            double xCoord = ghost.getPosition().x;
            double yCoord = ghost.getPosition().y;
            Rectangle2D.Double hitboxGhost = new Rectangle2D.Double(xCoord, yCoord, 2, 2);
            Rectangle2D.Double hitboxPacman = new Rectangle2D.Double(game.getPacman().getPosition().x,
                    game.getPacman().getPosition().y, 2, 2);

            if (hitboxPacman.intersects(hitboxGhost))
            {
                Rectangle2D hitboxCollision = hitboxPacman.createIntersection(hitboxGhost);
                if ((hitboxCollision.getHeight()
                        * hitboxCollision.getWidth()) > (hitboxPacman.getHeight() * hitboxPacman.getWidth()) / 5)
                {
                    return ghost;
                }
            }
        }
        return null;

    }

    private Ghost getNearestGhost(double x, double y, ArrayList<Entity> entities)
    {

        for (Entity entity : entities)
        {
            if (entity instanceof Ghost)
            {
                double xEntity = entity.getHitBox().getCenterX();
                double yEntity = entity.getHitBox().getCenterY();

                if (Math.sqrt(Math.pow(x - xEntity, 2) + Math.pow(y - yEntity, 2)) <= 4 && ((Ghost) entity).getAlive())
                {
                    return (Ghost) entity;

                }
            }

        }
        Ghost ghost = new Ghost(1.0, 1.0, GhostType.BLINKY);
        IBehaviour behaviour = ghostBehaviourFactory.createBehaviour(ghost, behavioursID.RANDOM, game);
        ghost.setBehaviour(behaviour);
        return ghost;
    }

    public synchronized void preparePacman()
    {
        game.getNewDirectionPacman().getHitBox().setRect(game.getPacman().getHitBox());
        game.getNextTilesPacman().getHitBox().setRect(game.getPacman().getHitBox());
        game.getNextTilesPacman().updatePosition(game.getNextTilesDirection());

        game.getNewDirectionPacman().setDirection(game.getNewDirection());
        game.getNewDirectionPacman().updatePosition(game.getNewDirectionPacman().getDirection());
    }
    
    private synchronized void isSameCorridor(Ghost ghost)
    {
 

		double coordGhostX = ghost.getHitBoxX();
		double coordGhostY = ghost.getHitBoxY();
		double coordPacmanX = game.getPacman().getHitBoxX();
		double coordPacmanY = game.getPacman().getHitBoxY();
		double coordMin = 0;
		double coordMax = 0;
		
		boolean isCorridor = false;
		boolean isX = true;
		if (coordGhostX + 0.05 == coordPacmanX)
		{
			
			isCorridor = true;
			isX = true;
			
		}
		else if (coordGhostY + 0.05 == coordPacmanY)
		{
			isCorridor = true;
			isX = false;
		}
		if(isCorridor)
		{
			if (!isX)
			{
				if(coordGhostX < coordPacmanX)
				{
					coordMin = coordGhostX;
					coordMax = coordPacmanX;
				}
				else if(coordGhostX > coordPacmanX)
				{
					coordMin = coordPacmanX;
					coordMax = coordGhostX;
				}
				
			}
			else
			{
				if(coordGhostY < coordPacmanY)
				{
					coordMin = coordGhostY;
					coordMax = coordPacmanY;

				}
				else if (coordGhostY > coordPacmanY)
				{
					coordMin = coordPacmanY;
					coordMax = coordGhostY;

				}
    			
			}
			for(int i =  (int)coordMin ; i <  (int)coordMax; i++ )
			{
				
				
				if ((!isX && !isAuth( i, (int) coordGhostY, ghost)) || (isX && !isAuth( (int)coordGhostX,  i, ghost)))
				{
					sendCorridorGhost("notcorridor", ghost);
					return;
					
				}
				
			}
			sendCorridorGhost("corridor", ghost);
			return;
			
		}
		else 
		{
			sendCorridorGhost("notcorridor", ghost);
			return;
		}
    	
    }
    
    private void sendCorridorGhost(String string, Ghost ghost)
    {
    	if(ghost.getType() == GhostType.BLINKY)
    	{
    		game.addStringBlinkyCorridorQueue(string);
    	}

    	else if(ghost.getType() == GhostType.INKY)
    	{
    		game.addStringInkyCorridorQueue(string);
    	}
    	else if(ghost.getType() == GhostType.PINKY)
    	{
    		game.addStringPinkyCorridorQueue(string);
    	}
    	else if(ghost.getType() == GhostType.CLYDE)
    	{
    		game.addStringClydeCorridorQueue(string);
    	}
    }

    @Override
    public void run()
    {
        this.isRunning = true;

        System.out.println("Start: Physics Thread");
        while (isRunning)
        {
            synchronized (game)
            {
                try
                {
                    game.wait();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            
            ghostBehaviour();
            preparePacman();
            sendCollisionGhost();

            // Strategy pattern for wall collisions.

            checkConsumablesCollision();
            executeWallStrategy();
            for(Ghost ghost : game.getGhosts())
            {
            	isSameCorridor(ghost);
            }
        }
        System.out.println("Stop: Physics Thread");
    }
    
    private void ghostBehaviour()
    {
        for (Entity entity : game.getEntities())
        {
            if (entity instanceof Ghost)
            {
                if (((Ghost) entity).getAlive() && !((Ghost) entity).getSpawning())
                {
                    ghostMove(entity);

                } else if (((Ghost) entity).getSpawning())
                {
                    ghostSpawn(entity);
                }
            }
        }
    }
    
    private void sendCollisionGhost()
    {
        Ghost collisionGhost = collisionGhost();

        if (collisionGhost != null && !game.getPacman().isInvincible() && collisionGhost.getAlive())
        {
            game.addCollisionGhostQueue("ghost");
        } else if (collisionGhost != null && game.getPacman().isInvincible())
        {
            game.addCollisionGhostQueue("killGhost");
            game.addGhostQueue(collisionGhost);
        }
    }
    

    public void stopThread()
    {
        this.isRunning = false;
    }

}
