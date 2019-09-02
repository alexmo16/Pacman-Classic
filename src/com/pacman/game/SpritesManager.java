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
		if (direction == "left") {
			pacman  = new int [][] {spritesCoords[96],spritesCoords[97],spritesCoords[112],spritesCoords[113]};
		} else if (direction == "up") {
			pacman  = new int [][] {spritesCoords[98],spritesCoords[99],spritesCoords[114],spritesCoords[115]};
		} else if (direction == "down") {
			pacman  = new int [][] {spritesCoords[106],spritesCoords[107],spritesCoords[122],spritesCoords[123]};
		} else {
			pacman  = new int [][] {spritesCoords[104],spritesCoords[105],spritesCoords[120],spritesCoords[121]};
		}
		
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
