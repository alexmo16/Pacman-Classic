package com.pacman.game.objects;

import java.awt.Color;
import java.awt.Graphics;

public class Background extends SceneObject
{
	private static final long serialVersionUID = -1595780761418114619L;

	private final Color color;
	
	public Background(Color col)
    {
		color = col;
    }

	@Override
    protected void paintComponent(Graphics g)
    {
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
