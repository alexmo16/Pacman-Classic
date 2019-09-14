package com.pacman.model.objects.consumables;

import com.pacman.model.objects.Animation;
import com.pacman.model.objects.Sprites;

public class Energizer extends Consumable implements Animation
{
    int animationState = 0, animationCycles = 3;
    boolean endOfAnimation = false;
	
    public Energizer(double x, double y)
    {
    	super(x + 0.25, y + 0.25, 0.5, 0.5);
        points = 10;
        sprite = Sprites.getEnergizer(0);
    }
    
    @Override
    public void accept(ConsumableVisitor visitor)
    {
    	super.accept(visitor);
    	visitor.visitEnergizer(this);
    }

	@Override
	public void nextSprite() 
	{		
		endOfAnimation = (animationState + 1 == animationCycles) ? true : endOfAnimation;
		endOfAnimation = (animationState == 0) ? false : endOfAnimation;
		animationState += (endOfAnimation ? -1 : 1);
		sprite = Sprites.getEnergizer(animationState);
	}
}