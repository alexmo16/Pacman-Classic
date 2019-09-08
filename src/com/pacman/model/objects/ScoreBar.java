package com.pacman.model.objects;

import com.pacman.model.Settings;
import com.pacman.model.world.Direction;

/**
 * The scorebar displays the score and the direction of the collision
 */
public class ScoreBar
{
	private int score;
    private boolean collision;
    private Direction direction;
    private String state;

    public ScoreBar(Settings s)
    {
        this.score = 0;
        this.collision = false;
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

    public void setCollision(boolean bool, Direction direction)
    {
        this.collision = bool;
        this.direction = direction;
    }
    
    public void setState(String s)
    {
    	state = s;
    }

	public Direction getDirection() 
	{
		return direction;
	}

	public boolean isCollision()
	{
		return collision;
	}

	public String getState() 
	{
		return state;
	}

}
