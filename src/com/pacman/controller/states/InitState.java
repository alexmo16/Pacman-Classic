package com.pacman.controller.states;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import com.pacman.controller.Engine;
import com.pacman.controller.GameController;

public class InitState implements IGameState
{
	private GameController game;
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
	
	public InitState( GameController gm )
	{
		game = gm;
	}
	
	@Override
	public void update(Engine engine)
	{
		game.playStartingMusic(startingMusicListener);
	}

	public String getName()
	{
		return name;
	}
}
