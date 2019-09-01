package com.pacman.game.objects;

import java.awt.Rectangle;
import com.pacman.game.Settings;
import com.pacman.game.SpritesManager;

import javax.swing.JComponent;

public abstract class GameObject extends JComponent{

	private static final long serialVersionUID = 1L;
	protected final int mazeHeight, mazeWidth;
	protected final SpritesManager spritesManager; 
	protected Rectangle object = null;
	
	public GameObject(int x, int y, int width, int height, Settings s) {
		super();
		this.mazeHeight = s.getMazeData().getHeight();
		this.mazeWidth = s.getMazeData().getWidth();
		this.spritesManager = s.getSpritesManager();
		this.object = new Rectangle(x, y, width, height);

	}
		
	public GameObject() {
		super();
		this.mazeHeight = 0;
		this.mazeWidth = 0;
		this.spritesManager = null;
		this.object = new Rectangle();
	}
	
	public Rectangle getRectangle() {
		return this.object;
	}
}
