package com.pacman.model.objects.entities;

import com.pacman.model.objects.Animation;
import com.pacman.model.objects.Sprites;
import com.pacman.model.world.Direction;
import com.pacman.model.world.GhostType;

public class Ghost extends Entity implements Animation
{
    private int animationState = 0, animationCycles = 2;
	private GhostType type;
	
    public Ghost(double x, double y, GhostType t)
    {
        super(x, y, 1, 1, Direction.UP);
        type = t;
        updateSprite();
    }
    
    @Override
    public void updateSprite()
    {
    	sprite = Sprites.getGhost(type, direction, animationState);
    }
    
	@Override
	public void nextSprite() 
	{
		animationState = (animationState + 1) % animationCycles;
		updateSprite();
	}
}
