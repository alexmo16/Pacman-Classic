package com.pacman.game;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineListener;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.pacman.engine.CollisionManager;
import com.pacman.engine.Engine;
import com.pacman.engine.IGame;
import com.pacman.engine.Sound;
import com.pacman.engine.Window;
import com.pacman.game.objects.Background;
import com.pacman.game.objects.DynamicObject;
import com.pacman.game.objects.Gum;
import com.pacman.game.objects.Maze;
import com.pacman.game.objects.PacGum;
import com.pacman.game.objects.PacmanObject;
import com.pacman.game.objects.PauseScreen;
import com.pacman.game.objects.ScoreBar;
import com.pacman.game.scenes.InGame;
import com.pacman.game.states.IGameState;
import com.pacman.game.states.InitState;
import com.pacman.game.states.PauseState;
import com.pacman.game.states.PlayingState;
import com.pacman.game.states.ResumeState;
import com.pacman.game.states.StopState;

/**
 * Contains an observer design pattern and a state pattern.
 *
 */
public class GameManager implements IGame
{
    protected boolean canPaused = false;
    public boolean isUserMuted = false; // pour savoir si c'est un mute system ou effectue par le user.

    private DynamicObject.Direction direction = DynamicObject.Direction.LEFT;
    private PacmanObject pacman;
    private int startingPosition[];
    private double pacmanBox;
	private ArrayList<Gum> gumList;
    private ArrayList<PacGum> pacGumList;
    
    Settings settings;
    PauseScreen pauseScreen;
    Background background;
    Maze maze;
    PauseScreen pausePane;
    ScoreBar scoreBar;
    
    InGame inGameScene;
    
    Sound startMusic;
    Sound gameSiren;
    Sound chomp;

    private IGameState initState;
    private IGameState stopState;
    private IGameState playingState;
    private IGameState pauseState;
    private IGameState resumeState;
    private IGameState currentState;
    


    public GameManager()
    {
    	settings = new Settings();
        
    	startingPosition = settings.getWorldData().findFirstInstanceOF(WorldTile.PAC_MAN_START.getValue());
        pacmanBox = 0.9;
        pacman = new PacmanObject(startingPosition[0], startingPosition[1], pacmanBox, pacmanBox, direction, settings);
        gumList = Gum.generateGumList(settings);
        pacGumList = PacGum.generatePacGumList(settings);
        
        pauseScreen = new PauseScreen("Pause");
        maze = new Maze(settings);
        background = new Background(Color.black);        
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
        loadInGameScene(window);
        loadMusics();

        CollisionManager.setSettings(settings);
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
            startMusic = new Sound(settings.getStartMusicPath());
            gameSiren = new Sound(settings.getGameSirenPath());
            chomp = new Sound(settings.getChompPath());
            pacman.setChompSound(chomp);
        } catch (UnsupportedAudioFileException | IOException e)
        {
            System.out.println("Unable to load sounds!!");
            return false;
        }

        return true;
    }

    /**
     * Load all scene objects needed in the game scene.
     * @param w
     */
    public void loadInGameScene(Window w)
    {
    	inGameScene = new InGame();
    	
    	inGameScene.addToGame(pauseScreen);
    	inGameScene.addToGame(pacman);
    	for (Gum gum : gumList) {inGameScene.addToGame(gum);}
    	for (PacGum pacGum : pacGumList) {inGameScene.addToGame(pacGum);}
    	inGameScene.addToGame(maze);
    	inGameScene.addToGame(background);
    	
    	inGameScene.addToStatusBar(scoreBar);
    	
        w.getFrame().add(inGameScene);
        w.getFrame().pack();
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
	
	public void togglePauseScreen()
	{
		pauseScreen.togglePausePane();
	}
	
	public ScoreBar getScoreBar()
	{
		return scoreBar;
	}

	public PacmanObject getPacman()
	{
		return pacman;
	}

	public DynamicObject.Direction getDirection()
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
}
