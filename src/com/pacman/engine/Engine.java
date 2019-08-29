package com.pacman.engine;

import com.pacman.engine.EngineUtils;
import com.pacman.engine.IGame;
import com.pacman.engine.Inputs;
import com.pacman.game.Settings;

/**
 * Classe principale de l'engin de jeu,
 * Engine, gere donc la gameloop de Pac-Man.
 * @Singleton
 */
public class Engine implements Runnable
{
	private Thread thread;
	private IGame game;
	private Inputs inputs;
	private Window window;
	private Settings settings;
	
	private boolean isRunning = false;
	private boolean isPause = false;
	
	private static Engine instance;
	
	private Engine() {} // parce que c'est un singleton
	
	public static Engine getInstance()
	{
		if ( instance == null )
		{
			instance = new Engine();		
		}
		
		return instance;
	}
	
	public boolean getIsRunning()
	{
		return isRunning;
	}
	
	public boolean getIsPause()
	{
		return isPause;
	}
	
	public void setGame( IGame game )
	{
		this.game = game;
	}
	
	public void startGame()
	{
		if ( game != null && !isRunning )
		{
			settings = new Settings();
			window = new Window( settings );
			thread = new Thread( this ) ;
			thread.run();	
		}
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
	
	public Inputs getInputs()
	{
		return inputs;
	}
	
	/**
	 *  On ne doit pas appeler cette methode directement. 
	 *  Passer plutot par startGame.
	 */
	public void run()
	{	
		if ( game == null )
		{
			return;
		}
		
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
			while( unprocessedTime >= settings.getUPDATE_RATE() )
			{
				unprocessedTime -= settings.getUPDATE_RATE();
				render = true;
				update();
			}
			
			// Si on a rien a afficher, on sleep.
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
	
	private void init ()
	{
		inputs = new Inputs( window );
		game.init();
		isRunning = true;
		isPause = false;
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
			Thread.sleep( sleepTime ); // 1ms, a voir s'il faut modifier cette valeur.
		} catch ( InterruptedException e ) 
		{
			e.printStackTrace();
		}
	}
	
    /**
     * sert a geler le thread tant et aussi longtemps que le jeu roule et que l'�tat de pause est demand�.
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
