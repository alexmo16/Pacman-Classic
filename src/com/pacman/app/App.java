package com.pacman.app;

import com.pacman.controller.GameController;
import com.pacman.model.Game;
import com.pacman.model.IGame;
import com.pacman.view.Window;

public class App
{
    /*
     * It all starts here!
     * The application is based on a MVC design. The objects are like the model, the gamemanager is the controller and the scenes and window are the views.
     */
    public static void main(String[] args)
    {
        IGame game = new Game();
        Window window = new Window();
        GameController engine = GameController.getInstance(window, game);
        engine.startGame();
    }

}
