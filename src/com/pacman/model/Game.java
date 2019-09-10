package com.pacman.model;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineListener;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.pacman.controller.GameController;
import com.pacman.controller.IGame;
import com.pacman.model.objects.Gum;
import com.pacman.model.objects.Maze;
import com.pacman.model.objects.PacGum;
import com.pacman.model.objects.PacmanObject;
import com.pacman.model.objects.ScoreBar;
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
    protected boolean canPaused = false;
    public boolean isUserMuted = false; // pour savoir si c'est un mute system ou effectue par le user.

    private Direction direction = Direction.LEFT;
    private PacmanObject pacman;
    private int startingPosition[];
    private double pacmanBox;
	private ArrayList<Gum> gumList;
    private ArrayList<PacGum> pacGumList;
    
    private Maze maze;
    ScoreBar scoreBar;
    
    Sound startMusic;
    Sound gameSiren;
    Sound chomp;

    private IGameState initState;
    private IGameState stopState;
    private IGameState playingState;
    private IGameState pauseState;
    private IGameState resumeState;
    private IGameState currentState;
    
    public Game()
    {
    	startingPosition = Settings.WORLD_DATA.findFirstInstanceOF(Tile.PAC_MAN_START.getValue());
        pacmanBox = 0.9;
        pacman = new PacmanObject(startingPosition[0], startingPosition[1], pacmanBox, pacmanBox, direction);
        gumList = Gum.generateGumList();
        pacGumList = PacGum.generatePacGumList();
        
        maze = new Maze();    
        scoreBar = new ScoreBar();
        
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
    
    /**
     * Initialization function called by the engine when it lunch the game.
     */
    @Override
    public void init(Window window)
    {
        //loadInGameScene(window);
    	window.setContainer(new GameView(this));
        loadMusics();
        Collision.setSettings();
    }


    /**
     * Update function called by the engine every tick.
     */
    @Override
    public void update()
    {
    	scoreBar.setState(currentState.getName());
    	currentState.update();
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

	@Override
	public void setPacmanDirection(Direction d)
	{
		pacman.setNextDirection(d);
	}
}
