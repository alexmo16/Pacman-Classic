package com.pacman.model.world;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pacman.model.objects.consumables.Consumable;
import com.pacman.model.objects.consumables.Energizer;
import com.pacman.model.objects.consumables.PacDot;
import com.pacman.utils.CSVUtils;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class Level
{
    private static int[][] tiles = null;
    private static int width = 0, height = 0;
    private String name;
    private static final int[] AUTH_TILES = { Tile.FLOOR.getValue(), Tile.GUM.getValue(), Tile.ENERGIZER.getValue(), Tile.FRUIT.getValue(), Tile.PAC_MAN_START.getValue(), Tile.TUNNEL.getValue() };
    private static final int[] AUTH_TILES_GHOST = { Tile.FLOOR.getValue(), Tile.GUM.getValue(), Tile.ENERGIZER.getValue(), Tile.FRUIT.getValue(), Tile.PAC_MAN_START.getValue() };
    private static final int[] AUTH_TILES_GHOST_ROOM = { Tile.FLOOR.getValue(), Tile.GUM.getValue(), Tile.ENERGIZER.getValue(), Tile.FRUIT.getValue(),
    		Tile.PAC_MAN_START.getValue(), Tile.DOOR_1.getValue(), Tile.DOOR_2.getValue(),
    		Tile.DOOR_3.getValue(), Tile.DOOR_4.getValue(),Tile.BLINKY_START.getValue(),
    		Tile.PINKY_START.getValue(), Tile.INKY_START.getValue(), Tile.CLYDE_START.getValue() };
    
    private volatile ArrayList<Consumable> consumables;
    private volatile ArrayList<PacDot> pacdots;
    private volatile ArrayList<Energizer> energizers;
    
    public Level(String worldFilePath, String levelName)
    {
    	name = levelName;
    	loadLevelInformations(worldFilePath);
        generateConsumables();
    }

    public int[] findFirstInstanceOF(int value)
    {
    	for (int y = 0; y < height; y++)
    	{
    		for (int x = 0; x < width; x++)
    		{
    			if (tiles[x][y] == value)
    			{
    				return new int[]{x, y};
    			}
    		}
    	}
    	return null;
    }
    
    public synchronized Consumable getConsumableAtCoords(double x, double y)
    {
        for (Consumable consumable : consumables)
        {
            if (consumable.getHitBoxX() - 0.20 == x && consumable.getHitBoxY() - 0.20 == y)
            {
                return consumable;
            }
        }
        return null;
    }
    
    public int getTile(int x, int y)
    {
    	if (x < 0 || x > width || y < 0 || y > height)
    	{
    		return -1;
    	}
		else
    	{
    		return tiles[x][y];
    	}
    }

    public static int[][] getTiles()
    {
        return tiles;
    }

    public static int getWidth()
    {
        return width;
    }

    public static int getHeight()
    {
        return height;
    }
    
    public int[] getAuthTiles()
    {
    	return AUTH_TILES;
    }
    
    public int[] getAuthTilesGhost()
    {
    	return AUTH_TILES_GHOST;
    }
    
    public int[] getAuthTilesGhostRoom()
    {
    	return AUTH_TILES_GHOST_ROOM;
    }
    
    public String getName()
    {
    	return name;
    }
    
    public synchronized ArrayList<Consumable> getConsumables()
    {
    	return consumables;
    }
    
    public synchronized void setConsumables(ArrayList<Consumable> list)
    {
        this.consumables = list;
    }
    
    public synchronized ArrayList<PacDot> getPacDots()
    {
    	return pacdots;
    }
    
    public synchronized ArrayList<Energizer> getEnergizers()
    {
    	return energizers;
    }
    
    private void loadLevelInformations(String filePath)
    {
    	try (Scanner scanner = new Scanner(new File(filePath)))
        {
            loadWorldSize(scanner);
            loadWorldPositions(scanner);
            
            scanner.close();
        } 
        catch (Exception e)
        {
        	width = 0;
        	height = 0;
        	tiles = null;
            System.out.println("Exception during WorldData construction : " + e.toString());
        }
    }
    
    private void loadWorldSize(Scanner scanner) throws Exception
    {
    	List<String> line = null;
        // Get the size of the world
        if (scanner.hasNext())
        {
            line = CSVUtils.parseLine(scanner.nextLine());
            if (line.size() == 2)
            {
                width = Integer.parseInt(line.get(0));
                height = Integer.parseInt(line.get(1));
            } 
            else
            {
                throw new Exception("The first line of the world file has incorrect format");
            }
        } 
        else
        {
            throw new Exception("The world file is empty");
        }
    }
    
    private void loadWorldPositions(Scanner scanner) throws Exception
    {
    	List<String> line = null;
    	// Get the data of the world
        tiles = new int[width][height];
        for (int row = 0; row < height; row++)
        {
            if (scanner.hasNext())
            {
                line = CSVUtils.parseLine(scanner.nextLine());
                for (int col = 0; col < width; col++)
                {
                    if (line.size() == width)
                    {
                        tiles[col][row] = Integer.parseInt(line.get(col));
                    } 
                    else
                    {
                        throw new Exception("The world file does not have consistent column count");
                    }
                }
            } 
            else
            {
                throw new Exception("The world file does not have consistent row count");
            }
        }
    }
    
    public synchronized void generateConsumables()
    {
    	consumables = new ArrayList<Consumable>();
        pacdots = new ArrayList<PacDot>();
        energizers = new ArrayList<Energizer>();
        
        for (int y = 0; y < getHeight(); y++)
        {
            for (int x = 0; x < getWidth(); x++)
            {

                if (getTile(x, y) == Tile.GUM.getValue())
                {
                	PacDot pacdot = new PacDot(x, y);
                	pacdots.add(pacdot);
                	consumables.add(pacdot);
                }
                else if (getTile(x, y) == Tile.ENERGIZER.getValue())
                {
                	Energizer energizer = new Energizer(x, y); 
                	energizers.add(energizer);
                	consumables.add(energizer);
                }
            }
        }
    }
}
