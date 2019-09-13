package com.pacman.model.objects.consumables;

import com.pacman.model.objects.GameObject;

public class Consumable extends GameObject
{
    protected int points;
    
    public Consumable(double x, double y, double width, double height)
    {
    	super(x, y, width, height);
        points = 0;
    }
    
    public int getPoints()
    {
        return points;
    }
    
    public void accept(ConsumableVisitor visitor)
    {
    	visitor.visitDefault(this);
    }
}
