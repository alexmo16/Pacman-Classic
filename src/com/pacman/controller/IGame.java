package com.pacman.controller;

/**
 * Interface which the engine will interact with to talk with the game.
 */
public interface IGame
{
    public void init(WindowController window);

    public void update(Engine engine);

    public ISettings getSettings();
}
