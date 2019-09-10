package com.pacman.model.states;

import com.pacman.controller.GameController;
import com.pacman.model.Game;

public class PauseState implements IGameState
{
	private Game gameManager;
	private String name = "Pause";
	
	public PauseState( Game gm )
	{
		gameManager = gm;
	}
	
	@Override
	public void update() 
	{
        gameManager.stopInGameMusics();
		GameController.setIsMuted(true);
	}

	public String getName()
	{
		return name;
	}
}
