package com.pacman.engine;

public interface ISettings {
	public String getTitle();
	
	public void setTitle(String title);
	
	public int getWidth();
	
	public void setWidth(int width);
	
	public int getHeight();
	
	public void setHeight(int height);
	
	public float getScale();
	
	public void setScale(float scale);

	public double getUpdateRate();
}
