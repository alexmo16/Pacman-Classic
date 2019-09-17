package com.pacman.view;

import java.awt.event.KeyListener;

import com.pacman.view.menus.MenuOption;
import com.pacman.view.views.ViewType;

public interface IWindow 
{
	public void showView(ViewType view);

	public ViewType getCurrentView();
	
	public void nextOption();
	public void previousOption();
	public MenuOption getMenuOption();
	public void setMainMenu();
	public void setAudioMenu();
	public void setHelpMenu();
	public void setIsGameActive(boolean state);
	
	public void addListener(KeyListener k);

	public void render();
	
	public void dispose();
}
