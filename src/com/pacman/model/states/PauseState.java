package com.pacman.model.states;

import com.pacman.controller.GameController;
import com.pacman.model.Game;
import com.pacman.utils.Settings;
import com.pacman.view.Input;

public class PauseState implements IGameState
{
	private Game gameManager;
	private String name = "Pause";
	
	public PauseState( Game gm )
	{
		gameManager = gm;
	}
	
	@Override
	public void update(GameController engine) 
	{
		Input inputs = engine.getInputs();
		
        gameManager.stopInGameMusics();
		GameController.setIsMuted(true);
		
		if (gameManager.getCanPausedGame() && inputs.isKeyDown(Settings.PAUSE_BUTTON))
		{
			gameManager.setState(gameManager.getResumeState());
		}
	}

	public String getName()
	{
		return name;
	}
}
