package com.pacman.engine;

import com.pacman.engine.objects.GameObject;

/**
 * 
 * Check the collisions between the different object in the game, pacman, the walls and the gums
 *
 */
public abstract class CollisionManager
{
    static int[][] map = Engine.getMap();
    static int mapH = Engine.getHeight();
    static int mapW = Engine.getWidth();
    static int[] authTiles;
    static ISettings settings;

    /**
     * method used to check if obj hits a wall or goes in the tunnel
     * 
     * @return "path" if there is no collision
     * @return "wall" when obj hits a wall
     * @return "void" when obj enters the tunnel
     */
    public static String collisionWall(GameObject obj)
    {
    	int xMin = (int) obj.getRectangle().getMinX(); 
        int yMin = (int) obj.getRectangle().getMinY();

        int xMax = (int) obj.getRectangle().getMaxX(); 
        int yMax = (int) obj.getRectangle().getMaxY();

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
     * return "true" if there is a collision between the 2 objects
     * return "false" if there is no collision.
     */
    public static boolean collisionObj(GameObject obj1, GameObject obj2)
    {
        return obj1.getRectangle().intersects(obj2.getRectangle());
    }

    /**
     * This method check if the maze's tiles at the coordinates given in parameters is a walkable tiles
     * 
     * return "true" if the the tile is walkable
     * return "false" if the tile is not a walkable tiles
     */
    private static boolean isAuth(int x, int y)
    {
        for (int i : authTiles)
        {
            if (map[x][y] == i)
            {
                return true;
            }
        }
        return false;
    }

    public static void setSettings(ISettings settings)
    {
        CollisionManager.settings = settings;
        authTiles = settings.getAuthTiles();
    }
}
