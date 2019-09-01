package com.pacman.game;

import java.awt.event.KeyEvent;

import com.pacman.engine.Engine;

public class PacmanObject extends DynamicObject {
	
	private static final long serialVersionUID = 1L;

	public PacmanObject() {
		super();
	}
	
	public PacmanObject(int x, int y, int width, int height, String direction) {
		super(x,y,width,height,direction);
	}
	
	public static String getNewDirection(String direction) {
		if (Engine.getInstance().getInputs().isKeyDown(KeyEvent.VK_UP)) {
			direction = "up";
		} else if(Engine.getInstance().getInputs().isKeyDown(KeyEvent.VK_DOWN)) {
			direction = "down";
		} else if(Engine.getInstance().getInputs().isKeyDown(KeyEvent.VK_RIGHT)) {
			direction = "right";
		} else if(Engine.getInstance().getInputs().isKeyDown(KeyEvent.VK_LEFT)) {
			direction = "left";
		}
		return direction;
	}
	

	
}
