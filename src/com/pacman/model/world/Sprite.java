package com.pacman.model.world;

/**
 * @author Felix Roy
 * 
 * This class is used to store Sprite's coordinate.
 * Those coordinate are used to locate the sprite on a tiles sheet
 */
public class Sprite 
{
	private int x, y, sizeX, sizeY;
	
	/**
	 * @param x : The stating x coordinate of the sprite in the tiles sheet.
	 * @param y : The stating y coordinate of the sprite in the tiles sheet.
	 * @param size : The height and width in pixel of the sprite.
	 */
	public Sprite(int x, int y, int size)
	{
		this.x = x;
		this.y = y;
		this.sizeX = size;
		this.sizeY = size;
	}
	
	/**
	/**
	 * @param x : The stating x coordinate of the sprite in the tiles sheet.
	 * @param y : The stating y coordinate of the sprite in the tiles sheet.
	 * @param sizeX : The width of the sprite in pixel.
	 * @param sizeY : The height of the sprite in pixel.
	 */
	public Sprite(int x, int y, int sizeX, int sizeY)
	{
		this.x = x;
		this.y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	/**
	 * @return Top left corner x coordinate of the sprite.
	 */
	public int getX1()
	{
		return x;
	}
	
	/**
	 * @return Top left corner y coordinate of the sprite.
	 */
	public int getY1()
	{
		return y;
	}
	
	
	/**
	 * @return Bottom right corner x coordinate of the sprite.
	 */
	public int getX2()
	{
		return x + sizeX;
	}
	
	/**
	 * @return Bottom right corner y coordinate of the sprite.
	 */
	public int getY2()
	{
		return y + sizeY;
	}

	/**
	 * @return Width of the sprite.
	 */
	public int getWidth() 
	{
		return sizeX;
	}

	/**
	 * @return Height of the sprite.
	 */
	public int getHeight() 
	{
		return sizeY;
	}
}
