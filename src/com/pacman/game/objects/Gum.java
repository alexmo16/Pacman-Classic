package com.pacman.game.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Gum extends Rectangle{
	
	private static final long serialVersionUID = 1L;

	public Gum(int x, int y) {
		setBounds(x, y, 32, 32);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect(x, y, 32, 32);
	}
}
