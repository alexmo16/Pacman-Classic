package com.pacman.game.objects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

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
	public int getPoint()
	{
		return this.SCORE;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g.create();
		int size = Math.min(getWidth(), getHeight()) / mazeHeight;
	}
	
	public static ArrayList<Gum> generateGumList(Settings s) {
		ArrayList<Gum> gumList = new ArrayList<>();
		
		for (int x = 0; x < mazeHeight; x++) {
			for (int y = 0; y < mazeWidth; y++) {
				int type = mazeData.getTile(y, x);
				
				if (type == 0) {
					gumList.add(new Gum(x, y, 10, 10, s));
				}
			}
		}
		
		return gumList;
	}
	
	@Override
	public String toString() {
		return "x : " + this.object.getX() + "\n Y : " + this.object.getY();
	}
}


