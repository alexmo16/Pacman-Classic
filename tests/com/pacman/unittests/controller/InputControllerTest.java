package com.pacman.unittests.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pacman.view.Input;
import com.pacman.view.Window;

class InputControllerTest 
{
	static Input input;
	
	static Window window;
	static JFrame frame;
	
	@BeforeAll
	static void init()
	{
		window = Mockito.mock( Window.class );
		Mockito.when( window.getFrame() ).thenReturn( new JFrame() );
		input = new Input();
		frame = InputControllerTest.window.getFrame();
		frame.addKeyListener(input);
	}
	
	@Test
	void testKeyPressed() 
	{
		KeyEvent key = new KeyEvent( frame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P' );
		frame.getKeyListeners()[0].keyPressed(key);
		
		boolean[] keys = input.getCurrentKeys();
		boolean[] lastKeys = input.getLastKeys();
		
		assertTrue( keys[ key.getKeyCode() ] );
		assertFalse( lastKeys[ key.getKeyCode() ] );
		
		assertTrue( input.isKeyDown(KeyEvent.VK_P ) );
		assertFalse( input.isKeyUp(KeyEvent.VK_P ) );
		input.update();
	}
	
	@Test
	void testKeyPressedInvalidKey() 
	{
		KeyEvent key = new KeyEvent( frame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 400, 'P' );
		frame.getKeyListeners()[0].keyPressed(key);
		
		boolean[] keys = input.getCurrentKeys();
		boolean[] lastKeys = input.getLastKeys();
		
		assertTrue( keys.length < key.getKeyCode() );
		assertTrue( lastKeys.length < key.getKeyCode() );
	
		assertFalse( input.isKeyDown(KeyEvent.VK_P ) );
		assertFalse( input.isKeyUp(KeyEvent.VK_P ) );
		input.update();
	}
	
	@Test
	void testKeyReleased()
	{
		KeyEvent keyPressed = new KeyEvent( frame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P' );
		KeyEvent keyReleased = new KeyEvent( frame, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P' );
		frame.getKeyListeners()[0].keyPressed( keyPressed );
		input.update();
		frame.getKeyListeners()[0].keyReleased( keyReleased );
		
		boolean[] keys = input.getCurrentKeys();
		boolean[] lastKeys = input.getLastKeys();
		
		assertFalse( keys[ keyPressed.getKeyCode() ] );
		assertTrue( lastKeys[ keyReleased.getKeyCode() ] );
		
		assertFalse( input.isKeyDown(KeyEvent.VK_P ) );
		assertTrue( input.isKeyUp(KeyEvent.VK_P ) );
		input.update();
	}
	
	@Test
	void testKeyReleasedInvalidKey()
	{
		KeyEvent keyPressed = new KeyEvent( frame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 400, 'P' );
		KeyEvent keyReleased = new KeyEvent( frame, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, 400, 'P' );
		frame.getKeyListeners()[0].keyPressed( keyPressed );
		input.update();
		frame.getKeyListeners()[0].keyReleased( keyReleased );
		
		boolean[] keys = input.getCurrentKeys();
		boolean[] lastKeys = input.getLastKeys();
		
		assertTrue( keys.length < keyPressed.getKeyCode() );
		assertTrue( lastKeys.length < keyReleased.getKeyCode() );
		
		assertFalse( input.isKeyDown(KeyEvent.VK_P ) );
		assertFalse( input.isKeyUp(KeyEvent.VK_P ) );
		input.update();
	}
}
