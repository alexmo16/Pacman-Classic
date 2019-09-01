package com.pacman.game;



import java.awt.Image;

import com.pacman.engine.CollisionManager;
import com.pacman.engine.Engine;
import com.pacman.engine.IGame;
import com.pacman.engine.Inputs;
import com.pacman.engine.Window;
import com.pacman.game.objects.DynamicObject;
import com.pacman.game.objects.Maze;
import com.pacman.game.objects.PacmanObject;;

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
	Maze maze = new Maze(settings);
	
	private boolean isPlaying = true;
	
	@Override
	public void init(Window window)
	{
		window.getFrame().add(maze);
		window.getFrame().pack();
		
		pacman = new PacmanObject(3*600/30-20,3*660/33-20,19,19,direction, settings);
		maybeFuturPacman = new PacmanObject(3*600/30-20,3*660/33-20,19,19,direction, settings);
		futurPacman = new PacmanObject(3*600/30-20,3*660/33-20,19,19,direction, settings);
		map = maze.getmazeData().getTiles();
		
		isPlaying = true;
	}
	
	@Override
	public void update(Engine engine)
	{	
		Inputs inputs = engine.getInputs();
		if ( inputs.isKeyDown( settings.getMutedButton() ) )
		{
			Engine.toggleMute();
		}
		
		if ( inputs.isKeyDown( settings.getPauseButton() ) )
		{
			isPlaying = !isPlaying;
			Engine.setIsMuted( !isPlaying );
		}
		
		if ( isPlaying )
		{
			direction = PacmanObject.getNewDirection(direction);
			
			maybeFuturPacman.getRectangle().setLocation((int)pacman.getRectangle().getX(),(int)pacman.getRectangle().getY());
			futurPacman.getRectangle().setLocation((int)pacman.getRectangle().getX(),(int)pacman.getRectangle().getY());
			DynamicObject.updatePosition(futurPacman.getRectangle(), direction);
			DynamicObject.updatePosition(maybeFuturPacman.getRectangle(), oldDirection);

			checkCollision = CollisionManager.getInstance().collisionWall(futurPacman,map,20,20);
			System.out.println("x "+pacman.getRectangle().getX()+" y "+pacman.getRectangle().getY());
			
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
	public void render(Window window) 
	{
		
		window.getFrame().repaint();
	}
	
	@Override
	public Settings getSettings()
	{
		return settings;
	}
}
