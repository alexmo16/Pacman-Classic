package com.pacman.engine;

import java.util.concurrent.atomic.AtomicBoolean;

import com.pacman.engine.EngineUtils;
import com.pacman.engine.IGame;
import com.pacman.engine.Inputs;

/**
 * Classe principale de l'engin de jeu, Engine, gere donc la gameloop de
 * Pac-Man.
 * 
 * @Singleton
 */
public class Engine implements Runnable
{
    private Thread thread;
    private static IGame game;
    private static Inputs inputs;
    private static Window window;
    private static ISettings settings;

    private static AtomicBoolean isRunning = new AtomicBoolean(false);
    private static boolean isPause = false;
    private static boolean isMuted = false;
    private static int[][] tiles;
    private static int tilesH;
    private static int tilesW;

    private static Engine instance;

    /**
     * Constructeur prive puisque c'est un singleton. Il faut passer par getInstance
     */
    private Engine()
    {
    }
    
    /**
     * Getter for the singleton.
     * @param window the window object where the game will be render.
     * @param game the object that implements the IGame interface. 
     * @return Engine
     */
    public static Engine getInstance(Window window, IGame game)
    {
        if (instance == null)
        {
            if (game == null || window == null)
            {
                return null;
            }

            Engine.game = game;
            settings = game.getSettings();
            if (settings == null || settings.getWorldData() == null)
            {
                return null;
            }

            tiles = settings.getWorldData().getTiles();
            tilesH = settings.getWorldData().getHeight();
            tilesW = settings.getWorldData().getWidth();

            Engine.window = window;
            inputs = new Inputs(Engine.window);
            instance = new Engine();
        }

        return instance;
    }

    public static boolean getIsRunning()
    {
        return isRunning.get();
    }

    public static boolean getIsPause()
    {
        return isPause;
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
     * Do not call this method directly. You need to call the startGame method.
     * This method needs to be public because Engine class implements Runnable.
     * 
     */
    public void run()
    {
        init();

        boolean render = false;
        double firstTime = 0;
        double lastTime = EngineUtils.getCurrentTimeInMillis();
        double deltaTime = 0;
        double unprocessedTime = 0;
        double sleepTime = 0;

        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        while (isRunning.get())
        {
            if (isPause)
            {
                freeze();
            }

            render = false;

            firstTime = EngineUtils.getCurrentTimeInMillis();
            deltaTime = firstTime - lastTime;
            lastTime = firstTime;
            unprocessedTime += deltaTime;
            frameTime += deltaTime;

            // Pour etre sur que le render et l'update sont synchronisé avec le 60 fps.
            while (unprocessedTime >= settings.getUpdateRate() )
            {
                unprocessedTime -= settings.getUpdateRate();
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
                window.getFrame().repaint();
                frames++;
            } else
            {
                sleepTime = settings.getUpdateRate() - deltaTime;
                if (sleepTime <= 0)
                {
                    sleepTime = 1;
                }
                rest(sleepTime);
            }
        }

        window.clear();
    }

    public static int[][] getTiles()
    {
        return tiles;
    }

    public static int getHeight()
    {
        return tilesH;
    }

    public static int getWidth()
    {
        return tilesW;
    }

    private void init()
    {
        game.init(window);
        isRunning.set(true);
        isPause = false;
    }

    /**
     * What the engine needs to update at each tick.
     */
    private void update()
    {
        game.update(this);
        inputs.update();
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

    /**
	 * Freeze the thread until isPause is true and isRunning is true.
     */
    private void freeze()
    {
        try
        {
            synchronized (this)
            {
                while (isPause && isRunning.get())
                {
                    wait();
                }
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
