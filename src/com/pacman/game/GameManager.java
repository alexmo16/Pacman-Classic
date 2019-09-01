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
	private boolean checkCollision = true;
	private int[][] map = null;
	private int x = 1;
	private int buffer = 0;
	Image pacmanSprite;
	Settings settings = new Settings();
	Maze maze = new Maze(settings);
	
	private boolean isPlaying = true;
	
	@Override
	public void init(Window window)
	{
		window.getFrame().add(maze);
		window.getFrame().pack();
		
		pacman = new PacmanObject(3*600/30-20,3*660/33-20,19,19,direction);
		maybeFuturPacman = new PacmanObject(3*600/30-20,3*660/33-20,19,19,direction);
		futurPacman = new PacmanObject(3*600/30-20,3*660/33-20,19,19,direction);
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
			System.out.println(pacman.getX());
			maybeFuturPacman.setLocation((int)pacman.getX(),(int)pacman.getY());
			futurPacman.setLocation((int)pacman.getX(),(int)pacman.getY());
			DynamicObject.updatePosition(futurPacman, direction);
			DynamicObject.updatePosition(maybeFuturPacman, oldDirection);

			
			checkCollision = CollisionManager.getInstance().collisionWall(futurPacman,map,20,20);

			
			if (checkCollision == false) {
				DynamicObject.updatePosition(pacman, direction);
				oldDirection = direction;
			} else {
				
				checkCollision = CollisionManager.getInstance().collisionWall(maybeFuturPacman,map,20,20);
				if (checkCollision == false) {
					DynamicObject.updatePosition(pacman, oldDirection);
				}

			}	
		}
		
	}

	@Override
	public void render(Window window) 
	{
		buffer += 1;
		if (buffer == 10) {
			x += 1;
			if (x == 4) {
				x = 1;
			}
			buffer = 1;
		}
		
		window.getFrame().repaint();
	
		//try {
		//	pacmanSprite = ImageIO.read(new File("assets"+File.separator+"pacman_"+direction+"_"+x+".png"));
		//	renderer.drawImage(pacmanSprite, (int) pacman.getX(), (int) pacman.getY());
		//} catch (IOException e) {
		//}
	}
	
	@Override
	public Settings getSettings()
	{
		return settings;
	}
}
