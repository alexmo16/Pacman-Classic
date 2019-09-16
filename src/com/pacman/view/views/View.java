package com.pacman.view.views;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.pacman.model.objects.Sprites;
import com.pacman.model.world.Level;
import com.pacman.model.world.Sprite;

abstract public class View extends JPanel 
{
	private static final long serialVersionUID = -28564039894940654L;
	protected int sFactor = 0; // Tile size
	
	@Override
	public void paintComponent(Graphics g)
	{
		sFactor = Math.min(getHeight() / (Level.getHeight() + 4), getWidth() / Level.getWidth());
        if ( (sFactor & 1) != 0 ) { sFactor--; } // Odd tile size will break tile tileSize.
	}
	
	protected void renderBackground(Graphics g, Color color)
	{
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	protected void renderString(Graphics g, String message, int x, int y)
	{
		for (int i = 0; i < message.length(); i++)
        {
        	if (message.charAt(i) != ' ')
        	{
        		Sprite k = Sprites.getCharacter(message.charAt(i));
        		g.drawImage(Sprites.getTilesSheet(), 
							x + i * sFactor, 
							y, 
							x + i * sFactor + sFactor, 
							y + sFactor, 
							k.getX1(), 
							k.getY1(), 
							k.getX2(), 
							k.getY2(), 
							null);
        	}
        }
	}
	
	protected void renderCustomScaleString(Graphics g, String message, int x, int y, int scale)
	{
		for (int i = 0; i < message.length(); i++)
        {
        	if (message.charAt(i) != ' ')
        	{
        		Sprite k = Sprites.getCharacter(message.charAt(i));
        		g.drawImage(Sprites.getTilesSheet(), 
							x + i * scale, 
							y, 
							x + i * scale + scale, 
							y + scale, 
							k.getX1(), 
							k.getY1(), 
							k.getX2(), 
							k.getY2(), 
							null);
        	}
        }
	}
	
	abstract public String getName();
}
