package com.pacman.model.states;

import com.pacman.model.Game;

public class MainMenuState implements IGameState 
{
	private Game game;
	private StatesName name = StatesName.MAIN_MENU;
	
	public MainMenuState( Game gm )
	{
		game = gm;
	}
	
	@Override
	public void update()
	{
		if (game == null)
		{
			return;
		}
	}

	@Override
	public StatesName getName()
	{
		return name;
	}
}
