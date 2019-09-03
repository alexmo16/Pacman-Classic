package com.pacman.engine;

import java.awt.Image;

public interface ISpritesManager 
{
	public void setSpritesSheet(Image spritesSheet);
	public int[][] getSpritesCoords();
	public void setSpritesCoords(int[][] spritesCoords);
	public Image getSpritesSheet();
}
