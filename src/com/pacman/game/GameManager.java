package com.pacman.game;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.pacman.engine.CollisionManager;
import com.pacman.engine.Engine;
import com.pacman.engine.IGame;
import com.pacman.engine.Inputs;
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
import com.pacman.utils.IObserver;

/**
 * Contains an observer design pattern
 *
 */
public class GameManager implements IGame, IObserver<DynamicObject.Direction>
{
	DynamicObject.Direction oldDirection = DynamicObject.Direction.LEFT, direction = DynamicObject.Direction.LEFT;
    int startingPosition[];
   
    double pacmanBox;

    private boolean isPlaying = false;
    private boolean isStartingNewGame = true;
    private boolean canPaused = false;
    private boolean isUserMuted = false; // pour savoir si c'est un mute system ou effectue par le user.

    Settings settings;
    
    PacmanObject pacman;
    PacmanObject futurPacman;
    PacmanObject maybeFuturPacman;
    ArrayList<Gum> gumList;
    ArrayList<PacGum> pacGumList;
    PauseScreen pauseScreen;
    Background background;
    Maze maze;
    PauseScreen pausePane;
    ScoreBar scoreBar;
    
    InGame inGameScene;
    
    Sound startMusic;
    Sound gameSiren;
    Sound chomp;

    LineListener startingMusicListener = new LineListener()
    {
        @Override
        public void update(LineEvent event)
        {
            if (event.getType() == LineEvent.Type.STOP)
            {
                if (startMusic != null)
                {
                    startMusic.stop();
                }
                isPlaying = true;
                canPaused = true;
                gameSiren.playLoopBack();
            }
        }
    };

    public GameManager()
    {
    	settings = new Settings();
        startingPosition = settings.getWorldData().findFirstInstanceOF(WorldTile.PAC_MAN_START.getValue());
        pacmanBox = 0.9;
        pacman = new PacmanObject(startingPosition[0], startingPosition[1], pacmanBox, pacmanBox, direction, settings);
        pacman.registerObserver( this );
        
        maybeFuturPacman = new PacmanObject(startingPosition[0], startingPosition[1], pacmanBox, pacmanBox, direction,
                settings);
        futurPacman = new PacmanObject(startingPosition[0], startingPosition[1], pacmanBox, pacmanBox, direction,
                settings);
        gumList = Gum.generateGumList(settings);
        pacGumList = PacGum.generatePacGumList(settings);
        pauseScreen = new PauseScreen("Pause");
        maze = new Maze(settings);
        background = new Background(Color.black);
        scoreBar = new ScoreBar(settings);  
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

        // TODO put this at true when the start button is pressed in the main menu.
        isStartingNewGame = true;
    }


    /**
     * Update function called by the engine every tick.
     */
    @Override
    public void update(Engine engine)
    {
        Inputs inputs = engine.getInputs();
        if ( canPaused && inputs.isKeyDown(settings.getPauseButton()))
        {
            togglePauseGame();
        }

        if (isStartingNewGame)
        {
            startMusic.play(startingMusicListener);
            isStartingNewGame = false;
        }

        if (isPlaying)
        {
            if (inputs.isKeyDown(settings.getMutedButton()))
            {
                toggleUserMuteSounds();
            }

            pacman.checkNewDirection(engine.getInputs());
            
            maybeFuturPacman.getRectangle().setRect(pacman.getRectangle().getX(), pacman.getRectangle().getY(),
                    pacman.getRectangle().getWidth(), pacman.getRectangle().getHeight());
            futurPacman.getRectangle().setRect(pacman.getRectangle().getX(), pacman.getRectangle().getY(),
                    pacman.getRectangle().getWidth(), pacman.getRectangle().getHeight());
            DynamicObject.updatePosition(futurPacman.getRectangle(), direction);
            DynamicObject.updatePosition(maybeFuturPacman.getRectangle(), oldDirection);

            checkGumCollision();
            checkPacGumCollision();
            // Strategy pattern for wall collisions.
            String checkWallCollision = CollisionManager.collisionWall(futurPacman);
            executeWallStrategy( checkWallCollision );
        }

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
    	
    	inGameScene.addToStatusBar(scoreBar);
    	
    	inGameScene.addToBackground(background);
    	
        w.getFrame().add(inGameScene);
        w.getFrame().pack();
    }
    
    /**
     * Function called when the user click on the mute button.
     */
    private void toggleUserMuteSounds()
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

    /**
     * Function called when the user click on the pause button.
     */
    private void togglePauseGame()
    {
    	pauseScreen.togglePausePane();
        isPlaying = !isPlaying;

        if (isPlaying)
        {
            if (!isUserMuted)
            {
                Engine.setIsMuted(false);
                gameSiren.playLoopBack();
            }
        } else
        {
            Engine.setIsMuted(true);
            gameSiren.stop();
        }
    }

    /**
     * Check if pacman eats a Gum
     */
    private void checkGumCollision()
    {
        for (Gum gum : gumList)
        {
            if (CollisionManager.collisionObj(pacman, gum))
            {
                pacman.eatGum(gum, scoreBar);
                gumList.remove(gum);
                gum.setVisible(false);
                gum = null;
                break;
            }
        }
    }
    
    /**
     * Check if pacman eats a PacGum
     */
    private void checkPacGumCollision()
    {
        for (PacGum pacGum : pacGumList)
        {
            if (CollisionManager.collisionObj(pacman, pacGum))
            {
                pacman.eatGum(pacGum, scoreBar);
                pacGumList.remove(pacGum);
                pacGum.setVisible(false);
                pacGum = null;
                break;
            }
        }
    }
    
    /**
     * Redirect to the correct strategy
     * @param collisionString
     */
    private void executeWallStrategy( String collisionString )
    {
    	if ( collisionString == "void" )
    	{
    		tunnelStrategy();
    	}
    	else if ( collisionString == "wall" )
    	{
    		oneWallStrategy();
    	}
    	else
    	{
    		noWallStrategy();
    	}
    }
    
    /**
     * Strategy if pacman hits a wall.
     */
    private void oneWallStrategy()
    {
    	String collisionString = CollisionManager.collisionWall(maybeFuturPacman);
        if (collisionString == "void")
        {
            DynamicObject.tunnel(pacman.getRectangle(), oldDirection);
            pacman.setDirection(oldDirection);
            scoreBar.setCollision(false, oldDirection);
        }
        if (collisionString == "path")
        {
            DynamicObject.updatePosition(pacman.getRectangle(), oldDirection);
            pacman.setDirection(oldDirection);
            scoreBar.setCollision(false, oldDirection);
        } 
        else
        {
        	scoreBar.setCollision(true, oldDirection);
        }
    }
    
    /**
     * Strategy if pacman goes through the tunnel
     */
    private void tunnelStrategy()
    {
    	DynamicObject.tunnel(pacman.getRectangle(), direction);
        scoreBar.setCollision(false, oldDirection);
    }
    
    /**
     * Strategy if pacman moves forward
     */
    private void noWallStrategy()
    {
    	DynamicObject.updatePosition(pacman.getRectangle(), direction);
        pacman.setDirection(direction);
        oldDirection = direction;
        scoreBar.setCollision(false, oldDirection);
    }

    /**
     * update of the observer
     */
	@Override
	public void update( DynamicObject.Direction direction ) 
	{
		oldDirection = this.direction;
		this.direction = direction;
	}
}
