package com.pacman.model.objects.entities.behaviours;

import com.pacman.model.objects.entities.Ghost;

public class ChaseBehaviour implements IBehaviour
{
	private Ghost ghost;
	
	public ChaseBehaviour(Ghost g) 
	{
		ghost = g;
	}
	
	@Override
	public void getNewDirection() 
	{
		
	}

}
