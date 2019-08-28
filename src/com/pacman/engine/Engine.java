package com.pacman.engine;

import com.pacman.engine.EngineUtils;
import com.pacman.engine.IGame;
import com.pacman.game.Settings;

/**
 * Classe principale de l'engin de jeu,
 * Engine, g�re donc la gameloop de Pac-Man.
 */
public class Engine implements Runnable 
{
	private Thread thread;
	private IGame game;
	private Window window;
	private Settings settings;
	
	private boolean isRunning = false;
	private boolean isPause = false;
	
	public Engine( IGame game )
	{
		this.game = game;
	}
	
	public void start()
	{
		settings = new Settings();
		window = new Window( settings );
		thread = new Thread( this ) ;
		thread.run();
	}
	
	public void stop()
	{
		isRunning = false;
		isPause = false;
	}
	
	public void pauseGame()
	{
		isPause = true;
	}
	
	public void resumeGame()
	{
		isPause = false;
	}
	
	public void run()
	{
		isRunning = true;
		isPause = false;
		
		boolean render = false;
		double firstTime = 0;
		double lastTime = EngineUtils.getCurrentTimeInMillis();
		double deltaTime = 0;
		double unprocessedTime = 0;
		
		while( isRunning )
		{
			if ( isPause )
			{
				freeze();
			}
			
			render = false;
			
			firstTime = EngineUtils.getCurrentTimeInMillis();
			deltaTime = firstTime - lastTime;
			lastTime = firstTime;
			unprocessedTime += deltaTime;
			
			// Pour mettre � jour l'affichage seulement si l'Update a �t� fait.
			while( unprocessedTime >= settings.getUPDATE_RATE() )
			{
				unprocessedTime -= settings.getUPDATE_RATE();
				render = true;
				game.update( this );
			}
			
			// Si on a rien � afficher, on sleep.
			if( render )
			{
				window.update();
				// TODO render game
			}
			else
			{
				rest( 1 );
			}
		}
	}
	
	private void rest( int sleepTime )
	{
		try 
		{
			Thread.sleep( sleepTime ); // 1ms, � voir s'il faut modifier cette valeur.
		} catch ( InterruptedException e ) 
		{
			e.printStackTrace();
		}
	}
	
    /**
     * sert � geler le thread tant et aussi longtemps que le jeu roule et que l'�tat de pause est demand�.
     */
	private void freeze()
	{
		try
		{
			synchronized ( this ) 
			{
				while( isPause && isRunning )
				{
					wait();
				}
			}	
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
	}
}
