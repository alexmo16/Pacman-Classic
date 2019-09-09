package com.pacman.controller.states;

import com.pacman.controller.Engine;
import com.pacman.controller.GameController;

public class ResumeState implements IGameState 
{
	private GameController gameManager;
	private String name = "Resume";
	
	public ResumeState( GameController gm )
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
