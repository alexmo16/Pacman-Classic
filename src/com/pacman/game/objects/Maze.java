package com.pacman.game.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.pacman.game.MazeData;
import com.pacman.game.Settings;
import com.pacman.game.SpritesManager;

public class Maze extends JPanel
{
	private static final long serialVersionUID = -1068513811376492599L;
	
	private final int mazeHeight,
					  mazeWidth;
	private final MazeData mazeData;
	private final SpritesManager spritesManager; 
	
	public Maze(Settings s)
	{
		mazeHeight = s.getMazeData().getHeight();
		mazeWidth = s.getMazeData().getWidth();
		mazeData = s.getMazeData();
		spritesManager = s.getSpritesManager();
		setBackground(Color.BLACK);
	}

    @Override
    public Dimension getPreferredSize() 
    {
        return new Dimension(mazeWidth, mazeHeight);
    }
    
    @Override
	public void paint(Graphics g) 
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        int size = Math.min(getWidth(), getHeight()) / mazeHeight;
        
        int y = (getHeight() - (size * mazeHeight)) / 2;
        for (int horz = 0; horz < mazeHeight; horz++) 
        {
            int x = (getWidth() - (size * mazeWidth)) / 2;
            for (int vert = 0; vert < mazeWidth; vert++) 
            {
            	
            	int type = mazeData.getTile(vert, horz);
            	
            	if (type != 0)
            	{
            		int[] k = spritesManager.getMazeTileCoords(type);
            		g.drawImage(spritesManager.getSpritesSheet(), x, y, x + size, y + size, k[0], k[1], k[2], k[3], null);
            	}
            	
                x += size;
            }
            y += size;
        }
        g2d.dispose();
    }
    
	public int getMazeHeight() 
	{
		return mazeHeight;
	}
	
	public int getMazeWidth() 
	{
		return mazeWidth;
	}
	
	public MazeData getmazeData() 
	{
		return mazeData;
	}
}
