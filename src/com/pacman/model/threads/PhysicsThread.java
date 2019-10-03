package com.pacman.model.threads;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.pacman.model.Game;
import com.pacman.model.objects.GameObject;
import com.pacman.model.objects.consumables.Consumable;
import com.pacman.model.objects.consumables.ConsumableVisitor;
import com.pacman.model.objects.consumables.Energizer;
import com.pacman.model.objects.consumables.PacDot;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.objects.entities.Pacman;
import com.pacman.model.world.Direction;
import com.pacman.model.world.GhostType;
import com.pacman.model.world.Level;

/**
 * PHYSICTHREAD  = CREATED,
 * CREATED      = (start           						  ->RUNNING),
 * RUNNING      = (wait           							  ->WAITING
 *                |stop              						  ->TERMINATED),         
 * VERIFY_COLLISIONS = (checkCollisions                      ->SENDMESSAGE),
 * SENDMESSAGE = (sendMessages->RUNNING),
 * WAITING = (notify->VERIFY_COLLISIONS),
 * TERMINATED   = STOP.
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class PhysicsThread extends Thread
{

	private volatile int[] authTiles;
	private Game game;

	private volatile boolean isRunning;

	public PhysicsThread(Game game)
	{
		this.game = game;
		this.isRunning = false;
	}

	private class ConsumableCollisionVisitor implements ConsumableVisitor
	{

		@Override
		public void visitEnergizer(Energizer energizer)
		{
			Level level = game.getCurrentLevel();
			level.getEnergizers().remove(energizer);
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
		} else if (x >= (int) x + 0.01 && x <= (int) x + 0.09
				&& y >= (int) y + 0.01 && y <= (int) y + 0.09 )
		{
			entity.setPosition((int)x +0.05, (int)y +0.05);
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
				tunnelStrategy();
			} else if (checkWallCollision == "wall")
			{
				oneWallStrategy();
			} else
			{
				noWallStrategy();
			}
		} else
		{
			oneWallStrategy();
		}

	}

	/**
	 * Strategy if pacman hits a wall.
	 */
	private void oneWallStrategy()
	{

		String collisionString = collisionWall(game.getNextTilesPacman());
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

	/**
	 * Strategy if pacman goes through the tunnel
	 */
	private void tunnelStrategy()
	{
		game.getPacman().tunnel(game.getPacman().getDirection());
		game.getPacman().setCollision(false, game.getNewDirection());
	}

	/**
	 * Strategy if pacman moves forward
	 */
	private void noWallStrategy()
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

	/**
	 * Check if pacman eats a Gum
	 */
	public void checkConsumablesCollision()
	{

		if (middleOfATiles(game.getPacman()))
		{

			ConsumableCollisionVisitor visitor = new ConsumableCollisionVisitor();

			Level level = game.getCurrentLevel();
			double xPacman = game.getPacman().getHitBoxX();
			double yPacman = game.getPacman().getHitBoxY();

			Consumable consumable = level.getConsumableAtCoords(xPacman, yPacman);

			if (consumable != null)
			{
				if (collisionObj(game.getPacman(), consumable))
				{
					consumable.accept(visitor);
				}
			}

		}

	}

	public void ghostSpawn(GameObject obj)
	{

		Ghost g2 = new Ghost(obj.getHitBoxX(), obj.getHitBoxY(), ((Ghost) obj).getType());
		g2.setAuthTiles(game.getCurrentLevel().getAuthTilesGhost(), game.getCurrentLevel().getAuthTilesGhostRoom());
		g2.updatePosition(g2.getDirection());

		if (!((Ghost) obj).getInTheGate() && collisionWall(g2) != "path")
		{
			if (g2.getType() == GhostType.BLINKY || g2.getType() == GhostType.PINKY)
			{
				((Ghost) obj).updatePosition(Direction.RIGHT);
			} else
			{
				((Ghost) obj).updatePosition(Direction.LEFT);
			}
		} else if (((Ghost) obj).getInTheGate() && collisionWall(g2) != "path")
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

		Ghost g2 = new Ghost(((Ghost) obj).getHitBoxX(), ((Ghost) obj).getHitBoxY(), ((Ghost) obj).getType());
		g2.setAlive();
		g2.setAuthTiles(game.getCurrentLevel().getAuthTilesGhost(), game.getCurrentLevel().getAuthTilesGhostRoom());
		g2.setDirection(((Ghost) obj).getDirection());
		g2.getNewDirection();
		g2.updatePosition(g2.getDirection());
		while (collisionWall(g2) != "path")
		{
			g2.getHitBox().setRect(obj.getHitBox());
			g2.setDirection(((Ghost) obj).getDirection());
			g2.getNewDirection();
			g2.updatePosition(g2.getDirection());

		}
		obj.setDirection(g2.getDirection());
		obj.updatePosition(g2.getDirection());

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

	public Ghost collisionGhost()
    {
    	double xPacman = game.getPacman().getHitBox().getCenterX();
    	double yPacman = game.getPacman().getHitBox().getCenterY();
        Ghost ghost = getNearestGhost(xPacman, yPacman, game.getEntities());
        if (ghost.getAlive() == true)
        {
            double xCoord = ghost.getPosition().x;
            double yCoord = ghost.getPosition().y;
            Rectangle2D.Double hitboxGhost = new Rectangle2D.Double(xCoord, yCoord, 2, 2);
            Rectangle2D.Double hitboxPacman = new Rectangle2D.Double(game.getPacman().getPosition().x,game.getPacman().getPosition().y, 2, 2 );


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

        for (Entity entity : entities )
        {
        	if (entity instanceof Ghost)
        	{
        		double xEntity = entity.getHitBox().getCenterX();
                double yEntity = entity.getHitBox().getCenterY();

                if (Math.sqrt(Math.pow(x - xEntity, 2) + Math.pow(y - yEntity, 2)) <= 4
                        && ((Ghost) entity).getAlive())
                {
                    return (Ghost) entity;
                
                }
            }

        }
        return new Ghost(1.0, 1.0, GhostType.BLINKY);
    }
    
    public synchronized void preparePacman()
    {
        game.getNewDirectionPacman().getHitBox().setRect(game.getPacman().getHitBox());
        game.getNextTilesPacman().getHitBox().setRect(game.getPacman().getHitBox());
        game.getNextTilesPacman().updatePosition(game.getNextTilesDirection());

        game.getNewDirectionPacman().setDirection(game.getNewDirection());
        game.getNewDirectionPacman().updatePosition(game.getNewDirectionPacman().getDirection());
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
			    preparePacman();
			    
			    Ghost collisionGhost = collisionGhost();
			    
				if (collisionGhost != null && !game.getPacman().isInvincible())
				{
					game.killPacman();
				}
				else if (collisionGhost != null && game.getPacman().isInvincible())
				{
					game.killGhost(collisionGhost);
				}

				// Strategy pattern for wall collisions.

				checkConsumablesCollision();
				executeWallStrategy();
		}
		System.out.println("Stop: Physics Thread");
	}

	public void stopThread()
	{
		this.isRunning = false;
	}

}
