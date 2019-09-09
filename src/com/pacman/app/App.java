package com.pacman.app;

import com.pacman.controller.Engine;
import com.pacman.controller.GameController;
import com.pacman.controller.IGame;
import com.pacman.controller.WindowController;

public class App
{
    /*
     * It all starts here!
     * The application is based on a MVC design. The objects are like the model, the gamemanager is the controller and the scenes and window are the views.
     */
    public static void main(String[] args)
    {
        IGame game = new GameController();
        WindowController window = new WindowController(game.getSettings());
        Engine engine = Engine.getInstance(window, game);
        engine.startGame();
    }

}
