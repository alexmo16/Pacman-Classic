package com.pacman.app;

import com.pacman.engine.Engine;
import com.pacman.engine.IGame;
import com.pacman.engine.Window;
import com.pacman.game.GameManager;

public class Pacman 
{
	/*
	 * It all starts here!
	 */
	public static void main( String[] args ) 
	{
		IGame game = new GameManager();
		Window window = new Window( game.getSettings() );
		Engine engine = Engine.getInstance( window, game );
		engine.startGame();
	}

}
