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
        if (direction == Direction.UP)
        {
            object.setRect(object.getX(), object.getY() - speed, object.getWidth(), object.getHeight());
        }
        if (direction == Direction.DOWN)
        {
            object.setRect(object.getX(), object.getY() + speed, object.getWidth(), object.getHeight());
        }
        if (direction == Direction.RIGHT)
        {
            object.setRect(object.getX() + speed, object.getY(), object.getWidth(), object.getHeight());
        }
        if (direction == Direction.LEFT)
        {
            object.setRect(object.getX() - speed, object.getY(), object.getWidth(), object.getHeight());
        }
    }

    public static void tunnel(Rectangle2D.Double object, Direction direction)
    {
        switch (direction)
        {
        case RIGHT:
            object.setRect(0.5, object.getY(), object.getWidth(), object.getHeight());
            break;
        case LEFT:
            object.setRect(worldData.getWidth()- object.getWidth() - 0.5, object.getY(), object.getWidth(), object.getHeight());
            break;
        case UP:
            object.setRect(object.getX(), worldData.getHeight() - object.getHeight() - 0.5, object.getWidth(), object.getHeight());
            break;
        case DOWN:
            object.setRect(object.getX(), 0.5, object.getWidth(), object.getHeight());
            break;
        default:
            break;
        }
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
