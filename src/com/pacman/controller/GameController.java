package com.pacman.controller;

import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import com.pacman.model.IGame;
import com.pacman.model.states.IGameState;
import com.pacman.model.states.StatesName;
import com.pacman.model.world.Direction;
import com.pacman.utils.Settings;
import com.pacman.view.Input;
import com.pacman.view.Window;

/**
 * Classe principale de l'engin de jeu, Engine, gere donc la gameloop de
 * Pac-Man.
 * 
 * @Singleton
 */
public class GameController implements Runnable
{
    private Thread thread;
    private static IGame game;
    private static Input inputs;
    private static Window window;
 
    private static AtomicBoolean isRunning = new AtomicBoolean(false);
    private volatile static boolean isMuted = false;

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
    public static GameController getInstance(Window w, IGame g)
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
            inputs.addPressedCallback(KeyEvent.getKeyText(Settings.MUTED_BUTTON), (KeyEvent e) -> muteButtonPressed(e) );
            inputs.addPressedCallback(KeyEvent.getKeyText(KeyEvent.VK_UP), (KeyEvent e) -> arrowsKeysPressed(e) );
            inputs.addPressedCallback(KeyEvent.getKeyText(KeyEvent.VK_DOWN), (KeyEvent e) -> arrowsKeysPressed(e) );
            inputs.addPressedCallback(KeyEvent.getKeyText(KeyEvent.VK_RIGHT), (KeyEvent e) -> arrowsKeysPressed(e) );
            inputs.addPressedCallback(KeyEvent.getKeyText(KeyEvent.VK_LEFT), (KeyEvent e) -> arrowsKeysPressed(e) );
            inputs.addPressedCallback(KeyEvent.getKeyText(KeyEvent.VK_K), (KeyEvent e) -> killButtonPressed(e) );
            
            window.addListener(inputs);
            instance = new GameController();
        }

        return instance;
    }

    public static boolean getIsRunning()
    {
        return isRunning.get();
    }

    public synchronized static boolean getIsMuted()
    {
        return isMuted;
    }

    public synchronized static void setIsMuted(boolean isSoundMuted)
    {
        isMuted = isSoundMuted;
    }

    public synchronized static void toggleMute()
    {
        isMuted = !isMuted;
    }

    /*
     * start the game engine thread and by the same way the game.
     */
    public void startGame()
    {
        if (game != null && !isRunning.get())
        {
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Stop the game and the engine thread.
     */
    public static void stopGame()
    {
        isRunning.set(false);
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
        int fps = 0;

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
                //System.out.println(fps);
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
    
    private static void muteButtonPressed(KeyEvent e)
    {
    	IGameState currentState = game.getCurrentState();
    	if ( currentState.getName() == StatesName.PLAY )
    	{
    		game.toggleUserMuteSounds();
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
    }
    
    private static void killButtonPressed(KeyEvent e)
    {
    	game.killPacman();
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
}
