package com.pacman.model.highscores;

public class Score 
{
	private int points;
	private String name;
	
	public Score(int p, String n)
	{
		points = p;
		name = n;
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public String getName()
	{
		return name;
	}
}
