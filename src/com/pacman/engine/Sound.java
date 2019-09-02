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
	private LineListener listener;
	
	public Sound( String path ) throws UnsupportedAudioFileException, IOException
	{
		file = new File( path ).getAbsoluteFile();
		inputStream = AudioSystem.getAudioInputStream( file );
		Sound that = this;
		listener = new LineListener() 
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
	
	public boolean play()
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
		audioClip.addLineListener( listener );
		audioClip.start();
		return true;
	}
	
	public boolean playSynchronously()
	{
		if ( Engine.getIsMuted() )
		{
			return false;
		}
		
		play();
		while( audioClip != null && audioClip.getFramePosition() < audioClip.getFrameLength() )
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
		if ( audioClip != null )
		{
			audioClip.stop();
			audioClip.close();	
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
		audioClip.loop( Clip.LOOP_CONTINUOUSLY );
		return true;
	}
}
