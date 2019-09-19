package com.pacman.model.objects.consumables;

import com.pacman.model.objects.Sprites;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class PacDot extends Consumable
{
    public PacDot(double x, double y)
    {
        super(x, y);
        points = 10;
        sprite = Sprites.getPacDot();
    }
    
    @Override
    public void accept(ConsumableVisitor visitor)
    {
    	super.accept(visitor);
    	visitor.visitPacDot(this);
    }
}