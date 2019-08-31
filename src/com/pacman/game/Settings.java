package com.pacman.game;

import java.io.File;

import com.pacman.engine.ISettings;

public class Settings implements ISettings {

	private String title = "Pac-Man";
	private int width = 800, height = 600;
	private float scale = 1.0f;
	private final double UPDATE_RATE = 1.0/60.0; // pour avoir 60 fps dans notre jeu. 
	private String mapFilePath = System.getProperty("user.dir") + File.separator + "assets" + File.separator + "map.txt";
	private String spritesFilepath = System.getProperty("user.dir") + File.separator + "assets" + File.separator + "pacmanTiles.png";
	
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public void setWidth(int width) {
		this.width = width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public void setHeight(int height) {
		this.height = height;
	}
	
	@Override
	public float getScale() {
		return scale;
	}
	
	@Override
	public void setScale(float scale) {
		this.scale = scale;
	}

	@Override
	public double getUpdateRate() {
		return UPDATE_RATE;
	}

	@Override
	public String getMapFilePath() {
		return mapFilePath;
	}

	@Override
	public void setMapFilePath(String mapFilePath) {
		this.mapFilePath = mapFilePath;
	}

	@Override
	public String getSpritesFilepath() {
		return spritesFilepath;
	}

	@Override
	public void setSpritesFilepath(String spritesFilepath) {
		this.spritesFilepath = spritesFilepath;
	}
}
