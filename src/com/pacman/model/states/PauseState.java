package com.pacman.model.states;

import com.pacman.model.Game;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class PauseState implements IGameState
{
	private Game game;
	private StatesName name = StatesName.PAUSE;
	
	public PauseState( Game gm )
	{
		game = gm;
	}
	
	@Override
	public void update() 
	{
		game.muteAudio();
	}

	@Override
	public StatesName getName()
	{
		return name;
	}
}
