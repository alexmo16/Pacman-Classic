package com.pacman.engine;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound
{
	private File file;
	private AudioInputStream inputStream;
	private Clip audioClip;
	private LineListener defaultListener;
	private boolean isRunning = false;
	
	
	public Sound( String path ) throws UnsupportedAudioFileException, IOException
	{
		file = new File( path ).getAbsoluteFile();
		inputStream = AudioSystem.getAudioInputStream( file );
		
		Sound that = this;
		defaultListener = new LineListener() 
		{
			@Override
			public void update( LineEvent event ) 
			{
				if ( event.getType() == LineEvent.Type.STOP ) 
				{
	                that.stop();
	            }
			}
		};
	}
	
	/**
	 *  Si vous voulez utilisez le listener par default, il faut mettre null comme valeur. Attention si vous implemente votre propre listener, il faut appeler la methode stop.
	 * @param listener
	 * @return boolean
	 */
	public boolean play( LineListener listener )
	{
		if ( Engine.getIsMuted() )
		{
			return false;
		}
		try 
		{
			inputStream = AudioSystem.getAudioInputStream( file );
			audioClip = AudioSystem.getClip();
			if ( audioClip.isOpen() )
			{
				audioClip.close();
			}
				
			audioClip.open( inputStream );
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) 
		{
			return false;
		}
		audioClip.setFramePosition( 0 );
		listener = listener == null ? defaultListener : listener;
		audioClip.addLineListener( listener );
		isRunning = true;
		audioClip.start();
		return true;
	}
	
	public void stop()
	{
		if ( audioClip != null )
		{
			audioClip.stop();
			audioClip.close();
			isRunning = false;
		}
		audioClip = null;
		inputStream = null;
	}
	
	public boolean playLoopBack()
	{
		if ( Engine.getIsMuted() )
		{
			return false;
		}
		try
		{
			inputStream = AudioSystem.getAudioInputStream( file );
			audioClip = AudioSystem.getClip();
			if ( audioClip == null )
			{
				return false;
			}
			
			if ( audioClip.isOpen() )
			{
				audioClip.close();
			}
				
			audioClip.open( inputStream );
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) 
		{
			return false;
		}
		audioClip.setFramePosition( 0 );
		isRunning = true;
		audioClip.loop( Clip.LOOP_CONTINUOUSLY );
		return true;
	}
	
	public boolean getIsRunning()
	{
		return isRunning;
	}
}
