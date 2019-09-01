package com.pacman.game.objects;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.pacman.engine.Engine;

public class DynamicObject {
	
	
	private static DynamicObject instance;
	
	public static DynamicObject getInstance()
	{
		if ( instance == null )
		{
			instance = new DynamicObject();		
		}
		
		return instance;
	}
	
	public String getNewDirection(String direction) {
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
	
	public void updatePosition(Rectangle object, String direction) {
		if (direction.contentEquals("up")) {
			object.setLocation((int)object.getX(),(int)object.getY()-4);
		}
		if (direction.contentEquals("down")) {
			object.setLocation((int)object.getX(),(int)object.getY()+4);
		}
		if (direction.contentEquals("right")) {
			object.setLocation((int)object.getX()+4,(int)object.getY());
		}
		if (direction.contentEquals("left")) {
			object.setLocation((int)object.getX()-4,(int)object.getY());
		}
	}
}
