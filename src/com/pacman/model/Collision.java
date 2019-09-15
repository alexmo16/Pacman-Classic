package com.pacman.model;

import java.awt.geom.Rectangle2D;

import com.pacman.model.objects.GameObject;
import com.pacman.model.objects.consumables.Consumable;
import com.pacman.model.objects.consumables.ConsumableVisitor;
import com.pacman.model.objects.consumables.Energizer;
import com.pacman.model.objects.consumables.PacDot;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.world.Level;

/**
 * 
 * Check the collisions between the different object in the game, pacman, the
 * walls and the gums
 *
 */
public class Collision
{

    private int[] authTiles;
    private Game game;

    public Collision(Game game)
    {
    	this.game = game;
        this.game = game;
    }

    private class ConsumableCollisionVisitor implements ConsumableVisitor
    {

        @Override
        public void visitEnergizer(Energizer energizer)
        {
            Level level = game.getCurrentLevel();
            level.getEnergizers().remove(energizer);
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
            game.getPacman().eat(consumable);
            game.getConsumables().remove(consumable);
        }
    }

    /**
     * method used to check if obj hits a wall or goes in the tunnel
     * 
     * @return "path" if there is no collision, "wall" when obj hits a wall, "void"
     *         when obj enters the tunnel
     */
    public String collisionWall(GameObject obj)
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

        else if (isAuth(xMin, yMin) & isAuth(xMin, yMax) & isAuth(xMax, yMin) & isAuth(xMax, yMax))
        {

            return "path";
        }

        return "wall";
    }
    
    
    public boolean middleOfATiles() {
    	  	
    	double x = game.getPacman().getX();
    	double y = game.getPacman().getY();
    	
    	if (  x == (int) game.getPacman().getX() + 0.05 && y == (int) game.getPacman().getY() + 0.05 ) {
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
    public boolean collisionObj(GameObject obj1, GameObject obj2)
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
    	
        if (middleOfATiles())
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

        game.getPacman().updatePosition(game.getNewDirection());
        game.getPacman().setDirection(game.getNewDirection());
        game.setNextTilesDirection(game.getNewDirection());
        game.getPacman().setCollision(false, game.getNewDirection());
    }

    /**
     * Check if pacman eats a Gum
     */
    public void checkConsumablesCollision()
    {

        if (middleOfATiles())
        {

            ConsumableCollisionVisitor visitor = new ConsumableCollisionVisitor();

            Level level = game.getCurrentLevel();
            double xPacman = game.getPacman().getX();
            double yPacman = game.getPacman().getY();

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
    

    /**
     * This method check if the maze's tiles at the coordinates given in parameters
     * is a walkable tiles
     * 
     * @return "true" if the the tile is walkable, "false" if the tile is not a
     *         walkable tiles
     */
    public boolean isAuth(int x, int y)
    {
        int[][] tiles = Level.getTiles();

        for (int i : authTiles)
        {
            if (tiles[x][y] == i)
            {
                return true;
            }
        }
        return false;
    }

    public void setAuthTiles(int[] auth)
    {
        authTiles = auth;
    }
    
    public boolean collisionGhost()
    {
    	Ghost ghost = getNearestGhost();
    	if (ghost != null)
    	{
    		double xCoord = ghost.getXCoord();
    		double yCoord = ghost.getYCoord();
    		Rectangle2D.Double hitboxGhost = new Rectangle2D.Double(xCoord, yCoord, 2,2);
    		Rectangle2D.Double hitboxPacman = new Rectangle2D.Double(game.getPacman().getXCoord(),game.getPacman().getYCoord(), 2, 2 );
    		
    		if(hitboxGhost.intersects(hitboxPacman))
    		{
    			return true;
    		}
    	}
    	return false;
    	
    }
    
    private Ghost getNearestGhost()
    {
    	double xPacman = game.getPacman().getHitBox().getCenterX();
    	double yPacman = game.getPacman().getHitBox().getCenterY();
    	
    	for (Entity entity : game.getEntities())
    	{
    	//Entity entity = game.getEntities().get(0);
    		double xEntity = entity.getHitBox().getCenterX();
    		double yEntity = entity.getHitBox().getCenterY();
    		if (xEntity != xPacman && yEntity != yPacman)
    		{
    			//System.out.println(Math.sqrt(Math.pow(xPacman-xEntity,2)));
    			//System.out.println(Math.sqrt(Math.pow(yPacman-yEntity,2)));
    			//System.out.println(entity);
    			//System.out.println(Math.sqrt(Math.pow(xPacman-xEntity,2) + Math.pow(yPacman-yEntity,2)));
    			if (Math.sqrt(Math.pow(xPacman-xEntity,2) + Math.pow(yPacman-yEntity,2)) <= 4)
    			{
    				return (Ghost) entity;
    			}
    		}
    			
    	}
    	return null;
    }
}
