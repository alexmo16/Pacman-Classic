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
		AudioInputStream inputStream = AudioSystem.getAudioInputStream( new File( filePath ).getAbsoluteFile() );
		// Cree une reference vers un clip
		audioClip = AudioSystem.getClip();		
		audioClip.open( inputStream );
		
	}
	
	public boolean play()
	{
		if ( !Engine.getIsMuted() )
		{
			audioClip.setFramePosition( 0 );
			audioClip.start();
			return true;
		}
		
		return false;
	}
	
	public void stop()
	{
		audioClip.stop();
	}
}
