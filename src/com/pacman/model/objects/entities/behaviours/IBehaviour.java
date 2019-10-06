package com.pacman.model.objects.entities.behaviours;

public interface IBehaviour 
{
	public enum behavioursID 
	{
		CHASE,
		AMBUSH,
		FEAR,
		RANDOM
	}
	
	public void getNewDirection();
}
