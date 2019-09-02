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
	private int score = 0;

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
	
	public void updatePosition()
	{
		x = (int) this.object.getX() + 123;
    	y = (int) this.object.getY() - 12;
	}
	
	public int getScore()
	{
		return this.score;
	}
	
	public void setScore (int score)
	{
		this.score = score;
	}
	
	public void eatGum(Gum obj)
	{
		this.score += obj.getPoint();
		obj.setEaten(true);
	}
	
    @Override
	public void paint(Graphics g) {
    	int size = 17 /*(int) this.object.getWidth()*/;
    	
        super.paint(g);
        int[][] k = spritesManager.getPacmanCoords(this.getDirection());
        g.drawImage(spritesManager.getSpritesSheet(), x, y, x+size, y+size, k[0][0], k[0][1], k[0][2], k[0][3], null);
        g.drawImage(spritesManager.getSpritesSheet(), x+size, y, x+size+size, y+size, k[1][0], k[1][1], k[1][2], k[1][3], null);
        g.drawImage(spritesManager.getSpritesSheet(), x, y+size, x+size, y+size+size, k[2][0], k[2][1], k[2][2], k[2][3], null);
        g.drawImage(spritesManager.getSpritesSheet(), x+size, y+size, x+size+size, y+size+size, k[3][0], k[3][1], k[3][2], k[3][3], null);
    }


}
