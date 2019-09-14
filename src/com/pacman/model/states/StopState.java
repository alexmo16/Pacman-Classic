package com.pacman.model.states;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import com.pacman.model.Game;

public class StopState implements IGameState 
{
	private Game game;
	private StatesName name = StatesName.STOP;
	
	 LineListener deathSoundListener = new LineListener()
	    {
	        @Override
	        public void update(LineEvent event)
	        {
	            if (event.getType() == LineEvent.Type.STOP)
	            {
	            	game.setState(game.getMainMenuState());
	            	game.getPacman().resetLives();
	    			game.getPacman().resetScore();
	            }
	        }
	    };
	
	public StopState( Game gm )
	{
		game = gm;
	}
	
	@Override
	public void update() 
	{
		// TODO jouer la music de fin quand pacman est mort
		game.stopInGameMusics();
		boolean hasWon = game.isPacmanWon();
		if (hasWon)
		{
			game.setState(game.getInitState());
		}
		else
		{
			game.playDeathMusic(deathSoundListener);
		}
		

		
		// Juste pour voir le message gameover
		// TODO enlever ca quand y va avoir les animations et tout svp.
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public StatesName getName()
	{
		return name;
	}
}
