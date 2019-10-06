package com.pacman.model.highscores;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pacman.utils.CSVUtils;

public final class Highscores 
{	
	private final static int HIGHSCORE_COUNT = 5;
	
	private final static String filePath = System.getProperty("user.dir") + File.separator + "assets" + File.separator + "highscores.txt";
	private static ArrayList<Score> highscores = new ArrayList<Score>();
	
	static
	{
    	try (Scanner scanner = new Scanner(new File(filePath)))
        {
        	List<String> line = null;
        	int scoreCount = 0;
        	while(scanner.hasNext())
        	{
        		if (scoreCount < HIGHSCORE_COUNT)
        		{
	                line = CSVUtils.parseLine(scanner.nextLine());
	                if (line.size() == 2)
	                {
	                	highscores.add(scoreCount, new Score(Integer.parseInt(line.get(0)), line.get(1)));
	                } 
	                else
	                {
	                    throw new Exception("The highscores file does not have consistent column count");
	                }
        		}
        		else
        		{
        			throw new Exception("The highscores file must contains " + HIGHSCORE_COUNT + " highscore, but contains more");
        		}
                scoreCount++;
        	}
        	
            scanner.close();
        } 
        catch (Exception e)
        {
            System.out.println("Exception during Highscores construction : " + e.toString());
        }
	}
	
	private Highscores() {}
	
	private static void save()
	{
		try
		{
		    FileWriter writer = new FileWriter(filePath);
		    for (Score s : highscores)
		    {
		    	if (s.getName() == null && s.getName() == "")
		    	{
		    		System.out.println("Name can't be empty or null");
		    	}
		    	writer.write(s.getPoints() + "," + s.getName() + "\n");
		    }
		    writer.close();
		}
        catch (Exception e)
        {
            System.out.println("Exception during Highscores save : " + e.toString());
        }
	}
	
	public static ArrayList<Score> get()
	{
		return highscores;
	}
	
	public static boolean isNew(int points)
	{
		for (int i = 0; i < HIGHSCORE_COUNT; i++)
		{
			if (points >= highscores.get(i).getPoints())
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static void setNew(Score newScore)
	{		
		for (int i = 0; i < HIGHSCORE_COUNT; i++)
		{
			if (newScore.getPoints() >= highscores.get(i).getPoints())
			{
				highscores.add(i, newScore);
				highscores.remove(HIGHSCORE_COUNT);
				save();
				return;
			}
		}
	}
	
	
}
