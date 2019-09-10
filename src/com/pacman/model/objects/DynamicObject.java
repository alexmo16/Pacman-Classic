package com.pacman.model.objects;

import java.awt.geom.Rectangle2D;

import com.pacman.model.world.Direction;
import com.pacman.utils.Settings;
import com.pacman.view.Sprites;

public abstract class DynamicObject extends GameObject
{
    protected Direction direction;
    static double speed = 0.1;
    protected Sprites spritesManager;

    public DynamicObject()
    {
        this.direction = null;
    }
    
    public static double getSpeed() {
        return speed;
    }

    public DynamicObject(double x, double y, double width, double height, Direction direction)
    {
        super(x, y, width, height);
        this.direction = direction;
        spritesManager = Settings.SPRITES;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public void setDirection(Direction dir)
    {
        direction = dir;
    }

    public static void updatePosition(Rectangle2D.Double object, Direction direction)
    {
    	double x = 0, y = 0;
        if (direction == Direction.UP)
        {
        	x = object.getX(); 
        	y = object.getY() - speed;
        }
        if (direction == Direction.DOWN)
        {
            x = object.getX();
            y = object.getY() + speed;
        }
        if (direction == Direction.RIGHT)
        {
            x = object.getX() + speed;
            y = object.getY();
        }
        if (direction == Direction.LEFT)
        {
            x = object.getX() - speed;
            y = object.getY();
        }
        
        object.setRect(x, y, object.getWidth(), object.getHeight());
    }

    public static void tunnel(Rectangle2D.Double object, Direction direction)
    {
    	double x = 0, y = 0;
        if (direction == Direction.UP)
        {
        	x = object.getX(); 
        	y = worldData.getHeight() - object.getHeight() - 0.5;
        }
        if (direction == Direction.DOWN)
        {
            x = object.getX();
            y = 0.5;
        }
        if (direction == Direction.RIGHT)
        {
            x = 0.5;
            y = object.getY();
        }
        if (direction == Direction.LEFT)
        {
            x = worldData.getWidth()- object.getWidth() - 0.5;
            y = object.getY();
        }
        
        object.setRect(x, y, object.getWidth(), object.getHeight());
    }
}
