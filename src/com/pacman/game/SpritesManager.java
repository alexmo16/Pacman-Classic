package com.pacman.game;

import java.awt.Image;

import com.pacman.engine.ISpritesManager;
import com.pacman.utils.SpriteUtils;

public class SpritesManager implements ISpritesManager
{
    private Image spritesSheet = null;
    private int[][] spritesCoords = null;
    private final int sheetWidth = 16;
    int pacman[] = null;
    int blockSize = 0;

    public SpritesManager(String spritesSheetFilePath, int size)
    {
        blockSize = size;
        spritesSheet = SpriteUtils.getSpritesSheetImage(spritesSheetFilePath);
        spritesCoords = SpriteUtils.getSpritesCoordsFromSheet(spritesSheet, blockSize);
    }

    public int[] getMazeTileCoords(int tileNumber)
    {
        if (tileNumber > 12)
        {
            tileNumber += sheetWidth - 12;
        }

        return spritesCoords[tileNumber - 1];
    }

    public int[] getPacmanCoords(String direction, int animation)
    {
    	if (animation == 2)
    	{
    		pacman = new int[]
            { spritesCoords[128][0], spritesCoords[128][1], spritesCoords[145][0] + blockSize,
                    spritesCoords[145][1] + blockSize };
    		
    	} else if (direction == "right")
        {
    		if (animation == 0)
        	{
	            pacman = new int[]
	            { spritesCoords[104][0], spritesCoords[104][1], spritesCoords[121][0] + blockSize,
	                    spritesCoords[121][1] + blockSize };
        	} else 
        	{
        		pacman = new int[]
        	    { spritesCoords[108][0], spritesCoords[108][1], spritesCoords[125][0] + blockSize,
        	    		spritesCoords[125][1] + blockSize };
        	}

        } else if (direction == "left")
        {
        	if (animation == 0)
        	{
        		pacman = new int[]
        	    { spritesCoords[96][0], spritesCoords[96][1], spritesCoords[113][0] + blockSize,
        	    		spritesCoords[113][1] + blockSize };
        	} else 
        	{
        		pacman = new int[]
                	    { spritesCoords[100][0], spritesCoords[100][1], spritesCoords[117][0] + blockSize,
                	    		spritesCoords[117][1] + blockSize };
        	}
            
        } else if (direction == "up")
        {
        	if (animation == 0)
        	{
	            pacman = new int[]
	            { spritesCoords[98][0], spritesCoords[98][1], spritesCoords[115][0] + blockSize,
	                    spritesCoords[115][1] + blockSize };
        	} else 
        	{
        		pacman = new int[]
        	    { spritesCoords[102][0], spritesCoords[102][1], spritesCoords[119][0] + blockSize,
        	    		spritesCoords[119][1] + blockSize };
        	}

        } else if (direction == "down")
        {
        	if (animation == 0)
        	{
	            pacman = new int[]
	            { spritesCoords[106][0], spritesCoords[106][1], spritesCoords[123][0] + blockSize,
	                    spritesCoords[123][1] + blockSize };
        	} else 
        	{
        		pacman = new int[]
        	    { spritesCoords[110][0], spritesCoords[110][1], spritesCoords[127][0] + blockSize,
        	    		spritesCoords[127][1] + blockSize };
        	}

        }

        return pacman;
    }

    public int[] getGumCoords()
    {
        return spritesCoords[12];
    }

    public int[] getPacGumCoords()
    {
        return spritesCoords[14];
    }

    @Override
    public Image getSpritesSheet()
    {
        return spritesSheet;
    }
    
    @Override
    public void setSpritesSheet(Image spritesSheet)
    {
        this.spritesSheet = spritesSheet;
    }

    @Override
    public int[][] getSpritesCoords()
    {
        return spritesCoords;
    }

    @Override
    public void setSpritesCoords(int[][] spritesCoords)
    {
        this.spritesCoords = spritesCoords;
    }

}
