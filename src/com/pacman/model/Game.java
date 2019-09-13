package com.pacman.model;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineListener;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.pacman.controller.GameController;
import com.pacman.controller.IGame;
import com.pacman.model.objects.Wall;
import com.pacman.model.objects.consumables.Consumable;
import com.pacman.model.objects.consumables.Energizer;
import com.pacman.model.objects.consumables.PacDot;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.objects.entities.Pacman;
import com.pacman.model.states.IGameState;
import com.pacman.model.states.InitState;
import com.pacman.model.states.PauseState;
import com.pacman.model.states.PlayingState;
import com.pacman.model.states.ResumeState;
import com.pacman.model.states.StopState;
import com.pacman.model.world.Direction;
import com.pacman.model.world.Tile;
import com.pacman.utils.Settings;
import com.pacman.view.GameView;
import com.pacman.view.Window;

/**
 * Contains an observer design pattern and a state pattern.
 *
 */
public class Game implements IGame
{
    private Pacman pacman;
    private Pacman maybeFuturPacman;
    private Pacman futurPacman;
    
    private ArrayList<Entity> entities;
    private ArrayList<Wall> maze;
    private ArrayList<Consumable> consumables; 
    
    private Sound startMusic;
    private Sound gameSiren;
    private Sound chomp;
    
    private IGameState initState;
    private IGameState stopState;
    private IGameState playingState;
    private IGameState pauseState;
    private IGameState resumeState;
    private IGameState currentState;
    
    private boolean isUserMuted = false; // pour savoir si c'est un mute system ou effectue par le user.
    
    /**
     * Initialization function called by the engine when it lunch the game.
     */
    @Override
    public void init(Window window)
    {    	
        maze = new ArrayList<Wall>();
        consumables = new ArrayList<Consumable>();
        entities = new ArrayList<Entity>();
        for (int y = 0; y < Settings.WORLD_DATA.getHeight(); y++)
        {
            for (int x = 0; x < Settings.WORLD_DATA.getWidth(); x++)
            {

                if (Settings.WORLD_DATA.getTile(x, y) == Tile.GUM.getValue())
                {
                	consumables.add(new PacDot(x, y));
                }
                else if (Settings.WORLD_DATA.getTile(x, y) == Tile.ENERGIZER.getValue())
                {

                	consumables.add(new Energizer(x, y));
                }
                else if (Settings.WORLD_DATA.getTile(x, y) >= Tile.WALL_START.getValue() && Settings.WORLD_DATA.getTile(x, y) <= Tile.WALL_END.getValue())
            	{
                	maze.add(new Wall(x, y, Settings.WORLD_DATA.getTile(x, y)));
            	}
                else if (Settings.WORLD_DATA.getTile(x, y) == Tile.PAC_MAN_START.getValue())
                {
                	pacman = new Pacman(x, y);
                	entities.add(pacman);
                }
            }
        }
    	
        loadMusics();
        
    	window.setContainer(new GameView(this));
    	
        initState = new InitState(this);
        pauseState = new PauseState(this);
        resumeState = new ResumeState(this);
        playingState = new PlayingState(this);
        stopState = new StopState(this);
        currentState = initState; 
    }
    
    /**
     * Update function called by the engine every tick.
     */
    @Override
    public void update()
    {
    	currentState.update();
    }

	@Override
	public void setPacmanDirection(Direction d)
	{
		pacman.setNextDirection(d);
	}
	
    
    /**
     * Load all audio files and distribute them where they're needed.
     * @return
     */
    private boolean loadMusics()
    {
        try
        {
            startMusic = new Sound(Settings.START_MUSIC_PATH);
            gameSiren = new Sound(Settings.GAME_SIREN_PATH);
            chomp = new Sound(Settings.CHOMP_PATH);
            pacman.setChompSound(chomp);
        } catch (UnsupportedAudioFileException | IOException e)
        {
            System.out.println("Unable to load sounds!!");
            return false;
        }

        return true;
    }

    public void playStartingMusic( LineListener listener )
    {
    	startMusic.play(listener);
    }
    
    public void stopStartingMusic()
    {
    	if (startMusic.getIsRunning())
    	{
    		startMusic.stop();
    	}
    }

    /**
     * Function called when the user click on the mute button.
     */
    public void toggleUserMuteSounds()
    {
        GameController.toggleMute();
        isUserMuted = GameController.getIsMuted();
        if (GameController.getIsMuted())
        {
            gameSiren.stop();
        } else
        {
            gameSiren.playLoopBack();
        }
    }
    
	public void stopInGameMusics()
	{
		if ( gameSiren.getIsRunning() )
		{
			gameSiren.stop();
		}
	}
	
	public void resumeInGameMusics()
	{
		if ( !gameSiren.getIsRunning() )
		{
			gameSiren.playLoopBack();
		}
	}
	
	public void setState( IGameState state )
	{
		currentState = state;
	}
	
	public IGameState getInitState()
	{
		return initState;
	}

	public IGameState getStopState()
	{
		return stopState;
	}

	public IGameState getPlayingState()
	{
		return playingState;
	}

	public IGameState getPauseState()
	{
		return pauseState;
	}

	public IGameState getResumeState()
	{
		return resumeState;
	}

	public IGameState getCurrentState()
	{
		return currentState;
	}

    public Sound getStartMusic()
    {
        return startMusic;
    }

    public Sound getGameSiren()
    {
        return gameSiren;
    }

    public Sound getChomp()
    {
        return chomp;
    }
	
	public Pacman getPacman()
	{
		return pacman;
	}
	
	public Pacman getMaybeFuturPacman()
	{
		return maybeFuturPacman;
	}
	
	public Pacman getFuturPacman()
	{
		return futurPacman;
	}

	public ArrayList<Wall> getMaze()
	{
		return maze;
	}

	public ArrayList<Consumable> getConsumables() 
	{
		return consumables;
	}

	public ArrayList<Entity> getEntities() 
	{
		return entities;
	}

	public boolean isUserMuted() 
	{
		return isUserMuted;
	}
}
