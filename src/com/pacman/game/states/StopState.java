package com.pacman.game.states;

import com.pacman.engine.Engine;
import com.pacman.game.GameManager;

public class StopState implements IGameState 
{
	private GameManager gameManager;
	private String name = "Stop";
	
	public StopState( GameManager gm )
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
