package com.pacman.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineListener;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.pacman.controller.GameController;
import com.pacman.model.objects.Wall;
import com.pacman.model.objects.consumables.Consumable;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.objects.entities.Pacman;
import com.pacman.model.states.IGameState;
import com.pacman.model.states.InitState;
import com.pacman.model.states.MainMenuState;
import com.pacman.model.states.PauseState;
import com.pacman.model.states.PlayingState;
import com.pacman.model.states.ResumeState;
import com.pacman.model.states.StopState;
import com.pacman.model.world.Direction;
import com.pacman.model.world.GhostType;
import com.pacman.model.world.Level;
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
    private ArrayList<Wall> maze;
    private Pacman newDirectionPacman;
    private Pacman nextTilesPacman;
    
    private Direction nextTilesDirection;
    private Direction newDirection;
        
    private ArrayList<Entity> entities;
    
    private Sound startMusic;
    private Sound gameSiren;
    private Sound chomp;
    private Sound death;
    
    private Collision collision;
    
    private IGameState initState;
    private IGameState stopState;
    private IGameState playingState;
    private IGameState pauseState;
    private IGameState resumeState;
    private IGameState currentState;
    private IGameState mainMenuState;
    
    private Level currentLevel;
    
    private boolean pacmanWon = false;
    private boolean isUserMuted = false; // pour savoir si c'est un mute system ou effectue par le user.
    
    private final String LEVEL_DATA_FILE = new String(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "map.txt"); 
    
    /**
     * Initialization function called by the engine when it lunch the game.
     */
    @Override
    public void init(Window window)
    {    
    	collision = new Collision(this);
    	collision.setAuthTiles(Level.getAuthTiles());
    	currentLevel = new Level(LEVEL_DATA_FILE, "1");
    	maze = new ArrayList<Wall>();
        entities = new ArrayList<Entity>();
        
        for (int y = 0; y < Level.getHeight(); y++)
        {
            for (int x = 0; x < Level.getWidth(); x++)
            {

                if (currentLevel.getTile(x, y) >= Tile.WALL_START.getValue() && currentLevel.getTile(x, y) <= Tile.WALL_END.getValue())
            	{
                	maze.add(new Wall(x, y, currentLevel.getTile(x, y)));
            	}
                else if (currentLevel.getTile(x, y) == Tile.PAC_MAN_START.getValue())
                {
                	pacman = new Pacman(x, y);
                    newDirectionPacman = new Pacman(getPacman().getHitBox().getX(), getPacman().getHitBox().getY());
                    nextTilesPacman = new Pacman(getPacman().getHitBox().getX(), getPacman().getHitBox().getY());
                	entities.add(pacman);
                }
                else if (currentLevel.getTile(x, y) == Tile.BLINKY_START.getValue())
                {
                	entities.add(new Ghost(x, y, GhostType.BLINKY));
                }
                else if (currentLevel.getTile(x, y) == Tile.PINKY_START.getValue())
                {
                	entities.add(new Ghost(x, y, GhostType.PINKY));
                }
                else if (currentLevel.getTile(x, y) == Tile.INKY_START.getValue())
                {
                	entities.add(new Ghost(x, y, GhostType.INKY));
                }
                else if (currentLevel.getTile(x, y) == Tile.CLYDE_START.getValue())
                {
                	entities.add(new Ghost(x, y, GhostType.CLYDE));
                }
            }
        }
    	
        loadMusics();
        
    	window.setContainer(new GameView(this));
    	
    	mainMenuState = new MainMenuState(this);
        initState = new InitState(this);
        pauseState = new PauseState(this);
        resumeState = new ResumeState(this);
        playingState = new PlayingState(this);
        stopState = new StopState(this);
        currentState = mainMenuState; 
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
            death = new Sound(Settings.DEATH_PATH);
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

    public void playDeathMusic(LineListener listener)
    {
    	death.play(listener);
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
	
	public IGameState getMainMenuState()
	{
		return mainMenuState;
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
	
	public Pacman getNewDirectionPacman()
	{
		return newDirectionPacman;
	}
	
	public Pacman getNextTilesPacman()
	{
		return nextTilesPacman;
	}
	
	public Direction getNewDirection()
	{
		return newDirection;
	}
	
	public Direction getNextTilesDirection()
	{
		return nextTilesDirection;
	}
	
	public void setNewDirection(Direction direction)
	{
		this.newDirection = direction;
	}
	
	public void setNextTilesDirection(Direction direction)
	{
		this.nextTilesDirection = direction;
	}

	public ArrayList<Wall> getMaze()
	{
		return maze;
	}

	public ArrayList<Consumable> getConsumables() 
	{
		return currentLevel.getConsumables();
	}

	public ArrayList<Entity> getEntities() 
	{
		return entities;
	}

	public boolean isUserMuted() 
	{
		return isUserMuted;
	}

	public Level getCurrentLevel()
	{
		return currentLevel;
	}
	
	public Collision getCollision()
	{
		return collision;
	}
	
	public void setCurrentLevel(Level level)
	{
		currentLevel = level;
	}

	public boolean isPacmanWon()
	{
		return pacmanWon;
	}

	public void setPacmanWon(boolean pacmanWon)
	{
		this.pacmanWon = pacmanWon;
	}

	public void loadLevel(String levelName)
	{
		Level level = new Level(LEVEL_DATA_FILE, levelName);
		currentLevel = level;
		currentLevel.generateConsumables();
	}
}
