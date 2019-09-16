package com.pacman.view;

import java.awt.event.KeyListener;

public interface IWindow 
{
	public void showView(ViewType view);

	public void addListener(KeyListener k);

	public void render();
}
