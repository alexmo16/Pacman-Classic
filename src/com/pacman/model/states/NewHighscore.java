package com.pacman.model.states;

public class NewHighscore implements IGameState 
{
	private StatesName name = StatesName.NEW_HIGHSCORE;
	
	@Override
	public void update() 
	{
	}

	@Override
	public StatesName getName() 
	{
		return name;
	}
	
}
