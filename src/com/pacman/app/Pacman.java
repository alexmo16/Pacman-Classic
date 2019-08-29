package com.pacman.app;

import com.pacman.engine.Engine;
import com.pacman.game.GameManager;

public class Pacman 
{
	/*
	 * It all starts here!
	 */
	public static void main( String[] args ) 
	{
		Engine engine = Engine.getInstance();
		engine.setGame( new GameManager() );
		engine.startGame();
	}

}
