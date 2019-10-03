  package com.pacman.model.objects.entities;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.pacman.model.objects.GameObject;
import com.pacman.model.world.Direction;
import com.pacman.model.world.Level;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public abstract class Entity extends GameObject
{
    protected Direction direction;
	protected Point2D.Double spawn;
	protected Direction spawnDirection;

    public Entity(double x, double y, Direction dir)
    {
        super(x, y, new Point2D.Double(0.5, 0.5), new Rectangle2D.Double(x + 0.05, y + 0.05, 0.9, 0.9));
        direction = dir;
        spawn = new Point2D.Double(x,  y);
        spawnDirection = dir;
    }

    public abstract void updateSprite();
    
    public abstract int[] getAuthTiles();
    
    public void updatePosition(Direction dir)
    {
    	double x = 0, y = 0;
        if (dir == Direction.UP)
        {
        	x = getHitBoxX();
        	y = getHitBoxY() - getSpeed();
        }
        else if (dir == Direction.DOWN)
        {
            x = getHitBoxX();
            y = getHitBoxY() + getSpeed();
        }
        else if (dir == Direction.RIGHT)
        {
            x = getHitBoxX() + getSpeed();
            y = getHitBoxY();
        }
        else if (dir == Direction.LEFT)
        {
            x = getHitBoxX() - getSpeed();
            y = getHitBoxY();
        }

        setPosition(x, y);
    }
    
    public double getSpeed() {
    	return 0;
    }

    public void tunnel(Direction dir)
    {
    	double x = 0, y = 0;
        if (dir == Direction.UP)
        {
        	x = getHitBoxX(); 
        	y = Level.getHeight() - hitBox.getHeight() - 0.55;
        }
        if (dir == Direction.DOWN)
        {
            x = getHitBoxX();
            y = 0.55;
        }
        if (dir == Direction.RIGHT)
        {
            x = 0.55;
            y = getHitBoxY();
        }
        if (dir == Direction.LEFT)
        {
            x = Level.getWidth() - hitBox.getWidth() - 0.55;
            y = getHitBoxY();
        }

        setPosition(x, y);
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
}