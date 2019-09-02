package com.pacman.engine;

import com.pacman.game.MazeData;

public interface ISettings {
	public String getTitle();
	public int getMutedButton();
	public int getPauseButton();
	public int getMinWindowWidth();
	public int getMinWindowHeight();
	public float getScale();
	public double getUpdateRate();
	public MazeData getMazeData();
	public int[] getAuthTiles();
}
