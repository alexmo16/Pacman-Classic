package com.pacman.unittests.model.threads;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.channels.InterruptedByTimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pacman.model.Sound;
import com.pacman.model.threads.AudioThread;

class AudioThreadTest
{
	private final int WAIT_TIME = 400;
	private final int JOIN_TIME = 1000;
	
	private AudioThread thread;
	
	@BeforeEach
	void createThread()
	{
		thread = new AudioThread();
		thread.start();
	}
	
	@AfterEach
	void stopThread() throws InterruptedException, InterruptedByTimeoutException
	{
		if (thread == null || !thread.isAlive()) return;
		synchronized (thread)
		{
			thread.stopThread();
			thread.notifyAll();
			thread.join(JOIN_TIME);
			if(thread.isAlive())
			{
				thread.interrupt();
				throw new InterruptedByTimeoutException();
			}
		}
	}
	
	@Test
	void testInterruptWhenWaiting() throws InterruptedException
	{
		synchronized (thread)
		{
			thread.wait(WAIT_TIME);
		}
		
		try
		{
			thread.stopThread();
			thread.join(JOIN_TIME);
			if (thread.isAlive())
			{
				thread.interrupt();
				throw new InterruptedByTimeoutException();
			}
		}
		catch (InterruptedByTimeoutException e)
		{
			assertNotNull(e);
			return;
		}
		fail("Should had an exception.");
	}
	
	@Test 
	void testNoExceptionWhenCleanStop() throws InterruptedException
	{
		synchronized (thread)
		{
			thread.wait(WAIT_TIME);
			
			thread.stopThread();
			thread.notify();
			thread.join(JOIN_TIME);
			if (thread.isAlive())
			{
				fail("Should not be alive.");
			}
		}
	}
	
	@Test 
	void testNoExceptionNoMusicStopMusic()
	{
		try 
		{
			synchronized (thread)
			{
				thread.stopMusic();
				thread.wait(WAIT_TIME);
			}
		}
		catch(Exception e)
		{
			fail("Should not have any exception.");
		}
	}
	
	@Test
	void testAddMusic()
	{
		Sound sound = Mockito.mock(Sound.class);
		Mockito.when(sound.playLoopBack()).thenReturn(true);
		thread.addMusic(sound);
	}
	
	@Test
	void testAddNullMusic() throws InterruptedException
	{
		synchronized (thread)
		{
			thread.addMusic(null);
			thread.wait(100);
		}
	}
	
	@Test
	void testPlayMusicWhenThereIsAMusicAlreadyPlaying()
	{
		Sound firstMusic = Mockito.mock(Sound.class);
		Mockito.when(firstMusic.playLoopBack()).thenReturn(true);
		
		thread.addMusic(firstMusic);
		
		Sound secondMusic = Mockito.mock(Sound.class);
		Mockito.when(secondMusic.playLoopBack()).thenReturn(true);
		
		thread.addMusic(secondMusic);
	}
	
	@Test
	void testPlaySound()
	{
		Sound sound = Mockito.mock(Sound.class);
		Mockito.when(sound.play()).thenReturn(true);
		thread.addSound(sound);
	}
	
	@Test
	void testAddNullSound() throws InterruptedException
	{
		synchronized (thread)
		{
			thread.addSound(null);
			thread.wait(100);
		}
	}
	
	@Test
	void testAddSoundInBuffer() throws InterruptedException, InterruptedByTimeoutException
	{
		Sound firstSound = Mockito.mock(Sound.class);
		Mockito.when(firstSound.play()).thenReturn(true);
		
		Sound secondSound = Mockito.mock(Sound.class);
		Mockito.when(secondSound.play()).thenReturn(true);
	
		thread.addSound(firstSound);		
		thread.addSound(secondSound);
		
		synchronized (thread)
		{
			thread.wait(100);
			Mockito.verify(firstSound).play();
			Mockito.verify(secondSound).play();
		}
	}
	
	@Test
	void testStopMusic() throws InterruptedException
	{
		Sound firstSound = Mockito.mock(Sound.class);
		Mockito.when(firstSound.playLoopBack()).thenReturn(true);
		
		thread.addMusic(firstSound);
		synchronized (thread)
		{
			thread.wait(WAIT_TIME);
		}
		
		thread.stopMusic();
		
		Mockito.verify(firstSound).playLoopBack();
		Mockito.verify(firstSound).stop();
	}
}
