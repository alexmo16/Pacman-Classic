package com.pacman.game.objects;

import com.pacman.engine.objects.GameObject;
import com.pacman.game.Settings;
import com.pacman.game.SpritesManager;

abstract public class StaticObject extends GameObject
{

    private static final long serialVersionUID = 1L;
    protected boolean eaten;
    protected int points;
    protected SpritesManager spritesManager;

    public StaticObject()
    {
        super();
        this.eaten = false;
        this.points = 0;
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

}
