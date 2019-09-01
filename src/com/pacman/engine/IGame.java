package com.pacman.engine;

import com.pacman.engine.Engine;

/**
 * Sert interface de jeu que l'engin utilise afin d'intéragir avec le jeu.
 */
public interface IGame 
{	
	public void init(Window window);
	
	public void update( Engine engine );
	
	public void render( Renderer renderer );
	
	public ISettings getSettings();
}
