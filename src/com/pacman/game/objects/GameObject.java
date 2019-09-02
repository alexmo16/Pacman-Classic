package com.pacman.game.objects;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.pacman.game.Settings;
import com.pacman.game.SpritesManager;

import javax.swing.JPanel;

public abstract class GameObject extends JPanel{
	private static final long serialVersionUID = 5089602216528128118L;
	
	protected final int mazeHeight, mazeWidth;
	protected final SpritesManager spritesManager; 
	
	protected Rectangle2D.Float object = null;
	
	protected int x;
	protected int y;
	
	public GameObject(int x, int y, int width, int height, Settings s) {
		super();
		this.mazeHeight = s.getMazeData().getHeight();
		this.mazeWidth = s.getMazeData().getWidth();
		this.spritesManager = s.getSpritesManager();
		this.object = new Rectangle2D.Float(x, y, width, height);
		setOpaque(false);
	}
		
	public GameObject() {
		super();
		this.mazeHeight = 0;
		this.mazeWidth = 0;
		this.spritesManager = null;
		this.object = new Rectangle2D.Float();
		setOpaque(false);
	}
	
	public Rectangle2D.Float getRectangle() {
		return this.object;
	}
}
