package com.pacman.model;

import java.io.File;
import java.io.IOException;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sound.sampled.LineListener;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.pacman.controller.GameController;
import com.pacman.model.objects.GameObject;
import com.pacman.model.objects.Wall;
import com.pacman.model.objects.consumables.Consumable;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.objects.entities.Pacman;
import com.pacman.model.states.IGameState;
import com.pacman.model.states.InitState;
import com.pacman.model.states.MainMenuState;
import com.pacman.model.states.NewHighscore;
import com.pacman.model.states.PauseState;
import com.pacman.model.states.PlayingState;
import com.pacman.model.states.ResumeState;
import com.pacman.model.states.StatesName;
import com.pacman.model.states.StopState;
import com.pacman.model.threads.AudioThread;
import com.pacman.model.threads.PhysicsThread;
import com.pacman.model.threads.RenderThread;
import com.pacman.model.threads.TimerThread;
import com.pacman.model.world.Direction;
import com.pacman.model.world.GhostType;
import com.pacman.model.world.Level;
import com.pacman.model.world.Tile;
import com.pacman.utils.IObserver;
import com.pacman.utils.Settings;
import com.pacman.view.IWindow;
import com.pacman.view.views.ViewType;

/**
 * Contains an observer design pattern and a state pattern.
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros
 *          Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis
 *          Ryckebusch-rycl2501
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
    
    private double pacmanXBlinky;
    private double pacmanYBlinky;
    
    private double pacmanXInky;
    private double pacmanYInky;

    private volatile ArrayList<Entity> entities;

    private Sound startMusic;
    private Sound gameSiren;
    private Sound[] chomps = new Sound[4];
    private Sound death;
    private Sound intermission;

    private PhysicsThread physicsThread;
    private volatile TimerThread timerThread;
    private RenderThread renderThread;
    private AudioThread audioThread;
    private TimerThread intermissionThread;

    private static final int JOIN_TIMER = 500; // ms

    private static PriorityBlockingQueue<String> collisionQueue;
    private static PriorityBlockingQueue<String> collisionNextPacmanQueue;
    
    private static PriorityBlockingQueue<String> collisionConsumableQueue;
    private static LinkedTransferQueue<Consumable> consumableQueue;
    
    private static PriorityBlockingQueue<String> collisionGhostQueue;
    private static LinkedTransferQueue<Ghost> ghostQueue;
    
    private static PriorityBlockingQueue<String> stringBlinkyCorridorQueue;
    private static PriorityBlockingQueue<String> stringInkyCorridorQueue;
    private static PriorityBlockingQueue<String> stringPinkyCorridorQueue;
    private static PriorityBlockingQueue<String> stringClydeCorridorQueue;
    

    private IGameState initState;
    private IGameState stopState;
    private IGameState playingState;
    private IGameState pauseState;
    private IGameState resumeState;
    private IGameState currentState;
    private IGameState mainMenuState;
    private IGameState newHighscoreState;
    
    private int resumeTime = 3;

    private Level currentLevel;

    private boolean pacmanWon = false;

    private String levelDataFile = System.getProperty("user.dir") + File.separator + "assets" + File.separator + "map.txt";
    
    /**
     * Initialization function called by the engine when it lunch the game.
     */
    @Override
    public void init(IWindow w, GameController gc)
    {
        window = w;
        physicsThread = new PhysicsThread(this);
        currentLevel = new Level(levelDataFile, "1");
        maze = new ArrayList<Wall>();
        entities = new ArrayList<Entity>();
        physicsThread.setAuthTiles(currentLevel.getAuthTiles());

        collisionQueue = new PriorityBlockingQueue<String>(10);
        collisionNextPacmanQueue = new PriorityBlockingQueue<String>(10);
        
        collisionConsumableQueue = new PriorityBlockingQueue<String>(10);
        consumableQueue = new LinkedTransferQueue<Consumable>();
        
        collisionGhostQueue = new PriorityBlockingQueue<String>(10);
        ghostQueue = new LinkedTransferQueue<Ghost>();
        
        stringBlinkyCorridorQueue = new PriorityBlockingQueue<String>(10);
        stringInkyCorridorQueue = new PriorityBlockingQueue<String>(10);
        stringPinkyCorridorQueue = new PriorityBlockingQueue<String>(10);
        stringClydeCorridorQueue = new PriorityBlockingQueue<String>(10);
        
        double pacmanXBlinky = 0;
        double pacmanYBlinky = 0;
        
        double pacmanXInky = 0;
        double pacmanYInky = 0;
        
        loadEntities();
        if (pacman != null)
        {
        	IObserver observer = window.getGameView();
        	pacman.registerObserver(observer);
        }
        
        loadMusics();

        mainMenuState = new MainMenuState(this);
        initState = new InitState(this);
        pauseState = new PauseState(this);
        resumeState = new ResumeState(this);
        playingState = new PlayingState(this);
        stopState = new StopState(this);
        newHighscoreState = new NewHighscore();
        setState(mainMenuState);

        renderThread = new RenderThread(this, gc);
        audioThread = new AudioThread();
        renderThread.start();
        physicsThread.start();
        audioThread.start();
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

                if (currentLevel.getTile(x, y) >= Tile.WALL_START.getValue()
                        && currentLevel.getTile(x, y) <= Tile.WALL_END.getValue())
                {
                    maze.add(new Wall(x, y, currentLevel.getTile(x, y)));
                } else if (currentLevel.getTile(x, y) == Tile.PAC_MAN_START.getValue())
                {
                    pacman = new Pacman(x, y);
                    newDirectionPacman = new Pacman(getPacman().getHitBox().getX(), getPacman().getHitBox().getY());
                    nextTilesPacman = new Pacman(getPacman().getHitBox().getX(), getPacman().getHitBox().getY());
                    entities.add(pacman);
                    pacman.setAuthTiles(currentLevel.getAuthTiles());
                } else if (currentLevel.getTile(x, y) == Tile.BLINKY_START.getValue())
                {
                    entities.add(new Ghost(x, y, GhostType.BLINKY));
                    ((Ghost) getEntities().get(0)).setAuthTiles(currentLevel.getAuthTilesGhost(),
                            currentLevel.getAuthTilesGhostRoom());
                } else if (currentLevel.getTile(x, y) == Tile.PINKY_START.getValue())
                {
                    entities.add(new Ghost(x, y, GhostType.PINKY));
                    ((Ghost) getEntities().get(0)).setAuthTiles(currentLevel.getAuthTilesGhost(),
                            currentLevel.getAuthTilesGhostRoom());
                } else if (currentLevel.getTile(x, y) == Tile.INKY_START.getValue())
                {
                    entities.add(new Ghost(x, y, GhostType.INKY));
                    ((Ghost) getEntities().get(0)).setAuthTiles(currentLevel.getAuthTilesGhost(),
                            currentLevel.getAuthTilesGhostRoom());
                } else if (currentLevel.getTile(x, y) == Tile.CLYDE_START.getValue())
                {
                    entities.add(new Ghost(x, y, GhostType.CLYDE));
                    ((Ghost) getEntities().get(0)).setAuthTiles(currentLevel.getAuthTilesGhost(),
                            currentLevel.getAuthTilesGhostRoom());
                }
            }
        }
    }

    /**
     * Load all audio files and distribute them where they're needed.
     * 
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
            intermission = new Sound(Settings.INTERMISSION_PATH);
            intermission.setVolume(Settings.getMusicVolume());

            for (int index = 0; index < chomps.length; index++)
            {
                Sound chomp = new Sound(Settings.CHOMP_PATH);
                chomp.setVolume(Settings.getSoundsVolume());
                chomps[index] = chomp;
            }

            death = new Sound(Settings.DEATH_PATH);
            death.setVolume(Settings.getSoundsVolume());
        } catch (UnsupportedAudioFileException | IOException e)
        {
            System.out.println("Unable to load sounds!!");
            return false;
        }

        return true;
    }

    public void playStartingMusic(LineListener listener)
    {
        startMusic.setListener(listener);
        audioThread.addSound(startMusic);
    }

    public void playInGameMusic()
    {
        audioThread.addMusic(gameSiren);
    }

    public void stopMusic()
    {
        audioThread.stopMusic();
    }

    public void playDeathSound(LineListener listener)
    {
        death.setListener(listener);
        audioThread.addSound(death);
    }

    @Override
    public void setMusicVolume(int volume)
    {
        if (!Settings.isMusicMute())
        {
            gameSiren.setVolume(volume);
            startMusic.setVolume(volume);
            intermission.setVolume(volume);
        }
    }

    @Override
    public void setSoundsVolume(int volume)
    {
        if (!Settings.isSoundsMute())
        {
            for (Sound chomp : chomps)
            {
                chomp.setVolume(volume);
            }
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
        if (gameSiren != null && startMusic != null && intermission != null)
        {
            gameSiren.setVolume(0);
            startMusic.setVolume(0);
            intermission.setVolume(0);
        }
    }

    @Override
    public void muteSounds()
    {
        if (death != null && chomps != null)
        {
            death.setVolume(0);
            for (Sound chomp : chomps)
            {
                if (chomp == null)
                    continue;
                chomp.setVolume(0);
            }
        }
    }

    @Override
    public void resumeMusics()
    {
        if (!Settings.isMusicMute() && gameSiren != null && startMusic != null && intermission != null)
        {
            gameSiren.setVolume(Settings.getMusicVolume());
            startMusic.setVolume(Settings.getMusicVolume());
            intermission.setVolume(Settings.getMusicVolume());
        }
    }

    @Override
    public void resumeSounds()
    {
        if (!Settings.isSoundsMute() && death != null && chomps != null)
        {
            death.setVolume(Settings.getSoundsVolume());
            for (Sound chomp : chomps)
            {
                if (chomp == null)
                    continue;
                chomp.setVolume(Settings.getSoundsVolume());
            }
        }
    }

    public void setState(IGameState state)
    {
        if (state.getName() == StatesName.INIT)
        {
            window.showView(ViewType.GAME);
            window.setIsGameActive(true);
        } else if (state.getName() == StatesName.RESUME && currentState.getName() == StatesName.MAIN_MENU)
        {
            window.showView(ViewType.GAME);
        } else if (state.getName() == StatesName.STOP)
        {
            window.setIsGameActive(false);
        } 
        else if (state.getName() == StatesName.NEW_HIGHSCORE)
		{
			window.showView(ViewType.NEW_HIGHSCORE);
		}else if (state.getName() == StatesName.MAIN_MENU)
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

	public IGameState getNewHighscoreState()
	{
		return newHighscoreState;
	}
	
    public Sound getStartMusic()
    {
        return startMusic;
    }

    public Sound getGameSiren()
    {
        return gameSiren;
    }

    public Sound[] getChomps()
    {
        return chomps;
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

    public synchronized ArrayList<Wall> getMaze()
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

    public synchronized Level getCurrentLevel()
    {
        return currentLevel;
    }

    public synchronized PhysicsThread getPhysicsThread()
    {
        return physicsThread;
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
        Level level = new Level(levelDataFile, levelName);
        currentLevel = level;
        currentLevel.generateConsumables();
        physicsThread.setAuthTiles(currentLevel.getAuthTiles());
    }

    public synchronized List<GameObject> getGameObjects()
    {
        if (getMaze() != null && getConsumables() != null && getEntities() != null)
        {
            return Stream.of(getMaze(), getConsumables(), getEntities()).flatMap(x -> x.stream())
                    .collect(Collectors.toList());
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
    
    public double getPacmanXBlinky()
    {
    	return pacmanXBlinky;
    }
    
    public void setPacmanXBlinky(double value)
    {
    	pacmanXBlinky = value;
    }
    
    public double getPacmanYBlinky()
    {
    	return pacmanYBlinky;
    }
    
    public void setPacmanYBlinky(double value)
    {
    	pacmanYBlinky = value;
    }
    
    public double getPacmanXInky()
    {
    	return pacmanXInky;
    }
    
    public void setPacmanXInky(double value)
    {
    	pacmanXInky = value;
    }
    
    public double getPacmanYInky()
    {
    	return pacmanYInky;
    }
    
    public void setPacmanYInky(double value)
    {
    	pacmanYInky = value;
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
     * 
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

        audioThread.stopThread();
        synchronized (audioThread)
        {
            audioThread.notify();
            audioThread.join(JOIN_TIMER);
            if (audioThread.isAlive())
            {
                audioThread.interrupt();
                throw new InterruptedByTimeoutException();
            }
        }

        if (intermissionThread != null)
        {
            intermissionThread.stopThread();
            intermissionThread.join(JOIN_TIMER);
            if (intermissionThread.isAlive())
            {
                intermissionThread.interrupt();
                throw new InterruptedByTimeoutException();
            }
        }
    }

    public synchronized void pacmanEatConsummable(Consumable consumable)
    {
        pacman.eat(consumable);
        for (Sound chomp : chomps)
        {
            if (chomp == null)
                continue;
            if (chomp.getIsRunning())
                continue;
            audioThread.addSound(chomp);
            break;
        }
    }

    public void activateEnergizer()
    {
        if (currentState.getName() == StatesName.PLAY)
        {
            PlayingState state = (PlayingState) currentState;
            state.activateEnergizer();
            audioThread.addMusic(intermission);
        }
    }

    public ArrayList<Ghost> getGhosts()
    {
        ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
        for (Entity entity : entities)
        {
            if (entity instanceof Ghost)
            {
                Ghost ghost = (Ghost) entity;
                ghosts.add(ghost);
            }
        }
        return ghosts;
    }

    public TimerThread getIntermissionThread()
    {
        return intermissionThread;
    }

    public void setIntermissionThread(TimerThread timer)
    {
        intermissionThread = timer;
    }

    public void killGhost(Ghost ghost)
    {
        ghost.respawn();
    }

    public synchronized PriorityBlockingQueue<String> getCollisionQueue()
    {
        return collisionQueue;
    }

    public synchronized void addCollisionQueue(String s)
    {
        try
        {
            collisionQueue.offer(s);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public synchronized String readCollisionQueue()
    {
        try
        {
            return collisionQueue.poll();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public synchronized PriorityBlockingQueue<String> getCollisionNextPacmanQueue()
    {
        return collisionNextPacmanQueue;
    }

    public synchronized void addCollisionNextPacmanQueue(String s)
    {
        try
        {
            collisionNextPacmanQueue.offer(s);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public synchronized String readCollisionNextPacmanQueue()
    {
        try
        {
            return collisionNextPacmanQueue.poll();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized LinkedTransferQueue<Consumable> getConsumableQueue()
    {
        return consumableQueue;
    }

    public synchronized void addConsumableQueue(Consumable c)
    {
        try
        {
            consumableQueue.offer(c);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public synchronized Consumable readConsumableQueue()
    {
        try
        {
            return consumableQueue.poll();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized PriorityBlockingQueue<String> getcollisionConsumableQueue()
    {
        return collisionConsumableQueue;
    }

    public synchronized void addCollisionConsumableQueue(String s)
    {
        try
        {
            collisionConsumableQueue.offer(s);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public synchronized String readCollisionConsumableQueue()
    {
        try
        {
            return collisionConsumableQueue.poll();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public synchronized PriorityBlockingQueue<String> getCollisionGhostQueue()
    {
        return collisionGhostQueue;
    }
    
    public synchronized void addCollisionGhostQueue(String s)
    {
        try
        {
            collisionGhostQueue.offer(s);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public synchronized String readCollisionGhostQueue()
    {
        try
        {
            return collisionGhostQueue.poll();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public synchronized LinkedTransferQueue<Ghost> getGhostQueue()
    {
        return ghostQueue;
    }
    
    public synchronized void addGhostQueue(Ghost g)
    {
        try
        {
            ghostQueue.offer(g);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public synchronized Ghost readGhostQueue()
    {
        try
        {
            return ghostQueue.poll();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized PriorityBlockingQueue<String> getStringBlinkyCorridorQueue()
    {
        return stringBlinkyCorridorQueue;
    }
    
    public synchronized void addStringBlinkyCorridorQueue(String g)
    {
        try
        {
        	
            stringBlinkyCorridorQueue.offer(g);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public synchronized String readStringBlinkyCorridorQueue()
    {
        try
        {
            return stringBlinkyCorridorQueue.poll();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public synchronized PriorityBlockingQueue<String> getStringInkyCorridorQueue()
    {
        return stringInkyCorridorQueue;
    }
    
    public synchronized void addStringInkyCorridorQueue(String g)
    {
        try
        {
        	
            stringInkyCorridorQueue.offer(g);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public synchronized String readStringInkyCorridorQueue()
    {
        try
        {
            return stringInkyCorridorQueue.poll();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public synchronized PriorityBlockingQueue<String> getStringPinkyCorridorQueue()
    {
        return stringPinkyCorridorQueue;
    }
    
    public synchronized void addStringPinkyCorridorQueue(String g)
    {
        try
        {
        	
            stringPinkyCorridorQueue.offer(g);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public synchronized String readStringPinkyCorridorQueue()
    {
        try
        {
            return stringPinkyCorridorQueue.poll();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    public synchronized PriorityBlockingQueue<String> getStringClydeCorridorQueue()
    {
        return stringClydeCorridorQueue;
    }
    
    public synchronized void addStringClydeCorridorQueue(String g)
    {
        try
        {
        	
            stringClydeCorridorQueue.offer(g);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public synchronized String readStringClydeCorridorQueue()
    {
        try
        {
            return stringClydeCorridorQueue.poll();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
	public void setLevelDataFile(String lvl) 
	{
		levelDataFile = lvl;
	}
}
