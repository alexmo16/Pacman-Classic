package com.pacman.model.objects;

public class Sprite 
{
	private int x, y, sizeX, sizeY;
	
	public Sprite(int x, int y, int size)
	{
		this.x = x;
		this.y = y;
		this.sizeX = size;
		this.sizeY = size;
	}
	
	public Sprite(int x, int y, int sizeX, int sizeY)
	{
		this.x = x;
		this.y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	public int getX1()
	{
		return x;
	}
	
	public int getY1()
	{
		return y;
	}
	
	public int getX2()
	{
		return x + sizeX;
	}
	
	public int getY2()
	{
		return y + sizeY;
	}
}
