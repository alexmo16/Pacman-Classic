package com.pacman.game.objects;

import java.awt.Graphics;

import com.pacman.engine.objects.GameObject;
import com.pacman.game.Settings;
import com.pacman.game.SpritesManager;

abstract public class StaticObject extends GameObject
{

    private static final long serialVersionUID = 1L;
    protected boolean eaten;
    protected int points;
    protected int[] sprite;
    protected SpritesManager spritesManager;

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
    
    @Override
    protected void paintComponent(Graphics g)
    {
        int tileSize = Math.min(getHeight() / worldData.getHeight(), getWidth() / worldData.getWidth());
        if ((tileSize & 1) != 0)
        {
            tileSize--;
        }

        int x = ((int) object.getX() * tileSize) + (getWidth() - (tileSize * worldData.getWidth())) / 2;
        int y = ((int) object.getY() * tileSize) + (getHeight() - (tileSize * worldData.getHeight())) / 2;
        g.drawImage(spritesManager.getSpritesSheet(), x, y, x + tileSize, y + tileSize, this.sprite[0], this.sprite[1], this.sprite[2], this.sprite[3], null);
    }

}
