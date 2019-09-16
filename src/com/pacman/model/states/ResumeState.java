package com.pacman.model.states;

import com.pacman.model.Game;
import com.pacman.model.threads.TimerThread;

public class ResumeState implements IGameState 
{
	private Game game;
	private StatesName name = StatesName.RESUME;
	private TimerThread timer;
	private int resumeWaitTime = 3; //seconds
	
	public ResumeState( Game gm )
	{
		game = gm;
	}
	
	@Override
	public void update() 
	{	
		if (timer == null)
		{
			game.setResumeTime(resumeWaitTime);
			timer = new TimerThread(resumeWaitTime);
			timer.start();
		}
		
		if (timer.isAlive())
		{
			synchronized (timer)
			{
				int currentTime = (int)(timer.getTime() / 1000);
				game.setResumeTime(resumeWaitTime - currentTime);
			}
		}
		else
		{
			timer = null;
			game.setState(game.getPlayingState());
			
			if (!game.getIsUserMuted())
			{
				game.resumeAudio();
			}
		}
	}

	@Override
	public StatesName getName()
	{
		return name;
	}
}
