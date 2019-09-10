package com.pacman.model.states;

// Interface for the state pattern for gameManager.
public interface IGameState 
{
	public void update();
	
	public StatesName getName();
}
