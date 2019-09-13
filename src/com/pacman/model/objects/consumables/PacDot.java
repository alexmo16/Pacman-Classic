package com.pacman.model.objects.consumables;

import com.pacman.model.objects.Sprites;

public class PacDot extends Consumable
{
    public PacDot(double x, double y)
    {
        super(x + 0.25, y + 0.25, 0.5, 0.5);
        points = 10;
        sprite = Sprites.getPacDot();
    }    
}