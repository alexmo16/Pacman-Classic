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
	protected int score = 0;

	public PacmanObject() {
		super();
		spritesManager = settings.getSpritesManager();
	}
	
	public PacmanObject(double x, double y, double width, double height, String direction, Settings s) {
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
	
	
	public void eatGum(StaticObject obj)
	{
		this.score += obj.getPoints();
		obj.setEaten(true);
	}
	
	public int getScore() {
		return score;
	}
	
    @Override
	public void paintComponent(Graphics g) {
    	
    	int size = Math.min(getWidth(), getHeight()) / mazeHeight;
		double x = this.object.getX();
		double y = this.object.getY();
		
		x = (x * size) + ((getWidth() - (size * mazeWidth) - (size / 2)) / 2);
		y = (y * size) + ((getHeight() - (size * mazeHeight) - (size / 2)) / 2);

        super.paintComponent(g);
        int[] k = spritesManager.getPacmanCoords(this.getDirection());
        g.drawImage(spritesManager.getSpritesSheet(), (int)x, (int)y, (int)x+(2*size), (int)y+(2*size), k[0], k[1], k[2], k[3], null);
    }


}
