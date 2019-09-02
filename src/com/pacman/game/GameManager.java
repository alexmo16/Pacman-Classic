package com.pacman.game;


import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.pacman.engine.CollisionManager;
import com.pacman.engine.Engine;
import com.pacman.engine.IGame;
import com.pacman.engine.Inputs;
import com.pacman.engine.Sound;
import com.pacman.engine.Window;
import com.pacman.game.objects.DynamicObject;
import com.pacman.game.objects.Gum;
import com.pacman.game.objects.PacGum;
import com.pacman.game.objects.PacmanObject;
import com.pacman.game.scenes.InGame;

public class GameManager implements IGame
{
	PacmanObject pacman;
	PacmanObject futurPacman;
	PacmanObject maybeFuturPacman;
	ArrayList<Gum> gumList;
	ArrayList<PacGum> pacGumList;
	String oldDirection = "right", direction = "right";
	private int checkCollision = 1;
	Settings settings = new Settings();
	InGame inGame = new InGame(settings);
	int startingPosition[];
	double pacmanBox;
	
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
		for (Gum gum : gumList) {
			inGame.addGameObject(gum);
		}
		
		for (PacGum pacGum : pacGumList)
		{
			inGame.addGameObject(pacGum);
		}
		
		inGame.init();
		
		CollisionManager.setSettings(settings);
		
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
            maybeFuturPacman.getRectangle().setRect(pacman.getRectangle().getX(),pacman.getRectangle().getY(),pacman.getRectangle().getWidth(),pacman.getRectangle().getHeight());
            futurPacman.getRectangle().setRect(pacman.getRectangle().getX(),pacman.getRectangle().getY(),pacman.getRectangle().getWidth(),pacman.getRectangle().getHeight());
			DynamicObject.updatePosition(futurPacman.getRectangle(), direction);
			DynamicObject.updatePosition(maybeFuturPacman.getRectangle(), oldDirection);

			checkCollision = CollisionManager.collisionWall(futurPacman);
			
			checkGumCollision();
			checkPacGumCollision();

			if(checkCollision == 2)
			{
				DynamicObject.tunnel(pacman.getRectangle(), direction);

			} else if (checkCollision == 0) {
				DynamicObject.updatePosition(pacman.getRectangle(), direction);
				pacman.setDirection(direction);
				oldDirection = direction;
			} else {
				
				checkCollision = CollisionManager.collisionWall(maybeFuturPacman);
				if (checkCollision == 2) {
					DynamicObject.tunnel(pacman.getRectangle(), oldDirection);
					pacman.setDirection(oldDirection);
				}
				if (checkCollision == 0) {
					DynamicObject.updatePosition(pacman.getRectangle(), oldDirection);
					pacman.setDirection(oldDirection);
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
		startingPosition = new int []{2,2};
		pacmanBox = 0.9;
		pacman = new PacmanObject(startingPosition[0],startingPosition[1],pacmanBox,pacmanBox,direction, settings);
		maybeFuturPacman = new PacmanObject(startingPosition[0],startingPosition[1],pacmanBox,pacmanBox,direction, settings);
		futurPacman = new PacmanObject(startingPosition[0],startingPosition[1],pacmanBox,pacmanBox,direction, settings);
		settings.getMazeData().getTiles();
		gumList = Gum.generateGumList(settings);
		pacGumList = PacGum.generatePacGumList(settings);
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
	
	private void checkGumCollision() {
		for (Gum gum : gumList) {
			if (CollisionManager.collisionObj(pacman, gum)) {
				pacman.eatGum(gum);
				gumList.remove(gum);
				gum.setVisible(false);
				gum = null;
				break;
			}
		}
	}
	
	private void checkPacGumCollision() {
		for (PacGum pacGum : pacGumList) {
			if (CollisionManager.collisionObj(pacman, pacGum)) {
				pacman.eatGum(pacGum);
				pacGumList.remove(pacGum);
				pacGum.setVisible(false);
				pacGum = null;
				break;
			}
		}
	}
}
