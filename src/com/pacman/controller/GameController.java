package com.pacman.controller;

import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import com.pacman.model.states.IGameState;
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
    private static boolean isMuted = false;

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
            window.addListener(inputs);
            instance = new GameController();
        }

        return instance;
    }

    public static boolean getIsRunning()
    {
        return isRunning.get();
    }

    public static boolean getIsMuted()
    {
        return isMuted;
    }

    public static void setIsMuted(boolean isSoundMuted)
    {
        isMuted = isSoundMuted;
    }

    public static void toggleMute()
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

            firstTime = getCurrentTimeInMillis();
            deltaTime = firstTime - lastTime;
            lastTime = firstTime;
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
                // System.out.println(fps);
                frames = 0;
            }

            // Si on a rien a afficher, on sleep.
            if (render)
            {
            	render();
                //window.getFrame().repaint();
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
    	IGameState currentState = game.getCurrentState();
    	if ( currentState.getName() == "Pause" && inputs.isKeyDown(Settings.RESUME_BUTTON))
		{
    		game.setState(game.getResumeState());
		}
    	
    	if ( currentState.getName() == "Play" && inputs.isKeyDown(Settings.PAUSE_BUTTON))
		{
    		game.setState(game.getPauseState());
		}
    	
		if (currentState.getName() == "Play" && inputs.isKeyDown(Settings.MUTED_BUTTON))
        {
            game.toggleUserMuteSounds();
        }
		
		if (currentState.getName() == "Play")
		{
			if (inputs.isKeyDown(KeyEvent.VK_UP))
	        {
	            game.setPacmanDirection(Direction.UP);
	        } else if (inputs.isKeyDown(KeyEvent.VK_DOWN))
	        {
	        	game.setPacmanDirection(Direction.DOWN);
	        } else if (inputs.isKeyDown(KeyEvent.VK_RIGHT))
	        {
	        	game.setPacmanDirection(Direction.RIGHT);
	        } else if (inputs.isKeyDown(KeyEvent.VK_LEFT))
	        {
	        	game.setPacmanDirection(Direction.LEFT);
	        }	
		} 
    	
    	game.update();
        inputs.update();
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
            Thread.sleep((long) sleepTime); // 1ms, a voir s'il faut modifier cette valeur.
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    private static double getCurrentTimeInMillis()
    {
        return System.nanoTime() / 1000000000.0;
    }

}
