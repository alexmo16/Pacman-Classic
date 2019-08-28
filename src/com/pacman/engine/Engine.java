package com.pacman.engine;

import com.pacman.engine.EngineUtils;
import com.pacman.engine.IGame;
import com.pacman.engine.Inputs;

/**
 * Classe principale de l'engin de jeu,
 * Engine, gère donc la gameloop de Pac-Man.
 */
public class Engine implements Runnable 
{
	private Thread thread;
	private IGame game;
	private Inputs inputs;
	
	private boolean isRunning = false;
	private boolean isPause = false;
	private final double UPDATE_RATE = 1.0/60.0; // pour avoir 60 fps dans notre jeu.
	
	public Engine( IGame game )
	{
		this.game = game;
	}
	
	public void startGame()
	{
		thread = new Thread( this ) ;
		thread.run();
	}
	
	public void stopGame()
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
	
	public void init ()
	{
		inputs = new Inputs();
		game.init();
		isRunning = true;
		isPause = false;
	}
	
	public Inputs getInputs()
	{
		return inputs;
	}
	
	/**
	 *  On ne doit pas appeler cette methode directement. 
	 *  Passer plutot par start.
	 */
	public void run()
	{	
		init();
		
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
			
			// Pour mettre a jour l'affichage seulement si l'Update a ete fait.
			while( unprocessedTime >= UPDATE_RATE )
			{
				unprocessedTime -= UPDATE_RATE;
				render = true;
				update();
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
	
	private void update()
	{
		game.update( this );
		inputs.update();
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
