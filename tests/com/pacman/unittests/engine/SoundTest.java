package com.pacman.unittests.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.pacman.engine.Sound;

import org.junit.jupiter.api.Test;

class SoundTest 
{
	@Test
	void testEmptyFilePath() 
	{
		boolean isException = false;
		try 
		{
			new Sound( "" );
		} catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e ) 
		{
			isException = true;
		}
		
		assertTrue( isException );
	}
	
	@Test
	void testInvalidFilePath() 
	{
		boolean isException = false;
		try 
		{
			new Sound( "./tests/ladaojkndnoasd/test.wav" );
		} catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e ) 
		{
			isException = true;
		}
		
		assertTrue( isException );
	}
	
	@Test
	void testCorruptedFile() 
	{
		boolean isException = false;
		try 
		{
			new Sound( "./tests/testAssets/cipCorrupted.wav" );
		} catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e ) 
		{
			isException = true;
		}
		
		assertTrue( isException );
	}

	@Test
	void testFileOKNoLoopback() 
	{
		boolean isException = false;
		try 
		{
			Sound sound = new Sound( "./tests/testAssets/clip.wav" );
			sound.play();
		} catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e ) 
		{
			isException = true;
		}
		
		assertFalse( isException );
	}
	
	@Test
	void testFileOKWithLoopback() 
	{
		boolean isException = false;
		try 
		{
			Sound sound = new Sound( "./tests/testAssets/clip.wav", true );
			sound.play();
			
			sound = new Sound( "./tests/testAssets/clip.wav", false );
			sound.play();
 
		} catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e ) 
		{
			isException = true;
		}
		
		assertFalse( isException );
	}
	
	@Test
	void testStartStop() 
	{
		boolean isException = false;
		try 
		{
			Sound sound = new Sound( "./tests/testAssets/clip.wav", true );
			sound.play();
			sound.stop();
		} catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e ) 
		{
			isException = true;
		}
		
		assertFalse( isException );
	}
}
