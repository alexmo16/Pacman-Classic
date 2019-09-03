package com.pacman.game.objects;

import java.awt.Graphics;

import com.pacman.engine.objects.SceneObject;
import com.pacman.game.Settings;

public class ScoreBar extends SceneObject
{

    private static final long serialVersionUID = 1L;

    private int score = 0;
    private boolean collision = false;
    private String direction;

    public ScoreBar(Settings s)
    {

        s.getSpritesManager();;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public void addPointsScore(int points)
    {
        this.score += points;
    }

    public void setCollision(boolean bool, String direction)
    {
        this.collision = bool;
        this.direction = direction;
    }

	@Override
	protected void paintComponent(Graphics g) 
	{
        g.drawString("score :" + score, 0, 10);
        if (this.collision)
        {
            g.drawString("collision :" + direction, 100, 10);
        }
		
	}

}
