package com.pacman.game.objects;

import com.pacman.game.Settings;

abstract public class StaticObject extends GameObject
{

    private static final long serialVersionUID = 1L;
    protected boolean eaten;
    protected int points;

    public StaticObject()
    {
        super();
        this.eaten = false;
        this.points = 0;
    }

    public StaticObject(double x, double y, double width, double height, Settings s)
    {
        super(x, y, width, height, s);
        this.eaten = false;
        this.points = 0;
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
