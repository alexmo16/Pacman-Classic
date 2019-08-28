package com.pacman.game;

public class Settings {

	private String title = "Pac-Man";
	private int width = 800, height = 600;
	private float scale = 1.0f;
	private final double UPDATE_RATE = 1.0/60.0; // pour avoir 60 fps dans notre jeu. 
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}

	public double getUPDATE_RATE() {
		return UPDATE_RATE;
	}
	
}
