package com.pacman.view.menus;

import java.awt.Graphics;

abstract public class Menu 
{
	private  MenuType menuType;
	private  MenuOption[] options;
	private int currentSelection = 0;
	
	public Menu(MenuType n, MenuOption[] opt)
	{
		menuType = n;
		options = opt;
	}
	
	abstract public void render(Graphics g, int w, int h, int s);
	
	public MenuOption[] getOptions() 
	{
		return options;
	}
	
	public MenuType getMenuState()
	{
		return menuType;
	}
	
	public String getName()
	{
		return menuType.getValue();
	}
	
	public synchronized MenuOption getCurrentSelection()
	{
		return options[currentSelection];
	}
	
	public synchronized void next()
	{
		if (currentSelection + 1 < options.length)
		{
			currentSelection++;
		}
	}
	
	public synchronized void previous()
	{
		if (currentSelection - 1 >= 0)
		{
			currentSelection--;
		}
	}
	
	public void updateOption(int idx, MenuOption value)
	{
		if (idx >= 0 && idx < options.length)
		{
			options[idx] = value;
		}
	}
}
