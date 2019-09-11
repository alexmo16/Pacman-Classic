package com.pacman.model.states;

import com.pacman.model.Game;

public class StopState implements IGameState 
{
	private Game gameManager;
	private StatesName name = StatesName.STOP;
	
	public StopState( Game gm )
	{
		gameManager = gm;
	}
	
	@Override
	public void update() 
	{
		// TODO jouer la music de fin quand pacman est mort
		gameManager.stopInGameMusics();
		gameManager.getPacman().respawn();
		gameManager.setState(gameManager.getInitState());
	}

	public StatesName getName()
	{
		return name;
	}
}
