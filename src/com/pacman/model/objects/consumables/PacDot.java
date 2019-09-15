package com.pacman.model.objects.consumables;

import com.pacman.model.objects.Sprites;

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