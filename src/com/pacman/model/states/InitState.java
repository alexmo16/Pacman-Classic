package com.pacman.model.states;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import com.pacman.controller.GameController;
import com.pacman.model.Game;

public class InitState implements IGameState
{
	private Game game;
	private String name = "Init";
	
    LineListener startingMusicListener = new LineListener()
    {
        @Override
        public void update(LineEvent event)
        {
            if (event.getType() == LineEvent.Type.STOP)
            {
            	game.setState(game.getPlayingState());
            	game.stopStartingMusic();
            	game.setCanPausedGame(true);
            	game.resumeInGameMusics();
            }
        }
    };
	
	public InitState( Game gm )
	{
		game = gm;
	}
	
	@Override
	public void update(GameController engine)
	{
		game.playStartingMusic(startingMusicListener);
	}

	public String getName()
	{
		return name;
	}
}
