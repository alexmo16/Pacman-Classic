package com.pacman.model.objects.entities.behaviours;

import com.pacman.model.objects.entities.Ghost;

public class AmbushBehaviour implements IBehaviour 
{
	private Ghost ghost;
	
	public AmbushBehaviour(Ghost g) 
	{
		ghost = g;
	}
	
	@Override
	public void getNewDirection() {
		// TODO Auto-generated method stub
		
	}

}
