package com.pacman.model.objects.entities;

import com.pacman.model.objects.Sprites;
import com.pacman.model.world.Direction;
import com.pacman.model.world.GhostType;

public class Ghost extends Entity
{
	GhostType type;
	
    public Ghost(double x, double y, GhostType t)
    {
        super(x, y, 1, 1, Direction.UP);
        type = t;
        sprite = Sprites.getGhost(type, direction, 0);
    }
    
    @Override
    public void updateSprite()
    {
    	sprite = Sprites.getGhost(type, direction, 0);
    }
}
