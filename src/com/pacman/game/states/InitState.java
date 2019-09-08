package com.pacman.game.states;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import com.pacman.engine.Engine;
import com.pacman.game.GameManager;

public class InitState implements IGameState
{
	private GameManager gameManager;
	private String name = "Init";
	
    LineListener startingMusicListener = new LineListener()
    {
        @Override
        public void update(LineEvent event)
        {
            if (event.getType() == LineEvent.Type.STOP)
            {
            	gameManager.setState(gameManager.getPlayingState());
                gameManager.stopStartingMusic();
                gameManager.setCanPausedGame(true);
                gameManager.resumeInGameMusics();
            }
        }
    };
	
	public InitState( GameManager gm )
	{
		gameManager = gm;
	}
	
	@Override
	public void update(Engine engine)
	{
		gameManager.playStartingMusic(startingMusicListener);
	}

	public String getName()
	{
		return name;
	}
}
