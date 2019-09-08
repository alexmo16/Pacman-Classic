package com.pacman.model.world;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import com.pacman.utils.CSVUtils;

public class Data
{
    private int[][] tiles = null;
    private int width = 0, height = 0;

    public Data(String worldFilePath)
    {
        try (Scanner scanner = new Scanner(new File(worldFilePath)))
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

    public int[][] getTiles()
    {
        return tiles;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
