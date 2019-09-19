package com.pacman.model.states;

/**
 * Interface for the state pattern for gameManager.
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public interface IGameState 
{
	public void update();
	
	public StatesName getName();
}
