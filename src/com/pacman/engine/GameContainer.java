package com.pacman.engine;

import com.pacman.engine.EngineUtils;

public class GameContainer implements Runnable 
{
	private Thread thread;
	private boolean isRunning = false;
	private boolean isPause = false;
	private final double UPDATE_CAP = 1.0/60.0;
	
	public GameContainer()
	{		
	}
	
	public void start()
	{
		thread = new Thread( this );
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
			while( unprocessedTime >= UPDATE_CAP )
			{
				unprocessedTime -= UPDATE_CAP;
				render = true;
				// TODO Update game
			}
			
			if( render )
			{
				// TODO render game
			}
			else
			{
				rest();
			}
		}
	}
	
	
	private void rest()
	{
		try 
		{
			Thread.sleep( 1 );
		} catch ( InterruptedException e ) 
		{
			e.printStackTrace();
		}
	}
	
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
	
	public static void main(String args[])
	{
		GameContainer container = new GameContainer();
		container.start();
	}
}
