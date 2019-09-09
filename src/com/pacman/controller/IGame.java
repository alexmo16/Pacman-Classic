package com.pacman.controller;

import com.pacman.view.Window;

/**
 * Interface which the engine will interact with to talk with the game.
 */
public interface IGame
{
    public void init(Window window);

    public void update(GameController engine);

    public ISettings getSettings();
}
