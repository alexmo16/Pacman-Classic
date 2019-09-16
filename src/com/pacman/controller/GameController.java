package com.pacman.controller;

import java.awt.event.KeyEvent;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.pacman.model.IGame;
import com.pacman.model.menus.MenuOption;
import com.pacman.model.states.IGameState;
import com.pacman.model.states.StatesName;
import com.pacman.model.world.Direction;
import com.pacman.utils.Settings;
import com.pacman.view.IWindow;
import com.pacman.view.Input;

/**
 * Classe principale de l'engin de jeu, Engine, gere donc la gameloop de
 * Pac-Man.
 * 
 * @Singleton
 */
public class GameController extends Thread
{
    private static IGame game;
    private static Input inputs;
    private static IWindow window;
 
    private static AtomicBoolean isRunning = new AtomicBoolean(false);
    private static int fps;
    
    
    private static GameController instance;

    /**
     * Constructeur prive puisque c'est un singleton. Il faut passer par getInstance
     */
    private GameController()
    {
    }
    
    /**
     * Getter for the singleton.
     * @param window the window object where the game will be render.
     * @param game the object that implements the IGame interface. 
     * @return Engine
     */
    public static GameController getInstance(IWindow w, IGame g)
    {
        if (instance == null)
        {
            if (g == null || w == null)
            {
                return null;
            }
            game = g;
            
            window = w;
            inputs = new Input();
            inputs.addPressedCallback(KeyEvent.getKeyText(Settings.PAUSE_BUTTON), (KeyEvent e) -> pauseButtonPressed(e) );
            inputs.addPressedCallback(KeyEvent.getKeyText(Settings.RESUME_BUTTON), (KeyEvent e) -> resumeButtonPressed(e) );
            inputs.addPressedCallback(KeyEvent.getKeyText(KeyEvent.VK_UP), (KeyEvent e) -> arrowsKeysPressed(e) );
            inputs.addPressedCallback(KeyEvent.getKeyText(KeyEvent.VK_DOWN), (KeyEvent e) -> arrowsKeysPressed(e) );
            inputs.addPressedCallback(KeyEvent.getKeyText(KeyEvent.VK_RIGHT), (KeyEvent e) -> arrowsKeysPressed(e) );
            inputs.addPressedCallback(KeyEvent.getKeyText(KeyEvent.VK_LEFT), (KeyEvent e) -> arrowsKeysPressed(e) );
            inputs.addPressedCallback(KeyEvent.getKeyText(KeyEvent.VK_K), (KeyEvent e) -> killButtonPressed(e) );
            inputs.addPressedCallback(KeyEvent.getKeyText(KeyEvent.VK_ESCAPE), (KeyEvent e) -> menuKeyPressed(e) );
            inputs.addPressedCallback(KeyEvent.getKeyText(KeyEvent.VK_ENTER), (KeyEvent e) -> acceptKeyPressed(e) );
            
            inputs.addPressedCallback(KeyEvent.getKeyText(KeyEvent.VK_G), (KeyEvent e) -> muteSoundsPressed(e) );
            inputs.addPressedCallback(KeyEvent.getKeyText(KeyEvent.VK_H), (KeyEvent e) -> muteMusicPressed(e) );
            
            window.addListener(inputs);
            instance = new GameController();
        }

        return instance;
    }

	public static boolean getIsRunning()
    {
        return isRunning.get();
    }

    /**
     * Stop the game and the engine thread.
     */
    public static void stopGame()
    {
    	try 
    	{
			game.stopThreads();
			window.dispose();
			
	        isRunning.set(false);
	        instance.join(500);
			if (instance.isAlive())
			{
				instance.interrupt();
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
     * Do not call this method directly. You need to call the startGame method.
     * This method needs to be public because Engine class implements Runnable.
     * 
     */
    public void run()
    {
        init();

        boolean render = false;
        double firstTime = 0;
        double lastTime = getCurrentTimeInMillis();
        double deltaTime = 0;
        double unprocessedTime = 0;
        double sleepTime = 0;

        double frameTime = 0;
        int frames = 0;
        fps = 0;

        while (isRunning.get())
        {
            render = false;

            lastTime  = firstTime;
            firstTime = getCurrentTimeInMillis();
            deltaTime = firstTime - lastTime;
            unprocessedTime += deltaTime;
            frameTime += deltaTime;

            // Pour etre sur que le render et l'update sont synchronisé avec le 60 fps.
            while (unprocessedTime >= Settings.UPDATE_RATE )
            {
                unprocessedTime -= Settings.UPDATE_RATE;
                render = true;
                update();
            }

            if (frameTime >= 1.0)
            {
                frameTime = 0;
                fps = frames;
                frames = 0;
            }

            // Si on a rien a afficher, on sleep.
            if (render)
            {
            	render();
                frames++;
            } else
            {
                sleepTime = Settings.UPDATE_RATE - deltaTime;
                if (sleepTime <= 0)
                {
                    sleepTime = 1;
                }
                rest(sleepTime);
            }
        }
    }

    private void init()
    {
        game.init(window);
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
     * This will render the correct view.
     */
    private void render()
    {
    	window.render();
    }
    
    /**
     * Sleep method for the engine.
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
    
    private static double getCurrentTimeInMillis()
    {
        return System.nanoTime() / 1000000000.0;
    }
    
    private static void pauseButtonPressed(KeyEvent e)
    {
    	IGameState currentState = game.getCurrentState();
    	if ( currentState.getName() == StatesName.PLAY)
		{
    		pauseGame();
		}
    }
    
    private static void resumeButtonPressed(KeyEvent e)
    {
    	IGameState currentState = game.getCurrentState();
    	if ( currentState.getName() == StatesName.PAUSE)
		{
    		resumeGame();
		}
    }    
    
    private static void muteMusicPressed(KeyEvent e) 
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

	private static void muteSoundsPressed(KeyEvent e) 
	{
		if(!Settings.isSoundsMute())
    	{
			Settings.setSoundsMute(true);
    		game.muteSounds();
    	}
    	else
    	{
    		Settings.setSoundsMute(false);
    		game.resumeSounds();
    	}
	}
    
    private static void arrowsKeysPressed(KeyEvent e)
    {
    	IGameState currentState = game.getCurrentState();
		if (currentState.getName() == StatesName.PLAY)
		{
			Direction dir = null;
			switch(e.getKeyCode())
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
		}
		else if (currentState.getName() == StatesName.MAIN_MENU)
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_UP:
				game.mainMenuPrevious();
				break;
				
			case KeyEvent.VK_DOWN:
				game.mainMenuNext();
				break;
			}
		}
    }
    
    private static void killButtonPressed(KeyEvent e)
    {
    	game.killPacman();
    }
    
    private static void menuKeyPressed(KeyEvent e)
    {
    	mainMenuGame();
    }
    
    private static void acceptKeyPressed(KeyEvent e)
    {
    	MenuOption option = game.getCurrentSelection();
    	if (option != null && option == MenuOption.START)
    	{
    		game.setState(game.getInitState());
    	}
    	else if (option != null && option == MenuOption.RESUME)
    	{
    		game.setState(game.getResumeState());
    	}
    	else if (option != null && option == MenuOption.EXIT)
    	{
    		GameController.stopGame();
    	}
    }
    
    public static void mainMenuGame()
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
    
    public static void pauseGame()
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
    
    public static void resumeGame()
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
	public static int getFps() 
	{
		return fps;
	}
}
