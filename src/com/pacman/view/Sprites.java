package com.pacman.view;

import java.awt.Image;
import java.util.HashMap;

import com.pacman.model.world.Direction;
import com.pacman.utils.SpriteUtils;

public class Sprites
{
    private Image spritesSheet = null;
    private int[][] spritesCoords = null;
    private final int sheetWidth = 16;

    private int blockSize = 0;
    
    HashMap<Direction, int[]> pacman = new HashMap<Direction, int[]>();
    HashMap<Character, int[]> characters = new HashMap<Character, int[]>();
    
    public Sprites(String spritesSheetFilePath, int size)
    {
        blockSize = size;
        spritesSheet = SpriteUtils.getSpritesSheetImage(spritesSheetFilePath);
        spritesCoords = SpriteUtils.getSpritesCoordsFromSheet(spritesSheet, blockSize);
        
        pacman.put(Direction.RIGHT, new int []{ spritesCoords[104][0], spritesCoords[104][1], spritesCoords[121][0] + blockSize, spritesCoords[121][1] + blockSize });
        pacman.put(Direction.LEFT,  new int []{ spritesCoords[96][0],  spritesCoords[96][1],  spritesCoords[113][0] + blockSize, spritesCoords[113][1] + blockSize });
        pacman.put(Direction.UP,    new int []{ spritesCoords[98][0],  spritesCoords[98][1],  spritesCoords[115][0] + blockSize, spritesCoords[115][1] + blockSize });
        pacman.put(Direction.DOWN,  new int []{ spritesCoords[106][0], spritesCoords[106][1], spritesCoords[123][0] + blockSize, spritesCoords[123][1] + blockSize });
        
        characters.put('-', spritesCoords[15]);
        int idx = 28;
        for (char number = '0'; number <= '9'; number++) 
        {
        	characters.put(number, spritesCoords[idx]);
        	idx++;
        }
        for (char letter = 'A'; letter <= 'Z'; letter++) 
        {
        	characters.put(letter, spritesCoords[idx]);
        	characters.put(Character.toLowerCase(letter), spritesCoords[idx]);
        	idx++;
        }
        
    }
    
    public int[] getMazeTileCoords(int tileNumber)
    {
        if (tileNumber > 12)
        {
            tileNumber += sheetWidth - 12;
        }

        return spritesCoords[tileNumber - 1];
    }

    public int[] getPacmanCoords(Direction sprite)
    {
        return pacman.get(sprite);
    }

    public int[] getPacDotCoords()
    {
        return spritesCoords[12];
    }
    
    public int[] getEnergizerCoords()
    {
        return spritesCoords[14];
    }
    
    public int[] getCharacterCoords(Character c)
	{
    	return characters.get(c);
	}
    
    public Image getSpritesSheet()
    {
        return spritesSheet;
    }
    
    public void setSpritesSheet(Image spritesSheet)
    {
        this.spritesSheet = spritesSheet;
    }

    public int[][] getSpritesCoords()
    {
        return spritesCoords;
    }

    public void setSpritesCoords(int[][] spritesCoords)
    {
        this.spritesCoords = spritesCoords;
    }

}
