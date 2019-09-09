package com.pacman.model.states;

import com.pacman.controller.GameController;

// Interface for the state pattern for gameManager.
public interface IGameState 
{
	public void update(GameController engine);
	
	public String getName();
}
