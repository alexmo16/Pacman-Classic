package com.pacman.game.states;

import com.pacman.engine.Engine;
import com.pacman.engine.ISettings;
import com.pacman.engine.Inputs;
import com.pacman.game.GameManager;

public class PauseState implements IGameState
{
	private GameManager gameManager;
	private ISettings settings;
	private String name = "Pause";
	
	public PauseState( GameManager gm )
	{
		gameManager = gm;
		settings = gm.getSettings();
	}
	
	@Override
	public void update(Engine engine) 
	{
		Inputs inputs = engine.getInputs();
		
        gameManager.stopInGameMusics();
		Engine.setIsMuted(true);
		
		if (gameManager.getCanPausedGame() && inputs.isKeyDown(settings.getPauseButton()))
		{
			gameManager.togglePauseScreen();
			gameManager.setState(gameManager.getResumeState());
		}
	}

	public String getName()
	{
		return name;
	}
}
