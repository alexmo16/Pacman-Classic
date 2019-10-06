package com.pacman.model.objects.entities.behaviours;

import com.pacman.model.objects.entities.Ghost;

public class FearBehaviour implements IBehaviour
{
	private Ghost ghost;

	public FearBehaviour(Ghost g) 
	{
		ghost = g;
	}
	
	@Override
	public void getNewDirection() {
		// TODO Auto-generated method stub
		
	}

}
