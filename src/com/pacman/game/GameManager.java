package com.pacman.game;

import java.awt.Image;
import java.awt.Rectangle;
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
import com.pacman.game.DynamicObject;
import com.pacman.utils.CSVUtils;

public class GameManager implements IGame
{

	Rectangle pacman = null;
	String direction = "right";
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
		pacman = new Rectangle(10,10,settings.getWidth()/xMapSize-5,settings.getHeight()/yMapSize-5);
		for (int x=0; x<xMapSize;x++) {
			for (int y=0; y<yMapSize;y++) {
				System.out.print(map[x][y]+" ");
			}
			System.out.println("");
		}

	}
	
	@Override
	public void update(Engine engine)
	{
		direction = DynamicObject.getInstance().getNewDirection(direction);
		
		
		checkCollision = CollisionManager.getInstance().collisionWall(pacman,map,xMapSize,yMapSize);
		System.out.println("position x : "+pacman.getX()+" et y :"+pacman.getY());
		System.out.println(checkCollision);
		
		
		DynamicObject.getInstance().updatePosition(pacman, direction);
		
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
				if (map[x][y]>0) {
					try {
						wallSprite = ImageIO.read(new File("assets"+File.separator+"wall.png"));
						renderer.drawImage(wallSprite, x*28, y*31);
					} catch (IOException e) {
					}
				}
			}
			
		}
		
		try {
			pacmanSprite = ImageIO.read(new File("assets"+File.separator+"pacman_"+direction+"_"+x+".png"));
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
