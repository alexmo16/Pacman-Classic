package com.pacman.model.states;

import com.pacman.model.Game;

public class PauseState implements IGameState
{
	private Game game;
	private StatesName name = StatesName.PAUSE;
	
	public PauseState( Game gm )
	{
		game = gm;
	}
	
	@Override
	public void update() 
	{
		game.muteAudio();
	}

	@Override
	public StatesName getName()
	{
		return name;
	}
}
