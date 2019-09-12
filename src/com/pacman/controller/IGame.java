package com.pacman.controller;

import com.pacman.model.states.IGameState;
import com.pacman.model.world.Direction;
import com.pacman.view.Window;

/**
 * Interface which the engine will interact with to talk with the game.
 */
public interface IGame
{
    public void init(Window window);

    public void update();

	public void setState(IGameState state);
	public IGameState getMainMenuState();
	public IGameState getInitState();
	public IGameState getStopState();
	public IGameState getPlayingState();
	public IGameState getPauseState();
	public IGameState getResumeState();
	public IGameState getCurrentState();

	public void toggleUserMuteSounds();
	
	public void setPacmanDirection(Direction d);
}
