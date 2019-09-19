package com.pacman.model.objects;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;

import com.pacman.model.world.Direction;
import com.pacman.model.world.GhostType;
import com.pacman.model.world.Sprite;
import com.pacman.utils.SpriteUtils;

/**
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 * This class clips every object in the pacman tiles sheet, and stores them in multiple sprites.
 */
public final class Sprites
{	
	private final static String filePath = System.getProperty("user.dir") + File.separator + "assets" + File.separator + "pacmanTiles.png";
	private final static int tileSize = 48;
	
    private final static Image tilesSheet = SpriteUtils.getSpritesSheetImage(filePath);
    private final static int[][] tiles = SpriteUtils.getSpritesCoordsFromSheet(tilesSheet, tileSize);
    
    private final static HashMap<Integer, Sprite> walls = new HashMap<Integer, Sprite>();
    private final static Sprite pacDot = new Sprite(tiles[12][0], tiles[12][1], tileSize);;
    private final static HashMap<Integer, Sprite> energizer = new HashMap<Integer, Sprite>();
    private final static HashMap<Character, Sprite> characters = new HashMap<Character, Sprite>();
    private final static HashMap<Integer, Sprite> points = new HashMap<Integer, Sprite>();
    private final static Sprite title = new Sprite(tiles[72][0], tiles[72][1], 8 * tileSize, 2 * tileSize);
    private final static HashMap<Direction, HashMap<Integer, Sprite>> pacmanMovement = new HashMap<Direction, HashMap<Integer, Sprite>>();
    private final static HashMap<Integer, Sprite> pacmanDeath = new HashMap<Integer, Sprite>();
    private final static HashMap<Integer, Sprite> frightenedGhosts = new HashMap<Integer, Sprite>();
    private final static HashMap<GhostType, HashMap<Direction, HashMap<Integer, Sprite>>> ghosts = new HashMap<GhostType, HashMap<Direction, HashMap<Integer, Sprite>>>();
    private final static HashMap<Direction, HashMap<Integer, Sprite>> deadGhost = new HashMap<Direction, HashMap<Integer, Sprite>>();
    
    static
    {
        loadWalls();
        loadEnergizer();
        loadCharacters();
        loadPoints();
        loadPacmanMovements();
        loadPacmanDeath();
        loadFrightenedGhosts();
        loadGhosts();
        loadDeadGhost();  
    }
    
    private Sprites() {}
    
    private static void loadWalls()
    {
        for (int x = 0; x < 12; x++)
        {
        	walls.put(x + 1, new Sprite(tiles[x][0], tiles[x][1], tileSize)); // First row of tilesheet
        	walls.put(x + 13, new Sprite(tiles[x + 16][0], tiles[x+ 16][1], tileSize)); // Second row of tilesheet
        }
    }
    
    private static void loadEnergizer()
    {
    	energizer.put(0, new Sprite(tiles[12][0], tiles[12][1], tileSize));
    	energizer.put(1, new Sprite(tiles[13][0], tiles[13][1], tileSize));
    	energizer.put(2, new Sprite(tiles[14][0], tiles[14][1], tileSize));
    }
    
    private static void loadCharacters()
    {
        characters.put('-', new Sprite(tiles[15][0], tiles[15][1], tileSize));
        int idx = 28;
        for (char number = '0'; number <= '9'; number++) 
        {
        	characters.put(number, new Sprite(tiles[idx][0], tiles[idx][1], tileSize));
        	idx++;
        }
        for (char letter = 'A'; letter <= 'Z'; letter++) 
        {
        	characters.put(letter, new Sprite(tiles[idx][0], tiles[idx][1], tileSize));
        	characters.put(Character.toLowerCase(letter), new Sprite(tiles[idx][0], tiles[idx][1], tileSize));
        	idx++;
        }
    }
    
    private static void loadPoints()
    {
    	int idx = 80;
    	for (int point = 200; point <= 1600; point = 2 * point)
    	{
    		points.put(point, new Sprite(tiles[idx][0], tiles[idx][1], 2 * tileSize));
    		idx += 2;
    	}
    }

    private static void loadPacmanMovements()
    {    	
    	pacmanMovement.put(Direction.LEFT, new HashMap<Integer, Sprite>());
    	pacmanMovement.get(Direction.LEFT).put(0, new Sprite(tiles[96][0], tiles[96][1], 2 * tileSize));
    	pacmanMovement.get(Direction.LEFT).put(1, new Sprite(tiles[100][0], tiles[100][1], 2 * tileSize));
    	pacmanMovement.get(Direction.LEFT).put(2, new Sprite(tiles[128][0], tiles[128][1], 2 * tileSize));
    	
    	pacmanMovement.put(Direction.UP, new HashMap<Integer, Sprite>());
    	pacmanMovement.get(Direction.UP).put(0, new Sprite(tiles[98][0], tiles[98][1], 2 * tileSize));
    	pacmanMovement.get(Direction.UP).put(1, new Sprite(tiles[102][0], tiles[102][1], 2 * tileSize));
    	pacmanMovement.get(Direction.UP).put(2, new Sprite(tiles[128][0], tiles[128][1], 2 * tileSize));
    	
    	pacmanMovement.put(Direction.RIGHT, new HashMap<Integer, Sprite>());
    	pacmanMovement.get(Direction.RIGHT).put(0, new Sprite(tiles[104][0], tiles[104][1], 2 * tileSize));
    	pacmanMovement.get(Direction.RIGHT).put(1, new Sprite(tiles[108][0], tiles[108][1], 2 * tileSize));
    	pacmanMovement.get(Direction.RIGHT).put(2, new Sprite(tiles[128][0], tiles[128][1], 2 * tileSize));
    	
    	pacmanMovement.put(Direction.DOWN, new HashMap<Integer, Sprite>());
    	pacmanMovement.get(Direction.DOWN).put(0, new Sprite(tiles[106][0], tiles[106][1], 2 * tileSize));
    	pacmanMovement.get(Direction.DOWN).put(1, new Sprite(tiles[110][0], tiles[110][1], 2 * tileSize));
    	pacmanMovement.get(Direction.DOWN).put(2, new Sprite(tiles[128][0], tiles[128][1], 2 * tileSize));
    }
    
    private static void loadPacmanDeath()
    {
    	int idx = 128;
    	for (int x = 0; x < 8; x++)
    	{
    		pacmanDeath.put(x, new Sprite(tiles[idx][0], tiles[idx][1], 2 * tileSize));
    		idx += 2;
    	}
    	idx = 160;
    	for (int x = 8; x < 12; x++)
    	{
    		pacmanDeath.put(x, new Sprite(tiles[idx][0], tiles[idx][1], 2 * tileSize));
    		idx += 2;
    	}
    }
    
    private static void loadFrightenedGhosts()
    {
    	int idx = 168;
    	for (int x = 0; x < 4; x++)
    	{
    		frightenedGhosts.put(x, new Sprite(tiles[idx][0], tiles[idx][1], 2 * tileSize));
    		idx += 2;
    	}
    }
    
    private static void loadGhosts()
    {
        int block = 192;
        for (GhostType ghost : GhostType.values())
        {
        	ghosts.put(ghost, new HashMap<Direction, HashMap<Integer, Sprite>>());
            for (Direction dir : Direction.values())
            {
            	ghosts.get(ghost).put(dir, new HashMap<Integer, Sprite>());
            	ghosts.get(ghost).get(dir).put(0, new Sprite(tiles[block][0], tiles[block][1], 2 * tileSize));
            	ghosts.get(ghost).get(dir).put(1, new Sprite(tiles[block + 2][0], tiles[block + 2][1], 2 * tileSize));
            	block += 4;
            }
            block += 16;
        }
    }
    
    private static void loadDeadGhost()
    {
        int block = 336;
        for (Direction dir : Direction.values())
        {
        	deadGhost.put(dir, new HashMap<Integer, Sprite>());
        	deadGhost.get(dir).put(0, new Sprite(tiles[block][0], tiles[block][1], 2 * tileSize));
        	deadGhost.get(dir).put(1, new Sprite(tiles[block + 2][0], tiles[block + 2][1], 2 * tileSize));
        	block += 2;
        }
    }
    
    /**
     * @param type : Wall type (1 to 24).
     * @return Wall sprite.
     */
    public static Sprite getWall(int type)
    {
    	return walls.get(type);
    }
    
    /**
     * @return PacDot sprite.
     */
    public static Sprite getPacDot()
    {
    	return pacDot;
    }
    
    /**
     * @param state : State of the animation (0 or 1).
     * @return Engergizer sprite.
     */
    public static Sprite getEnergizer(int state)
    {
    	return energizer.get(state);
    }
    
    /**
     * @param c : The character (a to z, A to Z and -).
     * @return Character sprite.
     */
    public static Sprite getCharacter(Character c)
    {
    	return characters.get(c);
    }
    
    /**
     * @param p : Points (200, 400, 800 or 1600).
     * @return Points sprite.
     */
    public static Sprite getPoints(int p)
    {
    	return points.get(p);
    }
    
    /**
     * @return The pacman game title.
     */
    public static Sprite getTitle()
    {
    	return title;
    }
    
    /**
     * @param dir : Where pacman mouth is facing.
     * @param state : State of the animation (0, 1 or 2).
     * @return Pacman movement sprite.
     */
    /**
     * @param dir
     * @param state
     * @return
     */
    public static Sprite getPacmanMovement(Direction dir, int state)
    {
    	return pacmanMovement.get(dir).get(state);
    }
    
    /**
     * @param state : State of the animation (0 to 11).
     * @return Pacman death sprite.
     */
    public static Sprite getPacmanDeath(int state)
    {
    	return pacmanDeath.get(state);
    }
    
    /**
     * @param state : State of the animation (0, 1, 2 or 3).
     * @return Frightened ghost sprite.
     */
    public static Sprite getFrightenedGhost(int state)
    {
    	return frightenedGhosts.get(state);
    }
    
    /**
     * @param type : The ghost type.
     * @param dir : Where the ghost eyes are looking.
     * @param state : State of the animation (0 or 1).
     * @return Ghost sprite.
     */
    public static Sprite getGhost(GhostType type, Direction dir, int state)
    {
    	return ghosts.get(type).get(dir).get(state);
    }
    
    /**
     * @param dir : Where the dead ghost eyes are looking.
     * @param state : State of the animation (0 or 1).
     * @return Dead ghost sprite.
     */
    public static Sprite getDeadGhost(Direction dir, int state)
    {
    	return deadGhost.get(dir).get(state);
    }
    
    /**
     * @return The tileSheet where all the sprites are located.
     */
    public static Image getTilesSheet()
    {
        return tilesSheet;
    }
    
	/**
	 * @return The size of a single tile.
	 */
	public static int getTilesize() 
	{
		return tileSize;
	}  
}
