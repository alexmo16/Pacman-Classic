package com.pacman.game;

import java.awt.event.KeyEvent;
import java.io.File;

import com.pacman.engine.ISettings;

public class Settings implements ISettings 
{	
	private final String spritesFilepath = System.getProperty("user.dir") + File.separator + "assets" + File.separator + "pacmanTiles.png",
						 title = "Pac-Man";
	
	private final int mutedButton = KeyEvent.VK_M, 
					  pauseButton = KeyEvent.VK_P,
				  	  minWindowWidth = 800, 
				  	  minWindowHeight = 600;
	
	private final float scale = 1.0f;
	
	private final double updateRate = 1.0 / 60.0; // pour avoir 60 fps dans notre jeu.
	
	private final Maze maze = new Maze(new String(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "map.txt"));
	
	public String getSpritesFilepath() {
		return spritesFilepath;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public int getMutedButton() {
		return mutedButton;
	}

	@Override
	public int getPauseButton() {
		return pauseButton;
	}

	@Override
	public int getMinWindowWidth() {
		return minWindowWidth;
	}

	@Override
	public int getMinWindowHeight() {
		return minWindowHeight;
	}

	@Override
	public float getScale() {
		return scale;
	}

	@Override
	public double getUpdateRate() {
		return updateRate;
	}

	@Override
	public Maze getMaze() {
		return maze;
	}
}
