package com.pacman.app;

import com.pacman.controller.GameController;
import com.pacman.model.Game;
import com.pacman.model.IGame;
import com.pacman.view.IWindow;
import com.pacman.view.Window;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class App
{
    /*
     * It all starts here!
     * The application is based on a MVC design. The objects are like the model, the gamemanager is the controller and the scenes and window are the views.
     */
    public static void main(String[] args)
    {
        IGame game = new Game();
        IWindow window = new Window(game);
        GameController engine = GameController.getInstance(window, game);
        engine.start();
    }

}
