package com.pacman.game.objects;


import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.pacman.engine.Inputs;
import com.pacman.game.Settings;
import com.pacman.game.SpritesManager;

public class PacmanObject extends DynamicObject {
	
	private static final long serialVersionUID = 1L;
	private final SpritesManager spritesManager;
	Settings settings = new Settings();

	public PacmanObject() {
		super();
		spritesManager = settings.getSpritesManager();
	}
	
	public PacmanObject(int x, int y, int width, int height, String direction, Settings s) {
		super(x,y,width,height,direction, s);
		spritesManager = settings.getSpritesManager();
	}
	
	public static String getNewDirection(Inputs inputs, String direction) {
		if ( inputs == null )
		{
			return direction;
		}
		
		if (inputs.isKeyDown(KeyEvent.VK_UP)) {
			direction = "up";
		} 
		else if(inputs.isKeyDown(KeyEvent.VK_DOWN)) {
			direction = "down";
		} 
		else if(inputs.isKeyDown(KeyEvent.VK_RIGHT)) {
			direction = "right";
		} 
		else if(inputs.isKeyDown(KeyEvent.VK_LEFT)) {
			direction = "left";
		}
		return direction;
	}
	
    @Override
	public void paint(Graphics g) {
    	int x = (int) this.object.getX();
    	int y = (int) this.object.getY();
    	int size = (int) this.object.getWidth();
    	
        super.paint(g);
        int[] k = spritesManager.getPacmanCoords();
        g.drawImage(spritesManager.getSpritesSheet(), x, y, x+size, y+size, k[0], k[1], k[2], k[3], null);
    }
}
