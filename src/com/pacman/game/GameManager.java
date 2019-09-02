package com.pacman.game;

import java.awt.Image;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.pacman.engine.CollisionManager;
import com.pacman.engine.Engine;
import com.pacman.engine.IGame;
import com.pacman.engine.Inputs;
import com.pacman.engine.Sound;
import com.pacman.engine.Window;
import com.pacman.game.objects.DynamicObject;
import com.pacman.game.objects.PacmanObject;
import com.pacman.game.scenes.InGame;

public class GameManager implements IGame
{
	PacmanObject pacman;
	PacmanObject futurPacman;
	PacmanObject maybeFuturPacman;
	String oldDirection = "right", direction = "right";
	private int checkCollision = 1;
	private int[][] map = null;
	Image pacmanSprite;
	Settings settings = new Settings();
	InGame inGame = new InGame(settings);
	
	private boolean isPlaying = true;
	private boolean isStartingNewGame = true;
	
	Sound startMusic;
	Sound gameSiren;
	boolean isUserMuted = false; // pour savoir si c'est un mute system ou effectue par le user.
	
	@Override
	public void init(Window window)
	{	
		createGameObjects();
		loadMusics();
		inGame.addGameObject(pacman);
		inGame.init();
		
		window.getFrame().add(inGame);
		window.getFrame().pack();

		// TODO mettre ca a true seulement quand on clic sur le start button
		isStartingNewGame = true;
	}
	
	@Override
	public void update(Engine engine)
	{	
		Inputs inputs = engine.getInputs();
		if ( inputs.isKeyDown( settings.getPauseButton() ) )
		{
			togglePauseGame();
		}
		
		if ( isStartingNewGame )
		{
			startMusic.playSynchronously();
			isStartingNewGame = false;
			isPlaying = true;
			gameSiren.playLoopBack();
		}
		
		if ( isPlaying )
		{
			if( inputs.isKeyDown( settings.getMutedButton() ) )
			{
				toggleUserMuteSounds();
			}
			direction = PacmanObject.getNewDirection(engine.getInputs(), direction);
            maybeFuturPacman.getRectangle().setLocation((int)pacman.getRectangle().getX(),(int)pacman.getRectangle().getY());
            futurPacman.getRectangle().setLocation((int)pacman.getRectangle().getX(),(int)pacman.getRectangle().getY());
			DynamicObject.updatePosition(futurPacman.getRectangle(), direction);
			DynamicObject.updatePosition(maybeFuturPacman.getRectangle(), oldDirection);
			pacman.updatePosition();

			checkCollision = CollisionManager.getInstance().collisionWall(futurPacman,map,20,20);
			
			
			if(checkCollision == 2)
			{
				DynamicObject.tunnel(pacman.getRectangle(), direction);

			}
			
			if (checkCollision == 0) {
				DynamicObject.updatePosition(pacman.getRectangle(), direction);
				oldDirection = direction;
			} else {
				
				checkCollision = CollisionManager.getInstance().collisionWall(maybeFuturPacman,map,20,20);
				if (checkCollision == 0) {
					DynamicObject.updatePosition(pacman.getRectangle(), oldDirection);
				}

			}
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
			startMusic = new Sound( "./assets/pacman_beginning.wav" );
			gameSiren = new Sound( "./assets/siren.wav" );
		} catch (UnsupportedAudioFileException | IOException e) 
		{
			System.out.println( "Unable to load sounds!!" );
			return false;
		}
		
		return true;
	}
	
	private void createGameObjects()
	{
		pacman = new PacmanObject(3*600/30-20,3*660/33-20,19,19,direction, settings);
		maybeFuturPacman = new PacmanObject(3*600/30-20,3*660/33-20,19,19,direction, settings);
		futurPacman = new PacmanObject(3*600/30-20,3*660/33-20,19,19,direction, settings);
		map = settings.getMazeData().getTiles();
	}
	
	private void toggleUserMuteSounds()
	{
		Engine.toggleMute();
		isUserMuted = Engine.getIsMuted();
		if ( Engine.getIsMuted() )
		{
			gameSiren.stop();	
		}
		else
		{
			gameSiren.playLoopBack();
		}
	}
	
	private void togglePauseGame()
	{
		inGame.togglePausePane();
		isPlaying = !isPlaying;
		
		if( isPlaying )
		{
			if ( !isUserMuted )
			{
				Engine.setIsMuted( false );
				gameSiren.playLoopBack();
			}
		}
		else
		{
			Engine.setIsMuted( true );
			gameSiren.stop();
		}
	}
}
