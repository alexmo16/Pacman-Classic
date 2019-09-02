package com.pacman.game.objects;

import com.pacman.game.Settings;

public class StaticObject extends GameObject {

	private static final long serialVersionUID = 1L;
	protected boolean eaten;

	public StaticObject() {
		super();
		this.eaten = false;
	}
	
	public StaticObject(int x, int y, double width, double height, Settings s) {
		super(x, y, width, height, s);
		this.eaten = false;
	}
	
	public boolean getEaten()
	{
		return this.eaten;
	}
	
	public void setEaten(boolean bool)
	{
		this.eaten=bool;
	}
	
}
