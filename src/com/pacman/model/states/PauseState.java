package com.pacman.model.states;

import com.pacman.model.Game;
import com.pacman.model.threads.TimerThread;
import com.pacman.utils.Settings;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class PauseState implements IGameState
{
	private Game game;
	private StatesName name = StatesName.PAUSE;
	
	public PauseState( Game gm )
	{
		game = gm;
	}
	
	@Override
	public void update() 
	{
		if (!Settings.isMusicMute() || !Settings.isSoundsMute())
		{
			game.muteAudio();
		}
		
		TimerThread intermissionThread = game.getIntermissionThread();
		if (intermissionThread != null)
		{
			intermissionThread.setPause(true);
		}
	}

	@Override
	public StatesName getName()
	{
		return name;
	}
}
