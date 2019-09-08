package com.pacman.game.objects;

import java.awt.Color;
import java.awt.Graphics;

import com.pacman.engine.objects.SceneObject;
import com.pacman.game.Settings;
import com.pacman.game.SpritesManager;

/**
 * The scorebar displays the score and the direction of the collision
 */
public class ScoreBar extends SceneObject
{
	private static final long serialVersionUID = 5855573757545455969L;
	
	private final int mazeHeight,
	  				  mazeWidth;
	private int score;
    private boolean collision;
    private DynamicObject.Direction direction;
    private SpritesManager spritesManager;
    private String state;

    public ScoreBar(Settings s)
    {
		mazeHeight = s.getWorldData().getHeight();
		mazeWidth = s.getWorldData().getWidth();
        this.score = 0;
        this.collision = false;
        this.spritesManager = s.getSpritesManager();
    }

    public void setScore(int score)
    {
        this.score = score;
    }
    
    public int getScore() {
        return this.score;
    }

    public void addPointsScore(int points)
    {
        this.score += points;
    }

    public void setCollision(boolean bool, DynamicObject.Direction direction)
    {
        this.collision = bool;
        this.direction = direction;
    }
    
    public void setState(String s)
    {
    	state = s;
    }

	@Override
	protected void paintComponent(Graphics g) 
	{
        int tileSize = 54 * getHeight() / 100;
        if ( (tileSize & 1) != 0 ) { tileSize--; }
        
        int x = (getWidth() - mazeWidth * tileSize) / 2;
        int y = (getHeight() - tileSize) / 2;
        
        String s = new String("score " + score);
        if (this.collision) { s += " collision " + direction;}
        int [] k;
        for (int i = 0; i < s.length(); i++)
        {
        	if (s.charAt(i) != ' ')
        	{
        		k = spritesManager.getCharacterCoords(s.charAt(i));
        		g.drawImage(spritesManager.getSpritesSheet(), x + i * tileSize, y, x + i * tileSize + tileSize, y + tileSize, k[0], k[1], k[2], k[3], null);
        	}
        }
        if (state != null)
        {
            g.drawString("State :" + state, 200, 10);
        }
	}

}
