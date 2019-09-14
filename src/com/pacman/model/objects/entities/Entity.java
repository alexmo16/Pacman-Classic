package com.pacman.model.objects.entities;

import com.pacman.model.objects.GameObject;
import com.pacman.model.world.Direction;
import com.pacman.model.world.Level;
import com.pacman.utils.Settings;

public abstract class Entity extends GameObject
{
    protected Direction direction;

    public Entity(double x, double y, double width, double height, Direction dir)
    {
        super(x, y, width, height);
        direction = dir;
    }

    public void updatePosition(Direction dir)
    {
    	double x = 0, y = 0;
        if (dir == Direction.UP)
        {
        	x = hitBox.getX(); 
        	y = hitBox.getY() - Settings.MOVEMENT;
        }
        else if (dir == Direction.DOWN)
        {
            x = hitBox.getX();
            y = hitBox.getY() + Settings.MOVEMENT;
        }
        else if (dir == Direction.RIGHT)
        {
            x = hitBox.getX() + Settings.MOVEMENT;
            y = hitBox.getY();
        }
        else if (dir == Direction.LEFT)
        {
            x = hitBox.getX() - Settings.MOVEMENT;
            y = hitBox.getY();
        }
        
        hitBox.setRect(x, y, hitBox.getWidth(), hitBox.getHeight());
    }

    public void tunnel(Direction dir)
    {
    	double x = 0, y = 0;
        if (dir == Direction.UP)
        {
        	x = hitBox.getX(); 
        	y = Level.getHeight() - hitBox.getHeight() - 0.5;
        }
        if (dir == Direction.DOWN)
        {
            x = hitBox.getX();
            y = 0.5;
        }
        if (dir == Direction.RIGHT)
        {
            x = 0.5;
            y = hitBox.getY();
        }
        if (dir == Direction.LEFT)
        {
            x = Level.getWidth() - hitBox.getWidth() - 0.5;
            y = hitBox.getY();
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
