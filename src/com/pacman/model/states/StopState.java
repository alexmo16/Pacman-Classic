package com.pacman.model.states;

import com.pacman.model.Game;
import com.pacman.model.threads.TimerThread;

/**
 *
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class StopState implements IGameState 
{
	private Game game;
	TimerThread timer;
	private StatesName name = StatesName.STOP;
	
	public StopState( Game gm )
	{
		game = gm;
	}
	
	@Override
	public void update() 
	{
		game.stopMusic();
		
		// Juste pour voir le message gameover
		if (timer == null)
		{
			timer = new TimerThread(2);
			timer.start();
		}
		
		if (timer.isAlive())
		{
			return;
		}
		
		timer = null;
		
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
