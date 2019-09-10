package com.pacman.model.states;

import com.pacman.controller.GameController;
import com.pacman.model.Game;

public class ResumeState implements IGameState 
{
	private Game gameManager;
	private StatesName name = StatesName.RESUME;
	
	public ResumeState( Game gm )
	{
		gameManager = gm;
	}
	
	@Override
	public void update() 
	{
		if ( !gameManager.isUserMuted )
		{
			GameController.setIsMuted(false);
			gameManager.resumeInGameMusics();
		}
		
		gameManager.setState(gameManager.getPlayingState());
	}

	public StatesName getName()
	{
		return name;
	}
}
