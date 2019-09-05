package com.pacman.game.objects;

import java.awt.geom.Rectangle2D;

import com.pacman.engine.objects.GameObject;
import com.pacman.game.Settings;
import com.pacman.game.SpritesManager;

public abstract class DynamicObject extends GameObject
{

    private static final long serialVersionUID = 1L;
    protected Direction direction;
    static double speed = 0.1;
    protected SpritesManager spritesManager;

    public DynamicObject()
    {
        this.direction = null;
    }

    public DynamicObject(double x, double y, double width, double height, Direction direction, Settings s)
    {
        super(x, y, width, height, s);
        this.direction = direction;
        spritesManager = s.getSpritesManager();
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
    
    public enum Direction
    {
    	RIGHT(0),
        LEFT(1),
        UP(2),
        DOWN(3);

        private final int value;

        Direction(final int newValue)
        {
            value = newValue;
        }

        public int getValue() { return value; }
    }
}
