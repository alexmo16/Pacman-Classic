package com.pacman.model.states;

import com.pacman.controller.GameController;
import com.pacman.model.Game;

public class ResumeState implements IGameState 
{
	private Game game;
	private StatesName name = StatesName.RESUME;
	
	public ResumeState( Game gm )
	{
		game = gm;
	}
	
	@Override
	public void update() 
	{
		if ( !game.isUserMuted() )
		{
			GameController.setIsMuted(false);
			game.resumeInGameMusics();
		}
		
		game.setState(game.getPlayingState());
	}

	@Override
	public StatesName getName()
	{
		return name;
	}
}
