package com.pacman.game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.pacman.engine.CollisionManager;
import com.pacman.engine.Engine;
import com.pacman.engine.IGame;
import com.pacman.engine.Inputs;
import com.pacman.engine.Renderer;
import com.pacman.game.object.DynamicObject;
import com.pacman.game.object.PacmanObject;
import com.pacman.utils.CSVUtils;

public class GameManager implements IGame
{

	PacmanObject pacman;
	PacmanObject futurPacman;
	PacmanObject maybeFuturPacman;
	String oldDirection = "right", direction = "right";
	private int x = 1;
	private int buffer = 0;
	Image pacmanSprite;
	Image wallSprite;
	Settings settings = new Settings();
	private int[][] map = null;
	private int xMapSize = 0, yMapSize = 0;
	private boolean checkCollision = true;
	
	@Override
	public void init()
	{
		loadMapInfosFromFile();
		pacman = new PacmanObject(3*600/30-20,3*660/33-20,19,19,direction);
		maybeFuturPacman = new PacmanObject(3*600/30-20,3*660/33-20,19,19,direction);
		futurPacman = new PacmanObject(3*600/30-20,3*660/33-20,19,19,direction);
		

	}
	
	@Override
	public void update(Engine engine)
	{
		direction = PacmanObject.getNewDirection(direction);
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

		
		Inputs inputs = engine.getInputs();
		if ( inputs.isKeyDown( settings.getMutedButton() ) )
		{
			Engine.toggleMute();
		}

	}

	@Override
	public void render(Renderer renderer ) 
	{
		buffer += 1;
		if (buffer == 10) {
			x += 1;
			if (x == 4) {
				x = 1;
			}
			buffer = 1;
		}
		
		for (int x=0; x<xMapSize;x++) {
			for (int y=0; y<yMapSize;y++) {
				if (map[x][y]==0) {
					try {
						wallSprite = ImageIO.read(new File("assets"+File.separator+"wall.png")).getScaledInstance(20, 20, Image.SCALE_FAST );
						renderer.drawImage(wallSprite, x*600/30, y*660/33);
					} catch (IOException e) {
					}
				}
			}
			
		}
		
		try {
			pacmanSprite = ImageIO.read(new File("assets"+File.separator+"pacman.png")).getScaledInstance(20, 20, Image.SCALE_FAST);
			renderer.drawImage(pacmanSprite, (int) pacman.getX(), (int) pacman.getY());
		} catch (IOException e) {
		}

	}
	
	@Override
	public Settings getSettings()
	{
		return settings;
	}
	
	private void loadMapInfosFromFile()
	{
        try (Scanner scanner = new Scanner(new File(settings.getMapFilePath())))
        {
        	List<String> line = null;

        	// Get the size of the map
			if (scanner.hasNext())
			{
				line = CSVUtils.parseLine(scanner.nextLine());
				if (line.size() == 2)
				{
					xMapSize = Integer.parseInt(line.get(0));
					yMapSize = Integer.parseInt(line.get(1));
				}
				else
				{
					throw new Exception("The first line of the map file has incorrect format");
				}
			}
        	else
        	{
        		throw new Exception("The map file is empty");
        	}

    		// Get the data of the map
    		map = new int[xMapSize][yMapSize];
    		
    		for (int y = 0; y < yMapSize; y++)
    		{
    			if (scanner.hasNext())
    			{
    				line = CSVUtils.parseLine(scanner.nextLine());
	    			for (int x = 0; x < xMapSize; x++)
	    			{
	    				if (line.size() == xMapSize)
	    				{    			
	    					map[x][y] = Integer.parseInt(line.get(x));
	    				}
	    				else
	    				{
	    					throw new Exception("The map file does not have consistent column count");
	    				}
	    			}
    			}
    			else
    			{
    				throw new Exception("The map file does not have consistent row count");
    			}
    		}
    		
			scanner.close();
        }
        catch (Exception e)
        {
        	System.out.println("Exception during map init : " + e.toString());
        }
        
	}
	
	/*private void printMapInConsol()
	{
		for (int y = 0; y < yMapSize; y++)
		{
			for (int x = 0; x < xMapSize; x++)
			{
				System.out.print(map[x][y] + ", ");
			}
			System.out.print("\n");
		}
	}*/
}
