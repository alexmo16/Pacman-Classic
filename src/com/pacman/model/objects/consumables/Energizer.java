package com.pacman.model.objects.consumables;

import com.pacman.model.objects.Animation;
import com.pacman.model.objects.Sprites;

public class Energizer extends Consumable implements Animation
{
    int animationState = 0, animationCycles = 3;
    long timeSinceLastSpriteUpdate = 0;
    boolean endOfAnimation = false;
	
    public Energizer(double x, double y)
    {
    	super(x, y);
        points = 50;
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
	
	@Override
	public int spritePerSecond()
	{
		return 5;
	}

	@Override
	public long getTimeSinceLastSpriteUpdate() 
	{
		return timeSinceLastSpriteUpdate;
	}
	
	@Override
	public void setTimeSinceLastSpriteUpdate(long time)
	{
		timeSinceLastSpriteUpdate = time;
	}
}