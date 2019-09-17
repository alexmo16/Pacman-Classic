package com.pacman.view.utils;

import java.awt.Color;
import java.awt.Graphics;

import com.pacman.model.objects.Sprites;
import com.pacman.model.world.Sprite;

final public class Renderer 
{
	public static void renderBackground(Graphics g, Color color, int w, int h)
	{
        g.setColor(color);
        g.fillRect(0, 0, w, h);
	}
	
	public static void renderString(Graphics g, String message, int x, int y, int scale)
	{
		for (int i = 0; i < message.length(); i++)
        {
        	if (message.charAt(i) != ' ')
        	{
        		Sprite sprite = Sprites.getCharacter(message.charAt(i));
        		g.drawImage(Sprites.getTilesSheet(), 
							x + i * scale, 
							y, 
							x + i * scale + scale, 
							y + scale, 
							sprite.getX1(), 
							sprite.getY1(), 
							sprite.getX2(), 
							sprite.getY2(), 
							null);
        	}
        }
	}
}
