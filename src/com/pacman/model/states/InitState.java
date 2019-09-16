package com.pacman.model.states;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import com.pacman.model.Game;
import com.pacman.model.objects.entities.Ghost;

public class InitState implements IGameState
{
	private Game game;
	private StatesName name = StatesName.INIT;
	
    LineListener startingMusicListener = new LineListener()
    {
        @Override
        public void update(LineEvent event)
        {
            if (event.getType() == LineEvent.Type.STOP)
            {
            	game.setState(game.getPlayingState());
            	game.stopStartingMusic();
            	game.resumeInGameMusics();
            }
        }
    };
	
	public InitState( Game gm )
	{
		game = gm;
	}
	
	@Override
	public void update()
	{
		// Logic when pacman finish a level, to generate the next one.
		if (game.isPacmanWon())
		{
			game.setPacmanWon(false);
			game.getPacman().respawn();
			int levelNumber = Integer.parseInt(game.getCurrentLevel().getName());
			levelNumber++;	
			game.loadLevel(Integer.toString(levelNumber));
		}
		
		game.getPacman().respawn();
		
		for (int i = 0; i < 4; i++)
	    {
			((Ghost) game.getEntities().get(i)).respawn();
	    }
		
		
		game.playStartingMusic(startingMusicListener);
	}

	@Override
	public StatesName getName()
	{
		return name;
	}
}
