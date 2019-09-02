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
		super.paintComponent(g);
        int size = Math.min(getWidth(), getHeight()) / mazeHeight;
        
        int y = (getHeight() - (size * mazeHeight)) / 2;
        for (int horz = 0; horz < mazeHeight; horz++) 
        {
            int x = (getWidth() - (size * mazeWidth)) / 2;
            for (int vert = 0; vert < mazeWidth; vert++) 
            {
            	
            	int type = mazeData.getTile(vert, horz);
            	
            	if (type == 0)
            	{
            		int[] k = spritesManager.getGumCoords();

            		g.drawImage(spritesManager.getSpritesSheet(), x, y, x + size, y + size, k[0], k[1], k[2], k[3], null);
            	}
            	
                x += size;
            }
            y += size;
        }
	}
	
	public static ArrayList<Gum> generateGumList(Settings s) {
		ArrayList<Gum> gumList = new ArrayList<>();
		
		for (int x = 0; x < mazeHeight; x++) {
			for (int y = 0; y < mazeWidth; y++) {
				
				if (mazeData.getTile(y, x) == 0) {
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


