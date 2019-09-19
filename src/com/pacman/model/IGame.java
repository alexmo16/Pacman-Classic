package com.pacman.model;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.List;

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
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
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
	public Pacman getPacman();
	public Level getCurrentLevel();
	
	public boolean isPacmanWon();
	public int getResumeTime();
	
	public void stopThreads() throws InterruptedException, InterruptedByTimeoutException;
	
	public void setMusicVolume(int volume);
	public void muteMusics();
	public void resumeMusics();
	public void setSoundsVolume(int volume);
	public void muteSounds();
	public void resumeSounds();
} 
