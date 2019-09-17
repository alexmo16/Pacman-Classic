package com.pacman.model;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.List;

import com.pacman.model.menus.MenuOption;
import com.pacman.model.objects.GameObject;
import com.pacman.model.objects.Wall;
import com.pacman.model.objects.consumables.Consumable;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.objects.entities.Pacman;
import com.pacman.model.states.IGameState;
import com.pacman.model.world.Direction;
import com.pacman.model.world.Level;
import com.pacman.view.IWindow;

/**
 * Interface which the engine will interact with to talk with the game.
 */
public interface IGame
{
    public void init(IWindow window);
    public void update();

	public void setState(IGameState state);
	public IGameState getMainMenuState();
	public IGameState getInitState();
	public IGameState getStopState();
	public IGameState getPlayingState();
	public IGameState getPauseState();
	public IGameState getResumeState();
	public IGameState getCurrentState();
	
	public void setPacmanDirection(Direction d);
	
	public ArrayList<Entity> getEntities();
	public ArrayList<Wall> getMaze();
	public ArrayList<Consumable> getConsumables(); 
	public List<GameObject> getGameObjects();
	public MenuOption getCurrentSelection();
	public void mainMenuNext();
	public void mainMenuPrevious();
	public Pacman getPacman();
	public Level getCurrentLevel();
	
	public ArrayList<MenuOption> getMainMenuOptions();
	
	public boolean isPacmanWon();
	public int getResumeTime();
	
	public void stopThreads() throws InterruptedException, InterruptedByTimeoutException;
	
	public void setMusicVolume(float volume);
	public void muteMusics();
	public void resumeMusics();
	public void setSoundsVolume(float volume);
	public void muteSounds();
	public void resumeSounds();
} 
