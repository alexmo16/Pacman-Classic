package com.pacman.model.states;

import com.pacman.controller.Engine;
import com.pacman.model.Game;

public class StopState implements IGameState 
{
	private Game gameManager;
	private String name = "Stop";
	
	public StopState( Game gm )
	{
		gameManager = gm;
	}
	
	@Override
	public void update(Engine engine) 
	{
		// TODO jouer la music de fin quand pacman est mort
		gameManager.stopInGameMusics();
		gameManager.setState(gameManager.getInitState());
	}

	public String getName()
	{
		return name;
	}
}
