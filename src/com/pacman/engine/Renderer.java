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
		width = s.getMinWindowWidth();
		height = s.getMinWindowHeight();
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
	
	public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2)
	{
		g2d.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}
	
	public void clear()
	{
		g2d.setColor( Color.black );
		g2d.fillRect( 0, 0, width, height );
	}
}
