package com.pacman.model;

import java.io.File;
import java.io.IOException;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sound.sampled.LineListener;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.pacman.model.objects.GameObject;
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
import com.pacman.model.states.StatesName;
import com.pacman.model.states.StopState;
import com.pacman.model.threads.PhysicsThread;
import com.pacman.model.threads.RenderThread;
import com.pacman.model.threads.TimerThread;
import com.pacman.model.world.Direction;
import com.pacman.model.world.GhostType;
import com.pacman.model.world.Level;
import com.pacman.model.world.Tile;
import com.pacman.utils.Settings;
import com.pacman.view.IWindow;
import com.pacman.view.views.ViewType;

/**
 * Contains an observer design pattern and a state pattern.
 *
 */
public class Game implements IGame
{	
	private IWindow window;
	
    private Pacman pacman;
    private ArrayList<Wall> maze;
    private Pacman newDirectionPacman;
    private Pacman nextTilesPacman;
    
    private Direction nextTilesDirection;
    private Direction newDirection;
        
    private volatile ArrayList<Entity> entities;
    
    private Sound startMusic;
    private Sound gameSiren;
    private Sound chomp;
    private Sound death;
    
    private PhysicsThread physicsThread;
    private volatile TimerThread timerThread;
    
    private IGameState initState;
    private IGameState stopState;
    private IGameState playingState;
    private IGameState pauseState;
    private IGameState resumeState;
    private IGameState currentState;
    private IGameState mainMenuState;
    
    private RenderThread renderThread;
    private static final int JOIN_TIMER = 500; //ms
    
    private int resumeTime = 3;
    
    private Level currentLevel;
    
    private boolean pacmanWon = false;
    private final String LEVEL_DATA_FILE = new String(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "map.txt"); 
    
    /**
     * Initialization function called by the engine when it lunch the game.
     */
    @Override
    public void init(IWindow w)
    {
        window = w;
    	physicsThread = new PhysicsThread(this);
    	currentLevel = new Level(LEVEL_DATA_FILE, "1");
    	maze = new ArrayList<Wall>();
        entities = new ArrayList<Entity>();
        renderThread = new RenderThread(this);
        renderThread.start();
        physicsThread.setAuthTiles(currentLevel.getAuthTiles());
        
        loadEntities();
        loadMusics();
        
        mainMenuState = new MainMenuState(this);
        initState = new InitState(this);
        pauseState = new PauseState(this);
        resumeState = new ResumeState(this);
        playingState = new PlayingState(this);
        stopState = new StopState(this);
        setState(mainMenuState);
        
        renderThread = new RenderThread(this);
        renderThread.start();
        physicsThread.start();
        
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
    
    
    private void loadEntities()
    {
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
                    // TODO : Make no sens to hard code some value in the game. Only the Pacman object himself shoudl know about it's correct position.
                    pacman = new Pacman(x+0.05, y+0.05);
                    newDirectionPacman = new Pacman(getPacman().getHitBox().getX(), getPacman().getHitBox().getY());
                    nextTilesPacman = new Pacman(getPacman().getHitBox().getX(), getPacman().getHitBox().getY());
                    entities.add(pacman);
                    pacman.setAuthTiles(currentLevel.getAuthTiles());
                }
                else if (currentLevel.getTile(x, y) == Tile.BLINKY_START.getValue())
                {
                    entities.add(new Ghost(x, y, GhostType.BLINKY));
                    ((Ghost) getEntities().get(0)).setAuthTiles(currentLevel.getAuthTilesGhost(),currentLevel.getAuthTilesGhostRoom());
                }
                else if (currentLevel.getTile(x, y) == Tile.PINKY_START.getValue())
                {
                    entities.add(new Ghost(x, y, GhostType.PINKY));
                    ((Ghost) getEntities().get(0)).setAuthTiles(currentLevel.getAuthTilesGhost(),currentLevel.getAuthTilesGhostRoom());
                }
                else if (currentLevel.getTile(x, y) == Tile.INKY_START.getValue())
                {
                    entities.add(new Ghost(x, y, GhostType.INKY));
                    ((Ghost) getEntities().get(0)).setAuthTiles(currentLevel.getAuthTilesGhost(),currentLevel.getAuthTilesGhostRoom());
                }
                else if (currentLevel.getTile(x, y) == Tile.CLYDE_START.getValue())
                {
                    entities.add(new Ghost(x, y, GhostType.CLYDE));
                    ((Ghost) getEntities().get(0)).setAuthTiles(currentLevel.getAuthTilesGhost(),currentLevel.getAuthTilesGhostRoom());
                }
            }
        }    
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
            startMusic.setVolume(Settings.getMusicVolume());
            gameSiren = new Sound(Settings.GAME_SIREN_PATH);
            gameSiren.setVolume(Settings.getMusicVolume());
            chomp = new Sound(Settings.CHOMP_PATH);
            chomp.setVolume(Settings.getSoundsVolume());
            pacman.setChompSound(chomp);
            death = new Sound(Settings.DEATH_PATH);
            death.setVolume(Settings.getSoundsVolume());
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
    
	@Override
	public void setMusicVolume(int volume)
	{
		if (!Settings.isMusicMute())
		{
			gameSiren.setVolume(volume);
			startMusic.setVolume(volume);
		}
	}

	@Override
	public void setSoundsVolume(int volume)
	{
		if (!Settings.isSoundsMute())
		{
			chomp.setVolume(volume);
			death.setVolume(volume);
		}
	}
    
    public void muteAudio()
    {
    	muteMusics();
    	muteSounds();
    }
    
    public void resumeAudio()
    {
    	resumeMusics();
    	resumeSounds();
    }
    
    @Override
    public void muteMusics()
    {
        if (gameSiren != null && startMusic != null)
        {
            gameSiren.setVolume(0);
            startMusic.setVolume(0);
        }
    }
    
    @Override
    public void muteSounds()
    {
	    if (death != null && chomp != null)
	    {
	    	death.setVolume(0);
	    	chomp.setVolume(0);
	    }
    }
    
    @Override
    public void resumeMusics()
    {
    	if (!Settings.isMusicMute() && gameSiren != null && startMusic != null)
    	{
    		gameSiren.setVolume(Settings.getMusicVolume());
    		startMusic.setVolume(Settings.getMusicVolume());
    	}
    }
    
    @Override
    public void resumeSounds()
    {
    	if (!Settings.isSoundsMute() && death != null && chomp != null)
    	{
    		death.setVolume(Settings.getSoundsVolume());
    		chomp.setVolume(Settings.getSoundsVolume());
    	}
    }
    
	public void stopInGameMusics()
	{
		if ( gameSiren.getIsRunning() )
		{
			gameSiren.stop();
		}
	}
	
	public void stopDeathMusic()
	{
		if ( death.getIsRunning() )
		{
			death.stop();
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
		if (state.getName() == StatesName.INIT)
		{		
			window.showView(ViewType.GAME);
			window.setIsGameActive(true);
		}
		else if (state.getName() == StatesName.RESUME && currentState.getName() == StatesName.MAIN_MENU)
		{
			window.showView(ViewType.GAME);
		}
		else if (state.getName() == StatesName.STOP)
		{
			window.setIsGameActive(false);
		}
		else if (state.getName() == StatesName.MAIN_MENU)
		{
			window.showView(ViewType.MAIN_MENU);
		}
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

	public synchronized ArrayList<Consumable> getConsumables() 
	{
		return currentLevel.getConsumables();
	}

	public synchronized ArrayList<Entity> getEntities() 
	{
		return entities;
	}

	public Level getCurrentLevel()
	{
		return currentLevel;
	}
	
	public synchronized PhysicsThread getPhysicsThread()
	{
		return physicsThread;
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

	public synchronized List<GameObject> getGameObjects() 
	{
		if (getMaze() != null && getConsumables() != null && getEntities() != null)
		{
			return Stream.of(getMaze(), getConsumables(), getEntities()).flatMap(x -> x.stream()).collect(Collectors.toList());
		}
		return null;
	}

	public void setResumeTime(int time)
	{
		resumeTime = time;
	}
	
	public int getResumeTime()
	{
		return resumeTime;
	}
	
	public synchronized TimerThread getTimerThread()
	{
	    return timerThread;
	}
	
	public synchronized void setTimerThread(TimerThread timerThread)
	{
	    this.timerThread = timerThread;
	}
	
	public synchronized void setTimerThreadNull()
	{
	    this.timerThread = null;
	}
	
	public synchronized void startTimerThread()
	{
	    if (this.timerThread != null)
	    {
	        this.timerThread.start();
	    }
	}

	public void notifyPhysics()
	{
        synchronized (this)
		{
			notify();
		}
	}
	
	public void killPacman()
	{
		if (currentState.getName() == StatesName.PLAY)
		{
			PlayingState state = (PlayingState) currentState;
			state.killPacman();
		}
	}

	/**
	 * Method to stop all sub threads of the application.
	 * @throws InterruptedException 
	 * @throws InterruptedByTimeoutException 
	 */
	@Override
	public void stopThreads() throws InterruptedException, InterruptedByTimeoutException 
	{
		renderThread.stopThread();
		renderThread.join(JOIN_TIMER);
		if (renderThread.isAlive())
		{
			renderThread.interrupt();
			throw new InterruptedByTimeoutException();
		}
        
		synchronized (this)
		{
			notifyAll();
		}
        physicsThread.stopThread();
        physicsThread.join(JOIN_TIMER);
        if (physicsThread.isAlive())
        {
            physicsThread.interrupt();
            throw new InterruptedByTimeoutException();
        }
	}
}
