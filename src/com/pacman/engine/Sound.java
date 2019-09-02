package com.pacman.engine;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound
{
	private static String filePath;
	private static Clip audioClip;
	private AudioInputStream inputStream;
	
	public Sound( String path ) throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		filePath = path;
		init();
	}
	
	public Sound( String path, boolean loopBack ) throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		filePath = path;
		init();
		if ( loopBack )
		{
			audioClip.loop( Clip.LOOP_CONTINUOUSLY );
		}
	}
	
	private void init() throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		inputStream = AudioSystem.getAudioInputStream( new File( filePath ).getAbsoluteFile() );
		// Cree une reference vers un clip
		audioClip = AudioSystem.getClip();
	}
	
	public boolean play()
	{
		if ( !Engine.getIsMuted() )
		{
			try 
			{
				if ( audioClip.isOpen() )
				{
					audioClip.close();
				}
				
				audioClip.open( inputStream );
			} catch (LineUnavailableException | IOException e) 
			{
				return false;
			}
			audioClip.setFramePosition( 0 );
			audioClip.start();
			return true;
		}
		return false;
	}
	
	public boolean playSynchronously()
	{
		if ( Engine.getIsMuted() )
		{
			return false;
		}
		
		play();
		while( audioClip.getFramePosition() < audioClip.getFrameLength() )
		{
			try 
			{
				Thread.sleep( 5 );
			} catch (InterruptedException e) 
			{
				return false;
			}
		}
		
		return true;
	}
	
	public void stop()
	{
		audioClip.stop();
	}
	
	public void activateLoopBack()
	{
		audioClip.loop( Clip.LOOP_CONTINUOUSLY );
	}
}
