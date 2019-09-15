  package com.pacman.model.objects.entities;

import com.pacman.model.objects.GameObject;
import com.pacman.model.world.Direction;
import com.pacman.model.world.Level;
import com.pacman.utils.Settings;

public abstract class Entity extends GameObject
{
    protected Direction direction;
    private Double x,y;
    private double xCoord,yCoord;
    
    protected static double xOffsetSprite;
    protected static double yOffsetSprite;
    





    public Entity(double x, double y, double width, double height, Direction dir)
    {
        super(x, y, width, height);
    	xOffsetSprite = (2 * Settings.TILES_WIDTH - width) / 2;
    	yOffsetSprite = (2 * Settings.TILES_HEIGHT - height) / 2;
        this.xCoord = x - xOffsetSprite;
        this.yCoord = y - yOffsetSprite;
        direction = dir;
    }

    
    public void updatePosition(Direction dir)
    {
        if (dir == Direction.UP)
        {
        	x = getX();
        	y = getY() - Settings.MOVEMENT;
        	this.setCoord(x, y);

        }
        else if (dir == Direction.DOWN)
        {
            x = getX();
            y = getY() + Settings.MOVEMENT;
            this.setCoord(x, y);
        }
        else if (dir == Direction.RIGHT)
        {
            x = y = getX() + Settings.MOVEMENT;
            y = getY();
            this.setCoord(x, y);
        }
        else if (dir == Direction.LEFT)
        {
            x = y = getX() - Settings.MOVEMENT;
            y = getY();
            this.setCoord(x, y);
        }
        //System.out.println(getXCoord());
        //System.out.println(getYCoord());
        hitBox.setRect(x, y, hitBox.getWidth(), hitBox.getHeight());
    }

    public void tunnel(Direction dir)
    {
    	double x = 0, y = 0;
        if (dir == Direction.UP)
        {
        	x = getX(); 
        	y = Level.getHeight() - hitBox.getHeight() - 0.55;
        	this.setCoord(x, y);
        }
        if (dir == Direction.DOWN)
        {
            x = getX();
            y = 0.55;
            this.setCoord(x, y);
        }
        if (dir == Direction.RIGHT)
        {
            x = 0.55;
            y = getY();
            this.setCoord(x, y);
        }
        if (dir == Direction.LEFT)
        {
            x = Level.getWidth() - hitBox.getWidth() - 0.55;
            y = getY();
            this.setCoord(x, y);
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
    
    public double getXCoord()
    {
    	return this.xCoord;
    }
    
    public double getYCoord()
    {
    	return this.yCoord;
    }
    
    public void setCoord(double x, double y)
    {
    	this.xCoord = x - xOffsetSprite ;
    	this.yCoord = y - yOffsetSprite ;
    }
    
    public void updateCoord()
    {
    	this.xCoord = this.getX() - xOffsetSprite ;
    	this.yCoord = this.getY() - yOffsetSprite ;
    }
    
    public abstract int[] getAuthTiles();
}