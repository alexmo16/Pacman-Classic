package com.pacman.model.states;

import com.pacman.controller.GameController;
import com.pacman.model.Game;

public class PauseState implements IGameState
{
	private Game gameManager;
	private StatesName name = StatesName.PAUSE;
	
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

	public StatesName getName()
	{
		return name;
	}
}
