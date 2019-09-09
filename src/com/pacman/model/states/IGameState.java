package com.pacman.model.states;

import com.pacman.controller.Engine;

// Interface for the state pattern for gameManager.
public interface IGameState 
{
	public void update(Engine engine);
	
	public String getName();
}
