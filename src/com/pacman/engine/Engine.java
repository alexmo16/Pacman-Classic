package com.pacman.engine;

import java.awt.Graphics2D;

import com.pacman.engine.EngineUtils;
import com.pacman.engine.IGame;
import com.pacman.engine.Inputs;

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
	private ISettings settings;
	private Renderer renderer;
	
	private static boolean isRunning = false;
	private static boolean isPause = false;
	private static boolean isMuted = false;
	
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
	
	public static boolean getIsRunning()
	{
		return isRunning;
	}
	
	public static boolean getIsPause()
	{
		return isPause;
	}
	
	public static boolean getIsMuted()
	{
		return isMuted;
	}
	
	public static void setIsMuted( boolean isSoundMuted )
	{
		isMuted = isSoundMuted;
	}
	
	public static void toggleMute()
	{
		isMuted = !isMuted;
	}
	
	public void setGame( IGame game )
	{
		this.game = game;
	}
	
	public void startGame()
	{
		if ( game != null && !isRunning )
		{
			thread = new Thread( this ) ;
			thread.run();	
		}
	}
	
	public static void stopGame()
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
	
	public Renderer getRenderer()
	{
		return renderer;
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
		double sleepTime = 0;
		
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
			while( unprocessedTime >= settings.getUpdateRate() )
			{
				unprocessedTime -= settings.getUpdateRate();
				render = true;
				update();
			}
			
			// Si on a rien a afficher, on sleep.
			if( render )
			{
				renderer.clear();
				game.render( renderer );
				window.update();
			}
			else
			{
				sleepTime = settings.getUpdateRate() - deltaTime;
				if ( sleepTime <= 0 )
				{
					sleepTime = 1;
				}
				rest( sleepTime );
			}
		}
		
		window.clear();
	}
	
	private void init ()
	{
		settings = game.getSettings();
		window = new Window( settings );
		inputs = new Inputs( window );
		renderer = new Renderer( (Graphics2D)window.getGraphics(), settings );
		renderer.clear();
		game.init(window);
		isRunning = true;
		isPause = false;
	}
	
	private void update()
	{
		game.update( this );
		inputs.update();
	}
	
	private void rest( double sleepTime )
	{
		try 
		{
			Thread.sleep( (long) sleepTime ); // 1ms, a voir s'il faut modifier cette valeur.
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
