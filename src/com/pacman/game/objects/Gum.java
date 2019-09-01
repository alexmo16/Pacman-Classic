package com.pacman.game.objects;

import java.awt.Graphics;
import java.awt.Graphics2D;

import com.pacman.game.Settings;

public class Gum extends StaticObject{

	private static final long serialVersionUID = 1L;
	protected final int SCORE = 10;
	
	public Gum() {
		super();
	}
	
	public Gum(int x, int y, int width, int height, Settings s) {
		super(x, y, width, height, s);
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g.create();
		int size = Math.min(getWidth(), getHeight()) / mazeHeight;
	}
}
