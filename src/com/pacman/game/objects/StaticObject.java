package com.pacman.game.objects;

public class StaticObject extends GameObject {

	private static final long serialVersionUID = 1L;
	protected boolean eaten;
	protected int score;

	public StaticObject() {
		super();
		this.eaten = false;
		this.score = 0;
	}
	
	public StaticObject(int x, int y, int width, int height, int score) {
		super(x, y, width, height);
		this.eaten = false;
		this.score = score;
	}
	
}
