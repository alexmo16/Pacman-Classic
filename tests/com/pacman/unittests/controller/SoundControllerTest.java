package com.pacman.unittests.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pacman.model.Sound;
import com.pacman.utils.Settings;

class SoundControllerTest 
{
	@BeforeEach
	void testSetMuteToFalse() 
	{
		Settings.setMusicMute(false);
		Settings.setSoundsMute(false);
	}
	
	@Test
	void testEmptyFilePath()
	{
		boolean isException = false;
		try 
		{
			new Sound( "" );
		} catch ( UnsupportedAudioFileException | IOException e ) 
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
		} catch ( UnsupportedAudioFileException | IOException e ) 
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
		} catch ( UnsupportedAudioFileException | IOException e ) 
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
			sound.setVolume(50);
		} catch ( UnsupportedAudioFileException | IOException e ) 
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
		Sound sound = null;
		
		try
		{
			sound = new Sound( "./tests/testAssets/clip.wav" );
			isPlayed = sound.playLoopBack();
			assertTrue( isPlayed );
			
			sound = new Sound( "./tests/testAssets/clip.wav" );
			isPlayed = sound.play();
			assertTrue( isPlayed );
 
		} catch ( UnsupportedAudioFileException | IOException e ) 
		{
			isException = true;
		}
		
		assertFalse( isException );
		assertTrue( isPlayed );
		if (sound != null)
		{
			sound.stop();
		}
	}
	
	@Test
	void testStartStop() 
	{
		boolean isException = false;
		boolean isPlayed = false;
		Sound sound = null;
		try
		{
			sound = new Sound( "./tests/testAssets/clip.wav" );
			isPlayed = sound.playLoopBack();
			sound.setVolume(50);
			sound.stop();
		} catch ( UnsupportedAudioFileException | IOException e )
		{
			isException = true;
		}
		
		assertFalse( isException );
		assertTrue( isPlayed );
		if (sound != null)
		{
			sound.stop();
		}
	}
	
	@Test
	void testMuteLoop() 
	{
		boolean isException = false;
		boolean isPlayed = false;
		Sound sound = null;
		try 
		{
			sound = new Sound( "./tests/testAssets/clip.wav" );
			Settings.setMusicMute(true);
			isPlayed = sound.playLoopBack();
			sound.setVolume(50);
		} catch ( UnsupportedAudioFileException | IOException e ) 
		{
			isException = true;
		}
		
		assertFalse( isException );
		assertTrue( isPlayed );
		if (sound != null)
		{
			sound.stop();
		}
	}
	
	@Test
	void testMute() 
	{
		boolean isException = false;
		boolean isPlayed = false;
		try
		{
			Sound sound = new Sound( "./tests/testAssets/clip.wav" );
			Settings.setMusicMute(true);
			isPlayed = sound.play();
			sound.setVolume(50);
		} catch ( UnsupportedAudioFileException | IOException e ) 
		{
			isException = true;
		}
		
		assertFalse( isException );
		assertTrue( isPlayed );
		Settings.setMusicMute(false);
	}
	
	@Test
	void testCustomCallback() 
	{
		boolean isException = false;
		boolean isPlayed = false;
		AtomicBoolean testBool = new AtomicBoolean( false );
		try 
		{
			Sound sound = new Sound( "./tests/testAssets/clip.wav" );
			LineListener listener = new LineListener() 
			{
				@Override
				public void update(LineEvent event) 
				{
					if ( event.getType() == LineEvent.Type.STOP ) 
					{
						sound.stop();
						testBool.set( true );
		            }
				}
			};
			
			sound.setListener(listener);
			isPlayed = sound.play();
			sound.setVolume(50);
			
			while( sound.getIsRunning() )
			{
				Thread.sleep( 5 );
			}
			
		} catch ( UnsupportedAudioFileException | IOException | InterruptedException e ) 
		{
			isException = true;
		}
		
		assertFalse( isException );
		assertTrue( isPlayed );
	}
	
	@Test
	void testVolume()
	{
		boolean isException = false;
		boolean isPlayed = false;
		try
		{
			Sound sound = new Sound( "./tests/testAssets/clip.wav" );
			sound.setVolume(50);
			isPlayed = sound.play();
			assertEquals(0.5f, sound.getVolume());
		} catch ( UnsupportedAudioFileException | IOException e ) 
		{
			isException = true;
			assertFalse( isException );
		}		
		assertTrue( isPlayed );
	}
}
