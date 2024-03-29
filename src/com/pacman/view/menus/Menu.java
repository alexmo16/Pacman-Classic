package com.pacman.view.menus;

import java.awt.Graphics;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
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
	
	public MenuType getMenuType()
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
