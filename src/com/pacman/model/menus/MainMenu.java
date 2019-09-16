package com.pacman.model.menus;

import java.util.ArrayList;
import java.util.ListIterator;

public class MainMenu 
{
	private MenuOption start = MenuOption.START;
	private MenuOption resume = MenuOption.RESUME;
	private MenuOption audio = MenuOption.AUDIO;
	private MenuOption help = MenuOption.HELP;
	private MenuOption exit = MenuOption.EXIT;
	
	private ArrayList<MenuOption> options = new ArrayList<MenuOption>();
	private volatile ListIterator<MenuOption> optionsIterator;
	private volatile MenuOption currentSelection;
	
	public MainMenu()
	{
		options.add(start);
		options.add(audio);
		options.add(help);
		options.add(exit);
		optionsIterator = options.listIterator();
		currentSelection = start;
	}

	public void setStartState()
	{
		if (options.contains(resume))
		{
			options.set(options.indexOf(resume), start);
			currentSelection = start;
		}
	}
	
	public void setResumeState()
	{
		if (options.contains(start))
		{
			options.set(options.indexOf(start), resume);
			currentSelection = resume;
		}
	}
	
	public MenuOption getState()
	{
		if (options.contains(start))
		{
			return start;
		}
		else
		{
			return resume;
		}
	}
	
	public synchronized ArrayList<MenuOption> getOptions() 
	{
		return options;
	}
	
	public synchronized MenuOption getCurrentSelection()
	{
		return currentSelection;
	}
	
	public synchronized void next()
	{
		if (optionsIterator.hasNext())
		{
			currentSelection = optionsIterator.next();
		}
	}
	
	public synchronized void previous()
	{
		if (optionsIterator.hasPrevious()) 
		{
			currentSelection = optionsIterator.previous();
		}
	}
}	
