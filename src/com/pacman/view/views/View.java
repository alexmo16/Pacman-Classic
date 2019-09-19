package com.pacman.view.views;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.pacman.model.world.Level;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
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
