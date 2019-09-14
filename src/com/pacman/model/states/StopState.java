package com.pacman.model.states;

import com.pacman.model.Game;

public class StopState implements IGameState 
{
	private Game game;
	private StatesName name = StatesName.STOP;
	
	public StopState( Game gm )
	{
		game = gm;
	}
	
	@Override
	public void update() 
	{
		game.stopInGameMusics();
		
		// Juste pour voir le message gameover
		// TODO enlever ca quand y va avoir les animations et tout svp.
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean hasWon = game.isPacmanWon();
		if (hasWon)
		{
			game.setState(game.getInitState());
		}
		// gameover
		else
		{
           	game.getPacman().resetLives();
			game.getPacman().resetScore();
			game.loadLevel("1");
			game.setState(game.getMainMenuState());
		}
	}

	@Override
	public StatesName getName()
	{
		return name;
	}
}
