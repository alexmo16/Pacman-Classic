package com.pacman.model.states;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import com.pacman.model.Game;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class InitState implements IGameState
{
	private Game game;
	private StatesName name = StatesName.INIT;
	private volatile boolean init = true;
	
    LineListener startingMusicListener = new LineListener()
    {
        @Override
        public void update(LineEvent event)
        {
            if (event.getType() == LineEvent.Type.STOP)
            {
            	game.setState(game.getPlayingState());
            	game.stopMusic();
            	game.playInGameMusic();
            	init = true;
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
			int levelNumber = Integer.parseInt(game.getCurrentLevel().getName());
			levelNumber++;	
			game.loadLevel(Integer.toString(levelNumber));
		}
		
		game.getPacman().respawn();
		
		game.getCollisionQueue().clear();
        game.getCollisionNextPacmanQueue().clear();
        
        game.getConsumableQueue().clear();
        game.getcollisionConsumableQueue().clear();
        
        game.getCollisionGhostQueue().clear();
        game.getGhostQueue().clear();
        
        game.setPacmanXBlinky(0);
		game.setPacmanYBlinky(0);
		
		game.setPacmanXInky(0);
		game.setPacmanYInky(0);
		
		for (int i = 0; i < game.getGhosts().size(); i++)
	    {
			game.getGhosts().get(i).respawn();
	    }
		
		if (init)
		{			
			game.resumeAudio();
			game.playStartingMusic(startingMusicListener);
			init = false;
		}
	}

	@Override
	public StatesName getName()
	{
		return name;
	}
}
