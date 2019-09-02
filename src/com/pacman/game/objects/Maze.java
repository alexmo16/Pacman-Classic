package com.pacman.game.objects;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.pacman.game.MazeData;
import com.pacman.game.Settings;
import com.pacman.game.SpritesManager;

public class Maze extends JPanel
{
	private static final long serialVersionUID = -8520066424473091119L;
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
		setOpaque(false);
	}
    
    @Override
	public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        int size = Math.min(getWidth(), getHeight()) / mazeHeight;
        
        int y = (getHeight() - (size * mazeHeight)) / 2;
        for (int horz = 0; horz < mazeHeight; horz++) 
        {
            int x = (getWidth() - (size * mazeWidth)) / 2;
            for (int vert = 0; vert < mazeWidth; vert++) 
            {
            	
            	int type = mazeData.getTile(vert, horz);
            	
            	if (type > 0 && type <= 24)
            	{
            		int[] k = spritesManager.getMazeTileCoords(type);

            		g.drawImage(spritesManager.getSpritesSheet(), x, y, x + size, y + size, k[0], k[1], k[2], k[3], null);
            	}
            	
                x += size;
            }
            y += size;
        }
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
