package com.pacman.model.states;

import com.pacman.model.Game;

public class StopState implements IGameState 
{
	private Game game;
	private StatesName name = StatesName.STOP;
	
	public StopState( Game gm )
	{
		game = gm;
	}
	
	@Override
	public void update() 
	{
		// TODO jouer la music de fin quand pacman est mort
		game.stopInGameMusics();
		game.getPacman().respawn();
		game.setState(game.getInitState());
	}

	@Override
	public StatesName getName()
	{
		return name;
	}
}
