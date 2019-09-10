package com.pacman.model.objects;

import com.pacman.utils.Settings;

public class Maze 
{
	private final int height,
	 				  width;
	private final int[][] tiles;
	
	public Maze()
	{
		height = Settings.WORLD_DATA.getHeight();
		width = Settings.WORLD_DATA.getWidth();
		tiles = Settings.WORLD_DATA.getTiles();
	}

	public int getHeight()
	{
		return height;
	}
	
	public int getWidth() 
	{
		return width;
	}
	
	public int[][] getTiles() 
	{
		return tiles;
	}
}
