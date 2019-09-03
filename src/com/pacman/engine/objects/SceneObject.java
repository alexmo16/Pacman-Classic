package com.pacman.engine.objects;

import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class SceneObject extends JPanel 
{
	private static final long serialVersionUID = -775383454698124968L;
	
	public SceneObject()
	{
		setOpaque(false);
	}

	abstract protected void paintComponent(Graphics g);
}
