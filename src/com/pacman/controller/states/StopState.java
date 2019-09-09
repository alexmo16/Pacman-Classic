package com.pacman.controller.states;

import com.pacman.controller.Engine;
import com.pacman.controller.GameController;

public class StopState implements IGameState 
{
	private GameController gameManager;
	private String name = "Stop";
	
	public StopState( GameController gm )
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
