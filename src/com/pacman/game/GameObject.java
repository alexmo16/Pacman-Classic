package com.pacman.game;

import java.awt.Rectangle;

public abstract class GameObject extends Rectangle{

	private static final long serialVersionUID = 1L;

	public GameObject(int x, int y, int width, int height ) {
		super(x, y, width, height);

	}
		
	public GameObject() {
		super();
	}
}
