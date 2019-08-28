package com.pacman.app;

import com.pacman.engine.Engine;
import com.pacman.game.GameManager;

public class Pacman 
{
	/*
	 * It all starts here!
	 */
	public static void main(String[] args) 
	{
		Engine engine = new Engine(new GameManager());
		engine.startGame();
	}

}
