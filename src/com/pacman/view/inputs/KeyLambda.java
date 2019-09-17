package com.pacman.view.inputs;

import java.awt.event.KeyEvent;

@FunctionalInterface
public interface KeyLambda
{
	public void run(KeyEvent e);
}
