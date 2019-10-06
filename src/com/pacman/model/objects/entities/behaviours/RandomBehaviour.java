package com.pacman.model.objects.entities.behaviours;

import java.util.Random;

import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.world.Direction;

public class RandomBehaviour implements IBehaviour 
{
	private Random random;
	private int randomInt;
	private Ghost ghost;
	
	public RandomBehaviour(Ghost g) 
	{
		ghost = g;
	}
	
	@Override
	public void getNewDirection() 
	{
		random = new Random();
		randomInt = random.nextInt(4);
		if (Direction.values()[(randomInt+2)%4] != ghost.getDirection())
		{
			ghost.setDirection(Direction.values()[randomInt]);
		} 
		else 
		{
			getNewDirection();
		}	
	}

}
