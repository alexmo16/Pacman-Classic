package com.pacman.game.objects;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.pacman.game.Settings;
import com.pacman.game.SpritesManager;

public class Maze extends JPanel
{
	private static final long serialVersionUID = -8520066424473091119L;

	private final int mazeHeight,
					  mazeWidth;
	private int[][] tiles;
	private final SpritesManager spritesManager; 

	public Maze(Settings s)
	{
		mazeHeight = s.getMazeData().getHeight();
		mazeWidth = s.getMazeData().getWidth();
		tiles = s.getMazeData().getTiles();
		spritesManager = s.getSpritesManager();
		setOpaque(false);
	}

    @Override
	public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        // We want all the tiles of the maze to take the maximum space possible.
        int tileSize = Math.min(getHeight() / mazeHeight, getWidth() / mazeWidth);
        if ( (tileSize & 1) != 0 ) { tileSize--; } // Odd tile size will break tile scaling.
        
        // Set stating location so the maze is centered.
        int y = (getHeight() - (tileSize * mazeHeight)) / 2;
        int xStart = (getWidth() - (tileSize * mazeWidth)) / 2;
        int x;
        int[] coords;
        
        // The drawing logic gets done here
        for (int horz = 0; horz < mazeHeight; horz++) 
        {
            x = xStart;
            for (int vert = 0; vert < mazeWidth; vert++) 
            {
            	if (tiles[vert][horz] > 0 && tiles[vert][horz] <= 24)
            	{
            		coords = spritesManager.getMazeTileCoords(tiles[vert][horz]);
            		g.drawImage(spritesManager.getSpritesSheet(), x, y, x + tileSize, y + tileSize, coords[0], coords[1], coords[2], coords[3], null);
            	}
                x += tileSize;
            }
            y += tileSize;
        }
    }
}
