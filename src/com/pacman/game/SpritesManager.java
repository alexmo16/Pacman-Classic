package com.pacman.game;

import java.awt.Image;
import com.pacman.utils.SpriteUtils;

public class SpritesManager 
{
	private Image spritesSheet = null;
	private int[][] spritesCoords = null;
	private final int sheetWidth = 16;
	int pacman [][] = null;
	
	public SpritesManager(String spritesSheetFilePath, int blockSize)
	{
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
	
	public int[][] getPacmanCoords(String direction) {

			pacman  = new int [][] {spritesCoords[104],spritesCoords[105],spritesCoords[120],spritesCoords[121]};
		
		
		return pacman;
	}

	public int[] getGumCoords()
	{		
		return spritesCoords[12];
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
