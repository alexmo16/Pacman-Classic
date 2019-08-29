package com.pacman.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.pacman.game.Settings;

public class Renderer 
{

	private Graphics2D g2d;
	private int width;
	private int height;
	
	public Renderer( Graphics2D g2d, Settings s )
	{
		this.g2d = g2d;
		width = s.getWidth();
		height = s.getHeight();
	}
	
	public void drawText( String text, Font font, Color color, int x, int y )
	{
        g2d.setColor( color );
        g2d.setFont( font );
        g2d.drawString( text, x, y );
	}
	
	public void drawImage( Image image, int x, int y )
	{
		g2d.drawImage( image, x, y, null );
	}
	
	public void clear()
	{
		Image image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		drawImage( image, 0, 0 );
	}
}
