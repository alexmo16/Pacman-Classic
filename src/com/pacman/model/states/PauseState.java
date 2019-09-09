package com.pacman.model.states;

import com.pacman.controller.Engine;
import com.pacman.controller.ISettings;
import com.pacman.controller.InputController;
import com.pacman.model.Game;

public class PauseState implements IGameState
{
	private Game gameManager;
	private ISettings settings;
	private String name = "Pause";
	
	public PauseState( Game gm )
	{
		gameManager = gm;
		settings = gm.getSettings();
	}
	
	@Override
	public void update(Engine engine) 
	{
		InputController inputs = engine.getInputs();
		
        gameManager.stopInGameMusics();
		Engine.setIsMuted(true);
		
		if (gameManager.getCanPausedGame() && inputs.isKeyDown(settings.getPauseButton()))
		{
			gameManager.setState(gameManager.getResumeState());
		}
	}

	public String getName()
	{
		return name;
	}
}
