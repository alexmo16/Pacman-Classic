package com.pacman.model.states;

import com.pacman.model.Game;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class MainMenuState implements IGameState 
{
	private Game game;
	private StatesName name = StatesName.MAIN_MENU;
	
	public MainMenuState( Game gm )
	{
		game = gm;
	}
	
	@Override
	public void update()
	{
		if (game == null) return;
		game.stopMusic();
	}

	@Override
	public StatesName getName()
	{
		return name;
	}
}
