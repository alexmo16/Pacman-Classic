package com.pacman.model.threads;

import java.util.ArrayList;

import com.pacman.model.Sound;

public class AudioThread extends Thread
{
	private boolean isRunning = false;
	
	private volatile ArrayList<Sound> sounds = new ArrayList<Sound>();
	private volatile Sound music;
	
	@Override
	public void run()
	{
		isRunning = true;
		while(isRunning)
		{
			synchronized (this)
			{
				if(!sounds.isEmpty())
				{
					for ( int index = sounds.size() - 1; index >= 0; index-- )
					{
						if (sounds.isEmpty()) break;
						Sound sound = sounds.remove(sounds.size() - 1);
						sound.play();
					}
				}
				
				if (music != null)
				{
					music.playLoopBack();
				}
				try
				{
					wait();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void stopThread()
	{
		isRunning = false;
	}
	
	public synchronized void playMusic(Sound music)
	{
		if (this.music != null)
		{
			this.music.stop();
		}
		this.music = music;
		this.notify();
	}
	
	public synchronized void playSound(Sound sound)
	{
		if (sound == null) return;
		this.sounds.add(sound);
		this.notify();
	}
	
	public synchronized void stopMusic()
	{
		if (music != null)
		{
			music.stop();
			music = null;
			this.notify();
		}
	}
}
