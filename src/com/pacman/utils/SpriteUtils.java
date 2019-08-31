package com.pacman.utils;

import java.awt.Image;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;

public class SpriteUtils 
{	
	public static int[][] getSpritesCoordsFromSheet(String spritesSheetFilePath, int blockSize)
	{
		Image spritesSheet = null;
		int[][] spritesCoords = null;
		try
		{
    		if (Files.exists(Paths.get(spritesSheetFilePath))) 
    		{
    			if ((spritesSheet = new ImageIcon(spritesSheetFilePath).getImage()) == null)
    			{
    				throw new Error("Failed to load the sprits sheet in Image object");
    			}
    			
        		if (spritesSheet.getHeight(null) % blockSize != 0)
        		{
        			throw new Error("Block size " + blockSize + " is not compatible with height " + spritesSheet.getHeight(null));
        		}
        		
        		if (spritesSheet.getWidth(null) % blockSize != 0)
        		{
        			throw new Error("Block size " + blockSize + " is not compatible with width " + spritesSheet.getWidth(null));
        		}
    		} 
    		else 
    		{
    			throw new Error("Couldn't find file: " + spritesSheetFilePath);
    		}
    		

    		int xSize = spritesSheet.getWidth(null) / blockSize;
    		int ySize = spritesSheet.getHeight(null) / blockSize;
    		
    		spritesCoords = new int[xSize * ySize][4];
    		for (int y = 0; y < ySize; y++)
    		{
    			for (int x = 0; x < xSize; x++)
    			{
    				spritesCoords[x + y * xSize][0] = x * blockSize;
    				spritesCoords[x + y * xSize][1] = y * blockSize;
    				spritesCoords[x + y * xSize][2] = x * blockSize + blockSize;
    				spritesCoords[x + y * xSize][3] = y * blockSize + blockSize;
    				//System.out.println(spritesCoords[x + y * xSize][0] + " " + spritesCoords[x + y * xSize][1] + " " + spritesCoords[x + y * xSize][2] + " " + spritesCoords[x + y * xSize][3]);
    			}
    		}
		}
        catch (Exception e)
        {
        	spritesCoords = null;
        	System.out.println("Exception in getSpritesCoords : " + e.toString());
        }

		return spritesCoords;
	}
}
