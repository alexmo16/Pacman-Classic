package com.pacman.game.states;

import com.pacman.engine.Engine;
import com.pacman.game.GameManager;

public class ResumeState implements IGameState 
{
	private GameManager gameManager;
	private String name = "Resume";
	
	public ResumeState( GameManager gm )
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
