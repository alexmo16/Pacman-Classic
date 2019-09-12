package com.pacman.model.objects.consumables;

import com.pacman.utils.Settings;

public class Energizer extends Consumable
{
    public Energizer(double x, double y)
    {
    	super(x + 0.25, y + 0.25, 0.5, 0.5);
        points = 10;
        sprite = Settings.SPRITES.getEnergizer(0);
    }    
}