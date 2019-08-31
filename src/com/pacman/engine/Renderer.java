package com.pacman.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

public class Renderer 
{

	private Graphics2D g2d;
	private int width;
	private int height;
	
	public Renderer( Graphics2D g2d, ISettings s )
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
		g2d.setColor( Color.black );
		g2d.fillRect( 0, 0, width, height );
	}
}
