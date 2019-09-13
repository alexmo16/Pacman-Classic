package com.pacman.model;

import com.pacman.model.objects.GameObject;
import com.pacman.utils.Settings;

/**
 * 
 * Check the collisions between the different object in the game, pacman, the walls and the gums
 *
 */
public abstract class Collision
{
    int[][] tiles = Settings.WORLD_DATA.getTiles();
    int mapH = Settings.WORLD_DATA.getHeight();
    int mapW = Settings.WORLD_DATA.getWidth();
    int[] authTiles = Settings.AUTH_TILES;
    
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
    	String checkWallCollision = Collision.collisionWall(game.getNextTilesPacman());
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
    	String collisionString = collisionWall(game.getNewDirectionPacman());
        if (collisionString == "void")
        {
        	game.getPacman().tunnel(game.getNewDirectionPacman().getDirection());
        	game.getPacman().setDirection(game.getNewDirectionPacman().getDirection());
        	game.getPacman().setCollision(false, game.getNewDirectionPacman().getDirection());
        }
        if (collisionString == "path")
        {
        	game.getPacman().updatePosition(game.getNewDirectionPacman().getDirection());
        	game.getPacman().setDirection(game.getNewDirectionPacman().getDirection());
        	game.getPacman().setCollision(false, game.getNewDirectionPacman().getDirection());
        } 
        else
        {
        	game.getPacman().setCollision(true, game.getNewDirectionPacman().getDirection());
        }
    }
    
    /**
     * Strategy if pacman goes through the tunnel
     */
    private void tunnelStrategy(Game game)
    {
    	game.getPacman().tunnel(game.getNextTilesPacman().getDirection());
    	game.getPacman().setCollision(false, game.getNewDirectionPacman().getDirection());
    }
    
    /**
     * Strategy if pacman moves forward
     */
    private void noWallStrategy(Game game)
    {
    	game.getPacman().updatePosition(game.getNewDirectionPacman().getDirection());
    	game.getPacman().setDirection(game.getNewDirectionPacman().getDirection());
        game.getNewDirectionPacman().setDirection(game.getNextTilesPacman().getDirection());
        game.getPacman().setCollision(false, game.getNewDirectionPacman().getDirection());
    }

    /**
     * This method check if the maze's tiles at the coordinates given in parameters is a walkable tiles
     * 
     * @return "true" if the the tile is walkable,
     * "false" if the tile is not a walkable tiles
     */
    public boolean isAuth(int x, int y)
    {
        for (int i : authTiles)
        {
            if (tiles[x][y] == i)
            {
                return true;
            }
        }
        return false;
    }
    
    public void setMap(int[][] map, int[] auth, int x,int y) 
    {
    	tiles = map;
    	authTiles = auth;
    	mapH = x;
    	mapW = y;
    }
}
