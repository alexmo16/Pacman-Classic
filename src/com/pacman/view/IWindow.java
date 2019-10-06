package com.pacman.view;

import java.awt.event.KeyListener;

import com.pacman.view.menus.MenuOption;
import com.pacman.view.menus.MenuType;
import com.pacman.view.views.ViewType;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public interface IWindow 
{
	public void showView(ViewType view);

	public ViewType getCurrentView();
	
	public void moveSelectionUp();
	public void moveSelectionDown();
	public void moveSelectionLeft();
	public void moveSelectionRight();
	public void nextOption();
	public void previousOption();
	public MenuOption getMenuOption();
	public MenuType getMenuType();
	public void setMainMenu();
	public void setHighscoresMenu();
	public void setAudioMenu();
	public void setHelpMenu();
	public void setIsGameActive(boolean state);
	public String getPlayerName();
	
	public void addListener(KeyListener k);

	public void render();
	
	public void dispose();
}
