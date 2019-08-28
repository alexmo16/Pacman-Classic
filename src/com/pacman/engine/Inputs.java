package com.pacman.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class Inputs implements KeyListener {
	
	private static final int SUPPORTED_KEYS = 256; 

	private final boolean[] keys = new boolean[ SUPPORTED_KEYS ];
	private final boolean[] lastPressedKeys = new boolean[ SUPPORTED_KEYS ];
	
	private char typed = 0;
	private HashMap< String, Integer > keyBind = new HashMap<>();
	
	public void update()
	{
		System.arraycopy( keys, 0, lastPressedKeys, 0, SUPPORTED_KEYS );
		typed = (char)0;
	}
	
	public char getTyped()
	{
		return typed;
	}
	
	public boolean isKeyDown( String keyName )
	{
		return isKeyDown( keyBind.get( keyName ) );
	}
	
	public boolean isKeyDown( int keyCode )
	{
		return keys[ keyCode ] && !lastPressedKeys[ keyCode ];
	}
	
	public boolean isKeyUp( String keyName )
	{
		return isKeyUp( keyBind.get( keyName ) );
	}
	
	public boolean isKeyUp( int keyCode )
	{
		return !keys[ keyCode ] && lastPressedKeys[ keyCode ];
	}
	
	@Override
	public void keyTyped(KeyEvent e) 
	{
		typed = e.getKeyChar();
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if ( e.getKeyCode() < SUPPORTED_KEYS )
		{
			keys[ e.getKeyCode() ] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if ( e.getKeyCode() < SUPPORTED_KEYS )
		{
			keys[ e.getKeyCode() ] = false;
		}
	}
	
}
