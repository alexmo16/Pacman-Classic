package com.pacman.unittests.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pacman.controller.GameController;
import com.pacman.model.IGame;
import com.pacman.view.Window;


class EngineTest 
{
	
	static IGame game;
	static Window window;
	static GameController engine;
	
	@BeforeAll
	static void setupTests()
	{	
		window = Mockito.mock( Window.class );
		Mockito.when( window.getFrame() ).thenReturn( new JFrame() );
		
		game = Mockito.mock( IGame.class );
		
		engine = new GameController( window, game );
	}
	
	@Test
	void testStartGame() 
	{
		engine.start();
		try 
		{
			Thread.sleep( 1000 );
		} catch (InterruptedException e) 
		{
			fail();
		}
		assertTrue( engine.getIsRunning() );
		
		synchronized( this ) 
        { 
			engine.stopGame();
        } 
	}
}
