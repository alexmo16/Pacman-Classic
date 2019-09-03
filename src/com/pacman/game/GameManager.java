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

public class GameManager implements IGame
{
    String oldDirection = "left", direction = "left";
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
        startingPosition = settings.getWorldData().getStartPosition();
        pacmanBox = 0.9;
        pacman = new PacmanObject(startingPosition[0], startingPosition[1], pacmanBox, pacmanBox, direction, settings);
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
    
    @Override
    public void init(Window window)
    {
        loadInGameScene(window);
        loadMusics();

        CollisionManager.setSettings(settings);

        // TODO mettre ca a true seulement quand on clic sur le start button
        isStartingNewGame = true;
    }

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

            direction = PacmanObject.getNewDirection(engine.getInputs(), direction);
            maybeFuturPacman.getRectangle().setRect(pacman.getRectangle().getX(), pacman.getRectangle().getY(),
                    pacman.getRectangle().getWidth(), pacman.getRectangle().getHeight());
            futurPacman.getRectangle().setRect(pacman.getRectangle().getX(), pacman.getRectangle().getY(),
                    pacman.getRectangle().getWidth(), pacman.getRectangle().getHeight());
            DynamicObject.updatePosition(futurPacman.getRectangle(), direction);
            DynamicObject.updatePosition(maybeFuturPacman.getRectangle(), oldDirection);

            checkGumCollision();
            checkPacGumCollision();
            // Strategy pattern for wall collisions.
            int checkWallCollision = CollisionManager.collisionWall(futurPacman);
            executeWallStrategy( checkWallCollision );
        }

    }

    @Override
    public Settings getSettings()
    {
        return settings;
    }

    private boolean loadMusics()
    {
        try
        {
            startMusic = new Sound("./assets/pacman_beginning.wav");
            gameSiren = new Sound("./assets/siren.wav");
            chomp = new Sound("." + File.separator + "assets" + File.separator + "pacman_chomp.wav");
        } catch (UnsupportedAudioFileException | IOException e)
        {
            System.out.println("Unable to load sounds!!");
            return false;
        }

        return true;
    }

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
    
    private void executeWallStrategy( int collisionID )
    {
    	if ( collisionID == 2 )
    	{
    		tunnelStrategy();
    	}
    	else if ( collisionID == 1 )
    	{
    		oneWallStrategy();
    	}
    	else
    	{
    		noWallStrategy();
    	}
    }
    
    private void oneWallStrategy()
    {
    	int collisionID = CollisionManager.collisionWall(maybeFuturPacman);
        if (collisionID == 2)
        {
            DynamicObject.tunnel(pacman.getRectangle(), oldDirection);
            pacman.setDirection(oldDirection);
            scoreBar.setCollision(false, oldDirection);
        }
        if (collisionID == 0)
        {
            DynamicObject.updatePosition(pacman.getRectangle(), oldDirection);
            pacman.setDirection(oldDirection);
            scoreBar.setCollision(false, oldDirection);
        } else
        {
        	scoreBar.setCollision(true, oldDirection);
        }
    }
    
    private void tunnelStrategy()
    {
    	DynamicObject.tunnel(pacman.getRectangle(), direction);
        scoreBar.setCollision(false, oldDirection);
    }
    
    private void noWallStrategy()
    {
    	DynamicObject.updatePosition(pacman.getRectangle(), direction);
        pacman.setDirection(direction);
        oldDirection = direction;
        scoreBar.setCollision(false, oldDirection);
    }
}
