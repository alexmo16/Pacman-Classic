package com.pacman.model.threads;

import java.util.ArrayList;

import com.pacman.model.Sound;

/**
 * AUDIOTHREAD = CREATED, CREATED = (start ->RUNNING), 
 * RUNNING = (wait ->WAITING
 * 		     |stop ->TERMINATED), 
 * PROCESSSOUNDS = (getSound->playSound ->RUNNING
 *                 |getMusic->playMusic ->RUNNING 
 *                 |getSound->RUNNING 
 *                 |getMusic->RUNNING),
 * WAITING = (notify->PROCESSSOUNDS), 
 * TERMINATED = STOP.
 */
public class AudioThread extends Thread
{
	private boolean isRunning = false;

	private volatile ArrayList<Sound> sounds = new ArrayList<Sound>();
	private volatile Sound music;

	private synchronized Sound getSound()
	{
		if (sounds.isEmpty())
			return null;
		return sounds.remove(sounds.size() - 1);
	}

	private synchronized Sound getMusic()
	{
		return music;
	}

	@Override
	public void run()
	{
		isRunning = true;
		System.out.println("Start: Audio Thread");
		while (isRunning)
		{
			synchronized (this)
			{
				for (int index = sounds.size() - 1; index >= 0; index--)
				{
					Sound sound = getSound();
					if (sound == null) continue;
					// playSound in fsp.
					sound.play();
				}
				
				Sound music = getMusic();
				if (music != null)
				{
					// playMusic in fsp.
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
		System.out.println("Stop: Audio Thread");
	}

	public void stopThread()
	{
		isRunning = false;
	}

	public synchronized void addMusic(Sound music)
	{
		if (this.music != null)
		{
			this.music.stop();
		}
		this.music = music;
		this.notify();
	}

	public synchronized void addSound(Sound sound)
	{
		if (sound == null)
			return;
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
