package com.pacman.unittests.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pacman.controller.GameController;
import com.pacman.controller.IGame;
import com.pacman.view.Window;


class EngineTest 
{
	
	static IGame game;
	static Window window;
	static GameController engine;
	
	private static void testCreateEngineWithoutGame() 
	{
		GameController e = GameController.getInstance( Mockito.mock( Window.class ), null );
		assertEquals( null, e );
	}
	
	@BeforeAll
	static void setupTests()
	{	
		window = Mockito.mock( Window.class );
		Mockito.when( window.getFrame() ).thenReturn( new JFrame() );
		
		game = Mockito.mock( IGame.class );
		
		EngineTest.testCreateEngineWithoutGame();
		
		engine = GameController.getInstance( window, game );
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
		assertTrue( GameController.getIsRunning() );
		
		synchronized( this ) 
        { 
			GameController.stopGame();
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
		
		assertTrue( GameController.getIsRunning() );
		
		engine.startGame();
		
		assertTrue( GameController.getIsRunning() );
		
		synchronized( this ) 
        { 
			GameController.stopGame();
        } 
	}
}
