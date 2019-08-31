package com.pacman.unittests.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.pacman.engine.Engine;
import com.pacman.engine.Sound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SoundTest 
{
	@BeforeEach
	void testSetMuteToFalse()
	{
		Engine.setIsMuted( false );
	}
	
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
		boolean isPlayed = false;
		try 
		{
			Sound sound = new Sound( "./tests/testAssets/clip.wav" );
			isPlayed = sound.play();
		} catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e ) 
		{
			isException = true;
		}
		
		assertFalse( isException );
		assertTrue( isPlayed );
	}
	
	@Test
	void testFileOKWithLoopback() 
	{
		boolean isException = false;
		boolean isPlayed = false;
		try 
		{
			Sound sound = new Sound( "./tests/testAssets/clip.wav", true );
			isPlayed = sound.play();
			assertTrue( isPlayed );
			
			sound = new Sound( "./tests/testAssets/clip.wav", false );
			isPlayed = sound.play();
			assertTrue( isPlayed );
 
		} catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e ) 
		{
			isException = true;
		}
		
		assertFalse( isException );
		assertTrue( isPlayed );
	}
	
	@Test
	void testStartStop() 
	{
		boolean isException = false;
		boolean isPlayed = false;
		try 
		{
			Sound sound = new Sound( "./tests/testAssets/clip.wav", true );
			isPlayed = sound.play();
			sound.stop();
		} catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e ) 
		{
			isException = true;
		}
		
		assertFalse( isException );
		assertTrue( isPlayed );
	}
	
	@Test
	void testMute() 
	{
		boolean isException = false;
		boolean isPlayed = false;
		try 
		{
			Sound sound = new Sound( "./tests/testAssets/clip.wav", true );
			Engine.setIsMuted( true );
			isPlayed = sound.play();
		} catch ( UnsupportedAudioFileException | IOException | LineUnavailableException e ) 
		{
			isException = true;
		}
		
		assertFalse( isException );
		assertFalse( isPlayed );
	}
}
