package com.pacman.model.objects.consumables;

import com.pacman.model.objects.GameObject;

public abstract class Consumable extends GameObject
{
    protected int points;
    
    protected static double consumableWidth = 0.5,
                            consumableHeight = 0.5,
                            xOffset = (1 - consumableWidth) / 2,
                            yOffset = (1 - consumableHeight) / 2;
    
    public Consumable(double x, double y)
    {
    	super(x + xOffset, y + yOffset, consumableWidth, consumableHeight);
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
