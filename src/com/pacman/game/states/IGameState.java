package com.pacman.game.states;

import com.pacman.engine.Engine;

// Interface for the state pattern for gameManager.
public interface IGameState 
{
	public void update(Engine engine);
	
	public String getName();
}
