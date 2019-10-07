package com.pacman.controller;

import java.awt.event.KeyEvent;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.pacman.model.IGame;
import com.pacman.model.highscores.Highscores;
import com.pacman.model.highscores.Score;
import com.pacman.model.states.IGameState;
import com.pacman.model.states.StatesName;
import com.pacman.model.world.Direction;
import com.pacman.utils.Settings;
import com.pacman.view.IWindow;
import com.pacman.view.inputs.Input;
import com.pacman.view.inputs.KeyInput;
import com.pacman.view.menus.MenuOption;
import com.pacman.view.menus.MenuType;

/**
 * Classe principale de l'engin de jeu, Engine, gere donc la gameloop de
 * Pac-Man.
 * @Singleton
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 */
public class GameController extends Thread
{
    private IGame game;
    private Input inputs;
    private IWindow window;
 
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private volatile static int fps = 0;
    
    /**
     * @param window the window object where the game will be render.
     * @param game the object that implements the IGame interface. 
     */
    public GameController(IWindow w, IGame g)
    {
        if (g == null || w == null)
        {
            return;
        }
        game = g;
        
        window = w;
        inputs = new Input();
        inputs.addPressedCallback(KeyEvent.getKeyText(KeyInput.P.getValue()), (KeyEvent e) -> pauseButtonPressed(e) );
        inputs.addPressedCallback(KeyEvent.getKeyText(KeyInput.R.getValue()), (KeyEvent e) -> resumeButtonPressed(e) );
        inputs.addPressedCallback(KeyEvent.getKeyText(KeyInput.UP.getValue()), (KeyEvent e) -> arrowsKeysPressed(e) );
        inputs.addPressedCallback(KeyEvent.getKeyText(KeyInput.DOWN.getValue()), (KeyEvent e) -> arrowsKeysPressed(e) );
        inputs.addPressedCallback(KeyEvent.getKeyText(KeyInput.RIGHT.getValue()), (KeyEvent e) -> arrowsKeysPressed(e) );
        inputs.addPressedCallback(KeyEvent.getKeyText(KeyInput.LEFT.getValue()), (KeyEvent e) -> arrowsKeysPressed(e) );
        inputs.addPressedCallback(KeyEvent.getKeyText(KeyInput.ESCAPE.getValue()), (KeyEvent e) -> menuKeyPressed(e) );
        inputs.addPressedCallback(KeyEvent.getKeyText(KeyInput.ENTER.getValue()), (KeyEvent e) -> acceptKeyPressed(e) );
        
        window.addListener(inputs);
    }

	public IWindow getWindow()
	{
		return window;
	}

	public  boolean getIsRunning()
	{
		return isRunning.get();
	}

	/**
	 * Stop the game and the engine thread.
	 */
	public void stopGame()
	{
		try
		{
			isRunning.set(false);
			game.stopThreads();
			window.dispose();
			join(500);
			if (isAlive())
			{
				interrupt();
				throw new InterruptedByTimeoutException();
			}
			
			System.exit(0);

		} catch (InterruptedByTimeoutException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public Input getInputs()
	{
		return inputs;
	}

	/**
	 * Do not call this method directly. You need to call the startGame method. This
	 * method needs to be public because Engine class implements Runnable.
	 * 
	 */
	public void run()
	{
		System.out.println("Start: Game loop Thread");
		init();

		boolean render = false;
		double firstTime = 0;
		double lastTime = getCurrentTimeInMillis();
		double deltaTime = 0;
		double unprocessedTime = 0;
		double sleepTime = 0;

		while (isRunning.get())
		{
			render = false;

			lastTime = firstTime;
			firstTime = getCurrentTimeInMillis();
			deltaTime = firstTime - lastTime;
			unprocessedTime += deltaTime;

			// Pour etre sur que le render et l'update sont synchronisé avec le 60 fps.
			while (unprocessedTime >= Settings.UPDATE_RATE)
			{
				unprocessedTime -= Settings.UPDATE_RATE;
				render = true;
				update();
			}

			if (render)
			{
				synchronized (this)
				{
					notify();
				}
			}
			
			sleepTime = Settings.UPDATE_RATE - deltaTime;
			if (sleepTime <= 0)
			{
				sleepTime = 1;
			}
			rest(sleepTime);
		}
		
		synchronized (this)
		{
			notifyAll();
		}
		
		System.out.println("Stop: Game loop Thread");
	}

	private void init()
	{
		game.init(window, this);
		isRunning.set(true);
	}

	/**
	 * What the engine needs to update at each tick.
	 */
	private void update()
	{
		game.update();
	}

	/**
	 * Sleep method for the engine.
	 * 
	 * @param sleepTime
	 */
	private void rest(double sleepTime)
	{
		try
		{
			Thread.sleep((long) sleepTime);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	private double getCurrentTimeInMillis()
	{
		return System.nanoTime() / 1000000000.0;
	}

	private void pauseButtonPressed(KeyEvent e)
	{
		IGameState currentState = game.getCurrentState();
		if (currentState.getName() == StatesName.PLAY)
		{
			pauseGame();
		}
	}

	private void resumeButtonPressed(KeyEvent e)
	{
		IGameState currentState = game.getCurrentState();
		if (currentState.getName() == StatesName.PAUSE)
		{
			resumeGame();
		}
    }    
    
    /**
     * @param volume Must be between 0 and 100.
     */
    public void changeMusicVolume(int delta)
    {
    	int tmpVolume = Settings.getMusicVolume() + delta;
    	if (tmpVolume >= 0 && tmpVolume <= 100)
    	{
        	Settings.setMusicVolume(tmpVolume);
        	game.setMusicVolume(tmpVolume);
    	}
    }
    
    /**
     * @param volume Must be between 0 and 100.
     */
    public void changeSoundsVolume(int delta)
    {
    	int tmpVolume = Settings.getSoundsVolume() + delta;
    	if (tmpVolume >= 0 && tmpVolume <= 100)
    	{
        	Settings.setSoundsVolume(tmpVolume);
        	game.setSoundsVolume(tmpVolume);
    	}
    }
    
    private void muteMusicPressed() 
    {    	
    	if(!Settings.isMusicMute())
    	{
    		Settings.setMusicMute(true);
    		game.muteMusics();
    	}
    	else
    	{
    		Settings.setMusicMute(false);
    		game.resumeMusics();
    	}
	}

	private void muteSoundsPressed()
	{
		if (!Settings.isSoundsMute())
		{
			Settings.setSoundsMute(true);
			game.muteSounds();
		} else
		{
			Settings.setSoundsMute(false);
			game.resumeSounds();
		}
	}

	private void arrowsKeysPressed(KeyEvent e)
	{
		IGameState currentState = game.getCurrentState();
		if (currentState.getName() == StatesName.PLAY)
		{
			Direction dir = null;
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_UP:
				dir = Direction.UP;
				break;

			case KeyEvent.VK_DOWN:
				dir = Direction.DOWN;
				break;

			case KeyEvent.VK_LEFT:
				dir = Direction.LEFT;
				break;

			case KeyEvent.VK_RIGHT:
				dir = Direction.RIGHT;
				break;
			}

			game.setPacmanDirection(dir);
		} else if (currentState.getName() == StatesName.MAIN_MENU)
		{
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_UP:
				window.previousOption();
				break;

			case KeyEvent.VK_DOWN:
				window.nextOption();
				break;
			}
			
			if (window.getMenuType() == MenuType.AUDIO)
			{

				switch(e.getKeyCode())
				{
					case KeyEvent.VK_LEFT:
						if (window.getMenuOption() == MenuOption.MUSIC_VOLUME)
						{
							changeMusicVolume(-10);
						}
						else if (window.getMenuOption() == MenuOption.SOUND_VOLUME)
						{
							changeSoundsVolume(-10);
						}
						else if (window.getMenuOption() == MenuOption.MUTE_MUSIC)
						{
							muteMusicPressed();
						}
						else if (window.getMenuOption() == MenuOption.MUTE_SOUND)
						{
							muteSoundsPressed();	
						}
						break;
					
					case KeyEvent.VK_RIGHT:
						if (window.getMenuOption() == MenuOption.MUSIC_VOLUME)
						{
							changeMusicVolume(10);
						}
						else if (window.getMenuOption() == MenuOption.SOUND_VOLUME)
						{
							changeSoundsVolume(10);
						}
						else if (window.getMenuOption() == MenuOption.MUTE_MUSIC)
						{
							muteMusicPressed();
						}
						else if (window.getMenuOption() == MenuOption.MUTE_SOUND)
						{
							muteSoundsPressed();	
						}
						break;
				}
			}
		}
		else if (currentState.getName() == StatesName.NEW_HIGHSCORE)
		{
			switch (e.getKeyCode())
			{
			case KeyEvent.VK_UP:
				window.moveSelectionUp();
				break;

			case KeyEvent.VK_DOWN:
				window.moveSelectionDown();
				break;
				
			case KeyEvent.VK_LEFT:
				window.moveSelectionLeft();
				break;
				
			case KeyEvent.VK_RIGHT:
				window.moveSelectionRight();
				break;
			}
		}
    }
	
    private void menuKeyPressed(KeyEvent e)
    {
    	mainMenuGame();
    }
    
    private void acceptKeyPressed(KeyEvent e)
    {
    	MenuOption option = window.getMenuOption();
    	if (game.getCurrentState().getName() == StatesName.MAIN_MENU)
    	{
    		if (option != null)
    		{
            	if (option == MenuOption.START)
            	{
            		game.setState(game.getInitState());
            	}
            	else if (option == MenuOption.RESUME)
            	{
            		game.setState(game.getResumeState());
            	}
            	else if (option == MenuOption.HIGHSCORES)
            	{
            		window.setHighscoresMenu();
            	}
            	else if (option == MenuOption.AUDIO)
            	{
            		window.setAudioMenu();
            	}
            	else if (option == MenuOption.HELP)
            	{
            		window.setHelpMenu();
            	}
            	else if (option == MenuOption.BACK)
            	{
            		window.setMainMenu();
            	}
            	else if (option == MenuOption.MUTE_MUSIC)
            	{
            		muteMusicPressed();
            	}
            	else if (option == MenuOption.MUTE_SOUND)
            	{
            		muteSoundsPressed();	
            	}
            	else if (option == MenuOption.EXIT)
            	{
            		stopGame();
            	}
    		}	
    	}
    	else if (game.getCurrentState().getName() == StatesName.NEW_HIGHSCORE)
    	{
			Highscores.setNew(new Score(game.getPacman().getScore(), window.getPlayerName()));
			game.getPacman().resetScore();
			game.setState(game.getMainMenuState());
    	}
    }
    
    public void mainMenuGame()
    {
    	if (game == null)
    	{
    		return;
    	}
    	IGameState currentState = game.getCurrentState();
    	if (currentState != null && currentState.getName() == StatesName.PLAY)
    	{
    		game.setState(game.getMainMenuState());
    	}
    }
    
    public void pauseGame()
    {
    	if (game == null)
    	{
    		return;
    	}
    	IGameState currentState = game.getCurrentState();
    	if (currentState != null && currentState.getName() == StatesName.PLAY)
    	{
    		game.setState(game.getPauseState());
    	}
    }
    
    public void resumeGame()
    {
    	if (game == null)
    	{
    		return;
    	}
    	IGameState currentState = game.getCurrentState();
    	if (currentState != null && currentState.getName() == StatesName.PAUSE)
    	{
    		game.setState(game.getResumeState());
    	}
    }

	/**
	 * @return the fps
	 */
	public synchronized int getFps()
	{
		return fps;
	}

	public synchronized void setFps(int frames)
	{
		fps = frames;
	}
}
