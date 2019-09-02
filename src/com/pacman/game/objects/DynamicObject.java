package com.pacman.game.objects;

import java.awt.Rectangle;

import com.pacman.game.Settings;

public abstract class DynamicObject extends GameObject {
	
	private static final long serialVersionUID = 1L;
	protected String direction;
	
	public DynamicObject() {
		super();
		this.direction = null;
	}
	
	public DynamicObject(int x, int y, int width, int height, String direction, Settings s) {
		super(x,y,width,height, s);
		this.direction = direction;
	}
	
	public String getDirection() {
		return this.direction;
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

	public static void tunnel(Rectangle object, String direction)
	{
		switch(direction)
		{
		case "right":
			object.setLocation(0,(int)object.getY());
			break;
		case "left":
			object.setLocation(600-19,(int)object.getY());
			break;
		case "up":
			object.setLocation((int)object.getX(),660-20);
			break;
		case "down":
			object.setLocation((int)object.getX(),0);
			break;
		default:
			break;
		}
	}
}
