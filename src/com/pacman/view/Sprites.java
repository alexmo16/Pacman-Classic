package com.pacman.view;

import java.awt.Image;
import java.util.HashMap;

import com.pacman.model.objects.Sprite;
import com.pacman.model.world.Direction;
import com.pacman.model.world.GhostType;
import com.pacman.utils.SpriteUtils;

public class Sprites
{	
    private Image spritesSheet = null;
    private int[][] coords = null;

    private int blockSize = 0;
    
    HashMap<Integer, Sprite> walls = new HashMap<Integer, Sprite>();
    Sprite pacDot;
    HashMap<Integer, Sprite> energizer = new HashMap<Integer, Sprite>();
    HashMap<Character, Sprite> characters = new HashMap<Character, Sprite>();
    HashMap<Integer, Sprite> points = new HashMap<Integer, Sprite>();
    Sprite title;
    HashMap<Direction, HashMap<Integer, Sprite>> pacmanMovement = new HashMap<Direction, HashMap<Integer, Sprite>>();
    HashMap<Integer, Sprite> pacmanDeath = new HashMap<Integer, Sprite>();
    HashMap<Integer, Sprite> frightenedGhosts = new HashMap<Integer, Sprite>();
    HashMap<GhostType, HashMap<Direction, HashMap<Integer, Sprite>>> ghosts = new HashMap<GhostType, HashMap<Direction, HashMap<Integer, Sprite>>>();
    HashMap<Direction, HashMap<Integer, Sprite>> deadGhost = new HashMap<Direction, HashMap<Integer, Sprite>>();
    
    public Sprites(String spritesSheetFilePath, int size)
    {
        blockSize = size;
        spritesSheet = SpriteUtils.getSpritesSheetImage(spritesSheetFilePath);
        coords = SpriteUtils.getSpritesCoordsFromSheet(spritesSheet, blockSize);
        
        loadWalls();
        loadPacDot();
        loadEnergizer();
        loadCharacters();
        loadPoints();
        loadTitle();
        loadPacmanMovements();
        loadPacmanDeath();
        loadFrightenedGhosts();
        loadGhosts();
        loadDeadGhost();        
    }
    
    private void loadWalls()
    {
        for (int x = 0; x < 12; x++)
        {
        	walls.put(x + 1, new Sprite(coords[x][0], coords[x][1], blockSize)); // First row of tilesheet
        	walls.put(x + 13, new Sprite(coords[x + 16][0], coords[x+ 16][1], blockSize)); // Second row of tilesheet
        }
    }
    
    private void loadPacDot()
    {
    	pacDot = new Sprite(coords[12][0], coords[12][1], blockSize);
    }
    
    private void loadEnergizer()
    {
    	energizer.put(0, new Sprite(coords[13][0], coords[13][1], blockSize));
    	energizer.put(1, new Sprite(coords[14][0], coords[14][1], blockSize));
    }
    
    private void loadCharacters()
    {
        characters.put('-', new Sprite(coords[15][0], coords[15][1], blockSize));
        int idx = 28;
        for (char number = '0'; number <= '9'; number++) 
        {
        	characters.put(number, new Sprite(coords[idx][0], coords[idx][1], blockSize));
        	idx++;
        }
        for (char letter = 'A'; letter <= 'Z'; letter++) 
        {
        	characters.put(letter, new Sprite(coords[idx][0], coords[idx][1], blockSize));
        	characters.put(Character.toLowerCase(letter), new Sprite(coords[idx][0], coords[idx][1], blockSize));
        	idx++;
        }
    }
    
    private void loadPoints()
    {
    	int idx = 80;
    	for (int point = 200; point <= 1600; point = 2 * point)
    	{
    		points.put(point, new Sprite(coords[idx][0], coords[idx][1], 2 * blockSize));
    		idx += 2;
    	}
    }
    
    private void loadTitle()
    {
    	title = new Sprite(coords[87][0], coords[87][1], 8 * blockSize, 2 * blockSize);
    }
    
    private void loadPacmanMovements()
    {    	
    	pacmanMovement.put(Direction.LEFT, new HashMap<Integer, Sprite>());
    	pacmanMovement.get(Direction.LEFT).put(0, new Sprite(coords[96][0], coords[96][1], 2 * blockSize));
    	pacmanMovement.get(Direction.LEFT).put(1, new Sprite(coords[100][0], coords[100][1], 2 * blockSize));
    	pacmanMovement.get(Direction.LEFT).put(2, new Sprite(coords[144][0], coords[144][1], 2 * blockSize));
    	
    	pacmanMovement.put(Direction.UP, new HashMap<Integer, Sprite>());
    	pacmanMovement.get(Direction.UP).put(0, new Sprite(coords[98][0], coords[98][1], 2 * blockSize));
    	pacmanMovement.get(Direction.UP).put(1, new Sprite(coords[99][0], coords[99][1], 2 * blockSize));
    	pacmanMovement.get(Direction.UP).put(2, new Sprite(coords[144][0], coords[144][1], 2 * blockSize));
    	
    	pacmanMovement.put(Direction.RIGHT, new HashMap<Integer, Sprite>());
    	pacmanMovement.get(Direction.RIGHT).put(0, new Sprite(coords[104][0], coords[104][1], 2 * blockSize));
    	pacmanMovement.get(Direction.RIGHT).put(1, new Sprite(coords[108][0], coords[108][1], 2 * blockSize));
    	pacmanMovement.get(Direction.RIGHT).put(2, new Sprite(coords[144][0], coords[144][1], 2 * blockSize));
    	
    	pacmanMovement.put(Direction.DOWN, new HashMap<Integer, Sprite>());
    	pacmanMovement.get(Direction.DOWN).put(0, new Sprite(coords[106][0], coords[106][1], 2 * blockSize));
    	pacmanMovement.get(Direction.DOWN).put(1, new Sprite(coords[110][0], coords[110][1], 2 * blockSize));
    	pacmanMovement.get(Direction.DOWN).put(2, new Sprite(coords[144][0], coords[144][1], 2 * blockSize));
    }
    
    private void loadPacmanDeath()
    {
    	int idx = 144;
    	for (int x = 0; x < 12; x++)
    	{
    		pacmanDeath.put(x, new Sprite(coords[idx + x][0], coords[idx + x][1], 2 * blockSize));
    		idx += 2;
    	}
    }
    
    private void loadFrightenedGhosts()
    {
    	int idx = 168;
    	for (int x = 0; x < 4; x++)
    	{
    		frightenedGhosts.put(x, new Sprite(coords[idx][0], coords[idx][1], 2 * blockSize));
    		idx += 2;
    	}
    }
    
    private void loadGhosts()
    {
        int block = 192;
        for (GhostType ghost : GhostType.values())
        {
        	ghosts.put(ghost, new HashMap<Direction, HashMap<Integer, Sprite>>());
            for (Direction dir : Direction.values())
            {
            	ghosts.get(ghost).put(dir, new HashMap<Integer, Sprite>());
            	ghosts.get(ghost).get(dir).put(0, new Sprite(coords[block][0], coords[block][1], 2 * blockSize));
            	ghosts.get(ghost).get(dir).put(1, new Sprite(coords[block + 2][0], coords[block + 2][1], 2 * blockSize));
            	block += 4;
            }
            block += 16;
        }
    }
    
    private void loadDeadGhost()
    {
        int block = 336;
        for (Direction dir : Direction.values())
        {
        	deadGhost.put(dir, new HashMap<Integer, Sprite>());
        	deadGhost.get(dir).put(0, new Sprite(coords[block][0], coords[block][1], 2 * blockSize));
        	deadGhost.get(dir).put(1, new Sprite(coords[block + 2][0], coords[block + 2][1], 2 * blockSize));
        	block += 2;
        }
    }
    
    public Sprite getWall(int wallType)
    {
    	return walls.get(wallType);
    }
    
    public Sprite getPacDot()
    {
    	return pacDot;
    }
    
    public Sprite getEnergizer(int state)
    {
    	// state must be 0 or 1
    	return energizer.get(state);
    }
    
    public Sprite getCharacter(Character c)
    {
    	// Supported char : a-z, A-Z and -
    	return characters.get(c);
    }
    
    public Sprite getPoints(int p)
    {
    	// p must be 200, 400, 800 or 1600
    	return points.get(p);
    }
    
    public Sprite getTitle()
    {
    	return title;
    }
    
    public Sprite getPacmanMovement(Direction dir, int state)
    {
    	// State must be 0, 1 or 2
    	return pacmanMovement.get(dir).get(state);
    }
    
    public Sprite getPacmanDeath(int state)
    {
    	// State must be 0 to 11
    	return pacmanDeath.get(state);
    }
    
    public Sprite getFrightenedGhost(int state)
    {
    	// State must be 0, 1 or 2
    	return frightenedGhosts.get(state);
    }
    
    public Sprite getGhost(GhostType type, Direction dir, int state)
    {
    	// State must be 0 or 1
    	return ghosts.get(type).get(dir).get(state);
    }
    
    public Sprite getDeadGhost(Direction dir, int state)
    {
    	// State must be 0 or 1
    	return deadGhost.get(dir).get(state);
    }
    
    public Image getSpritesSheet()
    {
        return spritesSheet;
    }  
}
