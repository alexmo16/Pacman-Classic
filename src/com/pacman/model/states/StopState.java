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
		// TODO jouer la music de fin quand pacman est mort
		game.stopInGameMusics();
		game.getPacman().resetLifes();
		game.getPacman().resetScore();
		game.getPacman().respawn();
		
		// Juste pour voir le message gameover
		// TODO enlever ca quand y va avoir les animations et tout svp.
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		game.setState(game.getMainMenuState());
	}

	@Override
	public StatesName getName()
	{
		return name;
	}
}
