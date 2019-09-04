package com.pacman.engine.objects;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Definie tous les objets du jeu et qui n'a pas de boite de collision.
 *
 */
public abstract class SceneObject extends JPanel 
{
	private static final long serialVersionUID = -775383454698124968L;
	
	public SceneObject()
	{
	    super();
		setOpaque(false);
	}

	abstract protected void paintComponent(Graphics g);
}
