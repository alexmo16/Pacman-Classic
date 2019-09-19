package com.pacman.model.objects.consumables;

import java.awt.geom.Rectangle2D;

import com.pacman.model.objects.GameObject;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public abstract class Consumable extends GameObject
{
    protected int points;
    
    public Consumable(double x, double y)
    {
    	super(x, y, new Rectangle2D.Double(x + 0.25, y + 0.25, 0.5, 0.5));
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
