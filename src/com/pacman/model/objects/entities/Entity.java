package com.pacman.model.objects.entities;

import com.pacman.model.objects.GameObject;
import com.pacman.model.world.Direction;
import com.pacman.model.world.Level;
import com.pacman.utils.Settings;

public abstract class Entity extends GameObject
{
    protected Direction direction;
    private Double x,y;

    public Entity(double x, double y, double width, double height, Direction dir)
    {
        super(x, y, width, height);
        direction = dir;
    }

    
    public void updatePosition(Direction dir)
    {
        if (dir == Direction.UP)
        {
        	x = getX();
        	y = getY() - Settings.MOVEMENT;

        }
        else if (dir == Direction.DOWN)
        {
            x = getX();
            y = getY() + Settings.MOVEMENT;
        }
        else if (dir == Direction.RIGHT)
        {
            x = y = getX() + Settings.MOVEMENT;
            y = getY();
        }
        else if (dir == Direction.LEFT)
        {
            x = y = getX() - Settings.MOVEMENT;
            y = getY();
        }
        
        hitBox.setRect(x, y, hitBox.getWidth(), hitBox.getHeight());
    }

    public void tunnel(Direction dir)
    {
    	double x = 0, y = 0;
        if (dir == Direction.UP)
        {
        	x = getX(); 
        	y = Level.getHeight() - hitBox.getHeight() - 0.55;
        }
        if (dir == Direction.DOWN)
        {
            x = getX();
            y = 0.55;
        }
        if (dir == Direction.RIGHT)
        {
            x = 0.55;
            y = getY();
        }
        if (dir == Direction.LEFT)
        {
            x = Level.getWidth() - hitBox.getWidth() - 0.55;
            y = getY();
        }

        hitBox.setRect(x, y, hitBox.getWidth(), hitBox.getHeight());
    }
    
    public Direction getDirection()
    {
        return direction;
    }

    public void setDirection(Direction dir)
    {
        direction = dir;
        updateSprite();
    }
    
    
    public abstract void updateSprite();
}
