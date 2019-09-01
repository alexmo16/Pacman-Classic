package com.pacman.game.objects;

import java.awt.Rectangle;

public abstract class DynamicObject extends GameObject {
	
	private static final long serialVersionUID = 1L;
	protected String direction;
	
	public DynamicObject() {
		super();
		this.direction = null;
	}
	
	public DynamicObject(int x, int y, int width, int height, String direction) {
		super(x,y,width,height);
		this.direction = direction;
	}
	
	
	
	public static void updatePosition(Rectangle object, String direction) {
		if (direction.contentEquals("up")) {
			object.setLocation((int)object.getX(),(int)object.getY()-1);
		}
		if (direction.contentEquals("down")) {
			object.setLocation((int)object.getX(),(int)object.getY()+1);
		}
		if (direction.contentEquals("right")) {
			object.setLocation((int)object.getX()+1,(int)object.getY());
		}
		if (direction.contentEquals("left")) {
			object.setLocation((int)object.getX()-1,(int)object.getY());
		}
	}
}
