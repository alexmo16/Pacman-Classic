package com.pacman.model;

import com.pacman.model.objects.GameObject;
import com.pacman.model.objects.consumables.Consumable;
import com.pacman.model.objects.consumables.ConsumableVisitor;
import com.pacman.model.objects.consumables.Energizer;
import com.pacman.model.objects.consumables.PacDot;
import com.pacman.model.world.Level;


/**
 * 
 * Check the collisions between the different object in the game, pacman, the walls and the gums
 *
 */
public class Collision
{

    private int[] authTiles;
    private Game game;
    
    public Collision(Game game)
    {
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
     * @return "path" if there is no collision,
     * "wall" when obj hits a wall,
     * "void" when obj enters the tunnel
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

    /**
     * This method is used to check if the hitbox of obj1 intersect the hit box of obj2
     * Used to check the collisions between pacman and the other objects of the game, the gums, the pacgums and the ghosts
     * 
     * @return "true" if there is a collision between the 2 objects,
     * "false" if there is no collision.
     */
    public boolean collisionObj(GameObject obj1, GameObject obj2)
    {
        return obj1.getHitBox().intersects(obj2.getHitBox());
    }
    
    
    /**
     * Redirect to the correct strategy
     * @param collisionString
     */
    public void executeWallStrategy( Game game)
    {

    	String checkWallCollision = collisionWall(game.getNewDirectionPacman());
    	if ( checkWallCollision == "void" )
    	{
    		tunnelStrategy(game);
    	}
    	else if ( checkWallCollision == "wall" )
    	{
    		oneWallStrategy(game);
    	}
    	else
    		
    		
    	{
    		noWallStrategy(game);
    	}
    }
    
    /**
     * Strategy if pacman hits a wall.
     */
    private void oneWallStrategy(Game game)
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
        } 
        else
        {
        	game.getPacman().setCollision(true, game.getNextTilesDirection());
        }
    }
    
    /**
     * Strategy if pacman goes through the tunnel
     */
    private void tunnelStrategy(Game game)
    {
    	game.getPacman().tunnel(game.getPacman().getDirection());
    	game.getPacman().setCollision(false, game.getNewDirection());
    }
    
    /**
     * Strategy if pacman moves forward
     */
    private void noWallStrategy(Game game)
    {

    	game.getPacman().updatePosition(game.getNewDirection());
    	game.getPacman().setDirection(game.getNewDirection());
        game.setNextTilesDirection(game.getNewDirection());
        game.getPacman().setCollision(false, game.getNewDirection());
    }
    
    
    /**
     * Check if pacman eats a Gum
     */
    public void checkConsumablesCollision(Game game)
    {
    	ConsumableCollisionVisitor visitor = new ConsumableCollisionVisitor();
        for (Consumable consumable : game.getConsumables())
        {
            if (collisionObj(game.getPacman(), consumable))
            {
            	consumable.accept(visitor);
                break;
            }
        }
    }

    /**
     * This method check if the maze's tiles at the coordinates given in parameters is a walkable tiles
     * 
     * @return "true" if the the tile is walkable,
     * "false" if the tile is not a walkable tiles
     */
    public boolean isAuth(int x, int y)
    {
    	int [][] tiles = Level.getTiles();

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
}
