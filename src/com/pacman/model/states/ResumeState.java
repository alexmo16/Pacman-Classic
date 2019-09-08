package com.pacman.model.states;

import com.pacman.controller.Engine;
import com.pacman.model.Game;

public class ResumeState implements IGameState 
{
	private Game gameManager;
	private String name = "Resume";
	
	public ResumeState( Game gm )
	{
		gameManager = gm;
	}
	
	@Override
	public void update(Engine engine) 
	{
		if ( !gameManager.isUserMuted )
		{
			Engine.setIsMuted(false);
			gameManager.resumeInGameMusics();
		}
		
		gameManager.setState(gameManager.getPlayingState());
	}

	public String getName()
	{
		return name;
	}
}
