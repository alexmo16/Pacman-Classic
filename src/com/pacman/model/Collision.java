package com.pacman.model;

import com.pacman.model.objects.GameObject;
import com.pacman.model.world.Level;

/**
 * 
 * Check the collisions between the different object in the game, pacman, the walls and the gums
 *
 */
public abstract class Collision
{
    static int[] authTiles;

    /**
     * method used to check if obj hits a wall or goes in the tunnel
     * 
     * @return "path" if there is no collision,
     * "wall" when obj hits a wall,
     * "void" when obj enters the tunnel
     */
    public static String collisionWall(GameObject obj)
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
    public static boolean collisionObj(GameObject obj1, GameObject obj2)
    {
        return obj1.getHitBox().intersects(obj2.getHitBox());
    }

    /**
     * This method check if the maze's tiles at the coordinates given in parameters is a walkable tiles
     * 
     * @return "true" if the the tile is walkable,
     * "false" if the tile is not a walkable tiles
     */
    public static boolean isAuth(int x, int y)
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
    
    public static void setAuthTiles(int[] auth)
    {
    	authTiles = auth;
    }
}
