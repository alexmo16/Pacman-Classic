package com.pacman.game.objects;

import java.awt.Graphics;

import com.pacman.engine.objects.SceneObject;
import com.pacman.game.Settings;
import com.pacman.game.SpritesManager;
import com.pacman.game.WorldTile;

public class Maze extends SceneObject
{
	private static final long serialVersionUID = -8520066424473091119L;

	private final int mazeHeight,
					  mazeWidth;
	private int[][] tiles;
	private final SpritesManager spritesManager; 

	public Maze(Settings s)
	{
		mazeHeight = s.getWorldData().getHeight();
		mazeWidth = s.getWorldData().getWidth();
		tiles = s.getWorldData().getTiles();
		spritesManager = s.getSpritesManager();
	}

    @Override
    protected void paintComponent(Graphics g)
    {
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
            	if (tiles[vert][horz] >= WorldTile.WALL_START.getValue() && tiles[vert][horz] <= WorldTile.WALL_END.getValue())
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
