package com.pacman.app;

import com.pacman.engine.Engine;
import com.pacman.engine.IGame;
import com.pacman.engine.Window;
import com.pacman.game.GameManager;

public class App
{
    /*
     * It all starts here!
     * The application is based on a MVC design. The objects are like the model, the gamemanager is the controller and the scenes and window are the views.
     */
    public static void main(String[] args)
    {
        IGame game = new GameManager();
        Window window = new Window(game.getSettings());
        Engine engine = Engine.getInstance(window, game);
        engine.startGame();
    }

}
