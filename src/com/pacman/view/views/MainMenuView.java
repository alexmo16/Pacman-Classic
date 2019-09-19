package com.pacman.view.views;

import java.awt.Color;
import java.awt.Graphics;

import com.pacman.view.menus.AudioMenu;
import com.pacman.view.menus.HelpMenu;
import com.pacman.view.menus.MainMenu;
import com.pacman.view.menus.Menu;
import com.pacman.view.menus.MenuOption;
import com.pacman.view.menus.MenuType;
import com.pacman.view.utils.Renderer;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class MainMenuView extends View
{	
	private static final long serialVersionUID = 1665591442723213675L;
	private static final ViewType name = ViewType.MAIN_MENU;
	
    private Menu mainMenu = new MainMenu();
    private Menu audioMenu = new AudioMenu();
    private Menu helpMenu = new HelpMenu();
    private Menu currentMenu = mainMenu;
    
	public void setIsGameActive(boolean state)
	{
		MenuOption option = state ? MenuOption.RESUME : MenuOption.START;
		mainMenu.updateOption(0, option);
	}
    
	public void setMainMenu()
	{
		currentMenu = mainMenu;
	}
	
	public void setAudioMenu()
	{
		currentMenu = audioMenu;
	}
	
	public void setHelpMenu()
	{
		currentMenu = helpMenu;
	}
	
	public MenuOption getMenuOption()
	{
		return currentMenu.getCurrentSelection();
	}
	
	public MenuType getMenuType()
	{
		return currentMenu.getMenuType();
	}
	
	public void nextOption()
	{
		currentMenu.next();
	}
	
	public void previousOption()
	{
		currentMenu.previous();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
		
        Renderer.renderBackground(g, Color.black, getWidth(), getHeight());
        currentMenu.render(g, getWidth(), getHeight(), sFactor);
	}
	
	@Override
	public String getName()
	{
		return name.getValue();
	}
}
