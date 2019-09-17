package com.pacman.view.views;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.pacman.model.world.Level;

abstract public class View extends JPanel 
{
	private static final long serialVersionUID = -28564039894940654L;
	protected int sFactor = 0; // Tile size
	
	abstract public String getName();
	
	@Override
	public void paintComponent(Graphics g)
	{
		sFactor = Math.min(getHeight() / (Level.getHeight() + 4), getWidth() / Level.getWidth());
        if ( (sFactor & 1) != 0 ) { sFactor--; } // Odd tile size will break tile tileSize.
	}
}
