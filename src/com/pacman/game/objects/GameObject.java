package com.pacman.game.objects;

import java.awt.Rectangle;

import javax.swing.JComponent;

public abstract class GameObject extends JComponent{

	private static final long serialVersionUID = 1L;
	protected Rectangle object = null;
	
	public GameObject(int x, int y, int width, int height ) {
		super();
		this.object = new Rectangle(x, y, width, height);

	}
		
	public GameObject() {
		super();
		this.object = new Rectangle();
	}
	
	public Rectangle getRectangle() {
		return this.object;
	}
}
