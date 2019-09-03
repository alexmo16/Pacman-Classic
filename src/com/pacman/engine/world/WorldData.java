package com.pacman.engine.world;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import com.pacman.utils.CSVUtils;

public class WorldData
{
    private int[][] tiles = null;
    private int width = 0, height = 0;
    private int[] startPosition = null;

    public WorldData(String mapFilePath)
    {
        try (Scanner scanner = new Scanner(new File(mapFilePath)))
        {
            List<String> line = null;

            // Get the size of the maze
            if (scanner.hasNext())
            {
                line = CSVUtils.parseLine(scanner.nextLine());
                if (line.size() == 2)
                {
                    width = Integer.parseInt(line.get(0));
                    height = Integer.parseInt(line.get(1));
                } else
                {
                    throw new Exception("The first line of the map file has incorrect format");
                }
            } else
            {
                throw new Exception("The map file is empty");
            }

            // Get the data of the maze
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
                            if (tiles[col][row] == 60)
                            {
                                startPosition = new int[]
                                { col, row };
                            }
                        } else
                        {
                            throw new Exception("The maze file does not have consistent column count");
                        }
                    }
                } else
                {
                    throw new Exception("The maze file does not have consistent row count");
                }
            }

            scanner.close();
        } catch (Exception e)
        {
            System.out.println("Exception during maze init : " + e.toString());
        }
    }

    public int getTile(int x, int y)
    {
        return tiles[x][y];
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

    public int[] getStartPosition()
    {
        return startPosition;
    }
}
