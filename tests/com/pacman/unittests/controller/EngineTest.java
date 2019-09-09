package com.pacman.unittests.controller;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pacman.controller.Engine;
import com.pacman.controller.IGame;
import com.pacman.controller.ISettings;
import com.pacman.controller.WindowController;
import com.pacman.model.world.Data;

class Settings implements ISettings 
{

	@Override
	public String getTitle() 
	{
		return null;
	}

	@Override
	public int getMutedButton() 
	{
		return 0;
	}

	@Override
	public int getPauseButton() 
	{
		return 0;
	}

	@Override
	public int getMinWindowWidth() 
	{
		return 0;
	}

	@Override
	public int getMinWindowHeight() 
	{
		return 0;
	}

	@Override
	public float getScale() 
	{
		return 0;
	}

	@Override
	public double getUpdateRate() 
	{
		return 0;
	}

	@Override
	public Data getWorldData() 
	{
		return new Data( null );
	}

	@Override
	public int[] getAuthTiles() {
		return null;
	}
}

class EngineTest 
{
	
	static IGame game;
	static ISettings settings;
	static WindowController window;
	static Engine engine;
	
	private static void testCreateEngineWithoutGame() 
	{
		Engine e = Engine.getInstance( Mockito.mock( WindowController.class ), null );
		assertEquals( null, e );
	}
	
	private static void testCreateEngineWithoutSettings() 
	{
		Mockito.when( game.getSettings() ).thenReturn( null );
		
		Engine e = Engine.getInstance( Mockito.mock( WindowController.class ), game );
		assertEquals( null, e );
		
		Mockito.when( game.getSettings() ).thenReturn( settings );
	}
	
	@BeforeAll
	static void setupTests()
	{	
		window = Mockito.mock( WindowController.class );
		Mockito.when( window.getFrame() ).thenReturn( new JFrame() );
		
		game = Mockito.mock( IGame.class );
		settings = new Settings();
		Mockito.when( game.getSettings() ).thenReturn( settings );
		
		EngineTest.testCreateEngineWithoutGame();
		EngineTest.testCreateEngineWithoutSettings();
		
		engine = Engine.getInstance( window, game );
	}
	
	@Test
	void testStartGame() 
	{
		engine.startGame();
		try 
		{
			Thread.sleep( 1000 );
		} catch (InterruptedException e) 
		{
			fail();
		}
		assertTrue( Engine.getIsRunning() );
		assertFalse( Engine.getIsPause() );
		
		synchronized( this ) 
        { 
			Engine.stopGame();
        } 
	}
	
	@Test
	void testDoubleStartGame() 
	{
		engine.startGame();
		try 
		{
			Thread.sleep( 1000 );
		} catch (InterruptedException e) 
		{
			fail();
		}
		
		assertTrue( Engine.getIsRunning() );
		assertFalse( Engine.getIsPause() );
		
		engine.startGame();
		
		assertTrue( Engine.getIsRunning() );
		assertFalse( Engine.getIsPause() );
		
		synchronized( this ) 
        { 
			Engine.stopGame();
        } 
	}
}
