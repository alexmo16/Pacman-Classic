package com.pacman.engine;

import com.pacman.engine.Engine;

/**
 * Interface which the engine will interact with to talk with the game.
 */
public interface IGame
{
    public void init(Window window);

    public void update(Engine engine);

    public ISettings getSettings();
}
