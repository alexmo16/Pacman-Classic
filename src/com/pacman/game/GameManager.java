package com.pacman.game;

import java.awt.Rectangle;
import java.io.File;
import java.util.List;
import java.util.Scanner;

import com.pacman.engine.Engine;
import com.pacman.engine.IGame;
import com.pacman.engine.Inputs;
import com.pacman.engine.Renderer;
import com.pacman.game.DynamicObject;
import com.pacman.utils.CSVUtils;

public class GameManager implements IGame
{

	Rectangle pacman = new Rectangle(10,10,50,50);
	String direction = "right";
	Settings settings = new Settings();
	private int[][] map = null;
	private int xMapSize = 0, yMapSize = 0;
	
	private boolean isPlaying = true;
	
	@Override
	public void init()
	{
		loadMapInfosFromFile();
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
			direction = DynamicObject.getInstance().getNewDirection(direction);
			DynamicObject.getInstance().updatePosition(pacman, direction);
		}
		
	}

	@Override
	public void render(Renderer renderer ) 
	{
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
