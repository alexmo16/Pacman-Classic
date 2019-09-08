package com.pacman.model.objects;

import com.pacman.model.Settings;
import com.pacman.view.Sprites;

abstract public class StaticObject extends GameObject
{
    protected boolean eaten;
    protected int points;
    protected int[] sprite;
    protected Sprites spritesManager;

    public StaticObject()
    {
        super();
        this.eaten = false;
        this.points = 0;
        this.sprite = null;
        this.spritesManager = null;
    }

    public StaticObject(double x, double y, double width, double height, Settings s)
    {
        super(x, y, width, height, s);
        this.eaten = false;
        this.points = 0;
        this.spritesManager = s.getSpritesManager();
    }

    public int getPoints()
    {
        return points;
    }

    public boolean getEaten()
    {
        return this.eaten;
    }

    public void setEaten(boolean bool)
    {
        this.eaten = bool;
    }

	public int getSprite(int id) 
	{
		return sprite[id];
	}

}
