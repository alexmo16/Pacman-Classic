package com.pacman.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineListener;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.pacman.controller.states.IGameState;
import com.pacman.controller.states.InitState;
import com.pacman.controller.states.PauseState;
import com.pacman.controller.states.PlayingState;
import com.pacman.controller.states.ResumeState;
import com.pacman.controller.states.StopState;
import com.pacman.model.Settings;
import com.pacman.model.objects.Gum;
import com.pacman.model.objects.Maze;
import com.pacman.model.objects.PacGum;
import com.pacman.model.objects.PacmanObject;
import com.pacman.model.objects.ScoreBar;
import com.pacman.model.world.Direction;
import com.pacman.model.world.Tile;
import com.pacman.view.GameView;

/**
 * Contains an observer design pattern and a state pattern.
 *
 */
public class GameController implements IGame
{
    protected boolean canPaused = false;
    public boolean isUserMuted = false; // pour savoir si c'est un mute system ou effectue par le user.

    private Direction direction = Direction.LEFT;
    private PacmanObject pacman;
    private int startingPosition[];
    private double pacmanBox;
	private ArrayList<Gum> gumList;
    private ArrayList<PacGum> pacGumList;
    
    Settings settings;
    private Maze maze;
    ScoreBar scoreBar;
    
    SoundController startMusic;
    SoundController gameSiren;
    SoundController chomp;

    private IGameState initState;
    private IGameState stopState;
    private IGameState playingState;
    private IGameState pauseState;
    private IGameState resumeState;
    private IGameState currentState;
    
    public GameController()
    {
    	settings = new Settings();
        
    	startingPosition = settings.getWorldData().findFirstInstanceOF(Tile.PAC_MAN_START.getValue());
        pacmanBox = 0.9;
        pacman = new PacmanObject(startingPosition[0], startingPosition[1], pacmanBox, pacmanBox, direction, settings);
        gumList = Gum.generateGumList(settings);
        pacGumList = PacGum.generatePacGumList(settings);
        
        maze = new Maze(settings);    
        scoreBar = new ScoreBar(settings);
        
        initState = new InitState(this);
        pauseState = new PauseState(this);
        resumeState = new ResumeState(this);
        playingState = new PlayingState(this);
        stopState = new StopState(this);
        currentState = initState;  
    }
    
    /**
     * Getters for sound
     */
    
    public SoundController getStartMusic()
    {
        return startMusic;
    }

    public SoundController getGameSiren()
    {
        return gameSiren;
    }

    public SoundController getChomp()
    {
        return chomp;
    }
    
    /**
     * Initialization function called by the engine when it lunch the game.
     */
    @Override
    public void init(WindowController window)
    {
        //loadInGameScene(window);
    	window.setContainer(new GameView(this));
        loadMusics();

        CollisionController.setSettings(settings);
    }


    /**
     * Update function called by the engine every tick.
     */
    @Override
    public void update(Engine engine)
    {
    	scoreBar.setState(currentState.getName());
    	currentState.update(engine);
    }

    @Override
    public Settings getSettings()
    {
        return settings;
    }

    /**
     * Load all audio files and distribute them where they're needed.
     * @return
     */
    private boolean loadMusics()
    {
        try
        {
            startMusic = new SoundController(settings.getStartMusicPath());
            gameSiren = new SoundController(settings.getGameSirenPath());
            chomp = new SoundController(settings.getChompPath());
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
        Engine.toggleMute();
        isUserMuted = Engine.getIsMuted();
        if (Engine.getIsMuted())
        {
            gameSiren.stop();
        } else
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
	
	public boolean getCanPausedGame()
	{
		return canPaused;
	}
	
	public void setCanPausedGame(boolean canPaused)
	{
		this.canPaused = canPaused;
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
	
	public ScoreBar getScoreBar()
	{
		return scoreBar;
	}

	public PacmanObject getPacman()
	{
		return pacman;
	}

	public Direction getDirection()
	{
		return direction;
	}

	public int[] getStartingPosition()
	{
		return startingPosition;
	}

	public double getPacmanBox()
	{
		return pacmanBox;
	}

	public ArrayList<Gum> getGumList()
	{
		return gumList;
	}

	public ArrayList<PacGum> getPacGumList()
	{
		return pacGumList;
	}

	public Maze getMaze() {
		return maze;
	}
}
