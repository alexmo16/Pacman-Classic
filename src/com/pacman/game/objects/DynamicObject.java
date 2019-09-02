package com.pacman.game.objects;

import java.awt.geom.Rectangle2D;

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
	
	
	
	public static void updatePosition(Rectangle2D.Float object, String direction) {
		if (direction.contentEquals("up")) {
			object.setRect(object.getX(),object.getY()-1,object.getWidth(),object.getHeight());
		}
		if (direction.contentEquals("down")) {
			object.setRect(object.getX(),object.getY()+1,object.getWidth(),object.getHeight());
		}
		if (direction.contentEquals("right")) {
			object.setRect(object.getX()+1,object.getY(),object.getWidth(),object.getHeight());
		}
		if (direction.contentEquals("left")) {
			object.setRect(object.getX()-1,object.getY(),object.getWidth(),object.getHeight());
		}
	}

	public static void tunnel(Rectangle2D.Float object, String direction)
	{
		switch(direction)
		{
		case "right":
			object.setRect(0,(int)object.getY(),object.getWidth(),object.getHeight());
			break;
		case "left":
			object.setRect(600-19,(int)object.getY(),object.getWidth(),object.getHeight());
			break;
		case "up":
			object.setRect((int)object.getX(),660-20,object.getWidth(),object.getHeight());
			break;
		case "down":
			object.setRect((int)object.getX(),0,object.getWidth(),object.getHeight());
			break;
		default:
			break;
		}
	}
}
