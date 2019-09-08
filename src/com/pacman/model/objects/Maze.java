package com.pacman.model.objects;

import com.pacman.model.Settings;
import com.pacman.view.Sprites;

public class Maze 
{
	private final int height,
	 				  width;
	private final int[][] tiles;
	private final Sprites sprites; 
	
	public Maze(Settings s)
	{
		height = s.getWorldData().getHeight();
		width = s.getWorldData().getWidth();
		tiles = s.getWorldData().getTiles();
		sprites = s.getSpritesManager();
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
	
	public Sprites getSrpites() 
	{
		return sprites;
	}
}
