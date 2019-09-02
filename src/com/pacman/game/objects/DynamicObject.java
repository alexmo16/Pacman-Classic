package com.pacman.game.objects;

import java.awt.geom.Rectangle2D;

import com.pacman.game.Settings;

public abstract class DynamicObject extends GameObject {
	
	private static final long serialVersionUID = 1L;
	protected String direction;
	static double speed = 0.1;
	
	public DynamicObject() {
		super();
		this.direction = null;
	}
	
	public DynamicObject(double x, double y, double width, double height, String direction, Settings s) {
		super(x,y,width,height, s);
		this.direction = direction;
	}
	
	public String getDirection() {
		return this.direction;
	}
	
	
	public void setDirection(String dir) {
		this.direction = dir;
	}
	
	public static void updatePosition(Rectangle2D.Double object, String direction) {
		if (direction.contentEquals("up")) {
			object.setRect(object.getX(),object.getY()-speed,object.getWidth(),object.getHeight());
		}
		if (direction.contentEquals("down")) {
			object.setRect(object.getX(),object.getY()+speed,object.getWidth(),object.getHeight());
		}
		if (direction.contentEquals("right")) {
			object.setRect(object.getX()+speed,object.getY(),object.getWidth(),object.getHeight());
		}
		if (direction.contentEquals("left")) {
			object.setRect(object.getX()-speed,object.getY(),object.getWidth(),object.getHeight());
		}
	}

	public static void tunnel(Rectangle2D.Double object, String direction)
	{
		switch(direction)
		{
		case "right":
			object.setRect(0,object.getY(),object.getWidth(),object.getHeight());
			break;
		case "left":
			object.setRect(mazeWidth - object.getWidth() - 0.1,object.getY(),object.getWidth(),object.getHeight());
			break;
		case "up":
			object.setRect(object.getX(),mazeHeight - object.getHeight() - 0.1 ,object.getWidth(),object.getHeight());
			break;
		case "down":
			object.setRect(object.getX(),0,object.getWidth(),object.getHeight());
			break;
		default:
			break;
		}
	}
}
