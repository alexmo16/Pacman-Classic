package com.pacman.gum;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class StaticObject extends Rectangle{

	private static final long serialVersionUID = 1L;
	
	protected int posX;
	protected int posY;
	protected int height;
	protected int width;

	public StaticObject(int posX, int posY, int width, int height) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
