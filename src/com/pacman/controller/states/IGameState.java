package com.pacman.controller.states;

import com.pacman.controller.Engine;

// Interface for the state pattern for gameManager.
public interface IGameState 
{
	public void update(Engine engine);
	
	public String getName();
}
