package com.pacman.engine;

import com.pacman.engine.Engine;
import com.pacman.game.Settings;

/**
 * Sert interface de jeu que l'engin utilise afin d'intéragir avec le jeu.
 */
public interface IGame 
{	
	public void init();
	
	public void update( Engine engine );
	
	public void render( Renderer renderer );
}
