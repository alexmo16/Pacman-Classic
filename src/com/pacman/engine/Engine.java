package com.pacman.engine;

import com.pacman.engine.EngineUtils;
import com.pacman.engine.IGame;

/**
 * Classe principale de l'engin de jeu,
 * Engine, gère donc la gameloop de Pac-Man.
 */
public class Engine implements Runnable 
{
	private Thread thread;
	private IGame game;
	
	private boolean isRunning = false;
	private boolean isPause = false;
	private final double UPDATE_RATE = 1.0/60.0; // pour avoir 60 fps dans notre jeu.
	
	public Engine( IGame game )
	{
		this.game = game;
	}
	
	public void start()
	{
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
			
			// Pour mettre à jour l'affichage seulement si l'Update a été fait.
			while( unprocessedTime >= UPDATE_RATE )
			{
				unprocessedTime -= UPDATE_RATE;
				render = true;
				game.update( this );
			}
			
			// Si on a rien à afficher, on sleep.
			if( render )
			{
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
			Thread.sleep( sleepTime ); // 1ms, à voir s'il faut modifier cette valeur.
		} catch ( InterruptedException e ) 
		{
			e.printStackTrace();
		}
	}
	
    /**
     * sert à geler le thread tant et aussi longtemps que le jeu roule et que l'état de pause est demandé.
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
