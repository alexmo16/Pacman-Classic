package com.pacman.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.pacman.model.Game;
import com.pacman.model.objects.Gum;
import com.pacman.model.objects.PacGum;
import com.pacman.model.objects.Pacman;
import com.pacman.model.states.StatesName;
import com.pacman.model.world.Tile;
import com.pacman.utils.Settings;

public class GameView extends JPanel
{
	private static final long serialVersionUID = 1594565623438214915L;  
	
	private Game game;
	private Sprites sprites;
	private int mazeHeight,
			    mazeWidth,
			    tileSize;
	
	
	public GameView(Game gm)
	{
		game = gm;
		sprites = Settings.SPRITES;
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		mazeHeight = game.getMaze().getHeight();
		mazeWidth = game.getMaze().getWidth();
        tileSize = Math.min(getHeight() / mazeHeight, getWidth() / mazeWidth);
        if ( (tileSize & 1) != 0 ) { tileSize--; } // Odd tile size will break tile scaling.
		
        renderBackground(g);
        renderMaze(g);
        renderGum(g);
        renderPacGum(g);
        renderPacman(g);
        renderScoreBar(g);
        renderPause(g);
	}
	
	private void renderBackground(Graphics g)
	{
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	private void renderMaze(Graphics g)
	{
		int [][] tiles = game.getMaze().getTiles();
        int y = (getHeight() - (tileSize * mazeHeight)) / 2;
        int xStart = (getWidth() - (tileSize * mazeWidth)) / 2;
        for (int horz = 0; horz < mazeHeight; horz++) 
        {
        	int x = xStart;
            for (int vert = 0; vert < mazeWidth; vert++) 
            {
            	if (tiles[vert][horz] >= Tile.WALL_START.getValue() && tiles[vert][horz] <= Tile.WALL_END.getValue())
            	{
            		int[] coords = sprites.getMazeTileCoords(tiles[vert][horz]);
            		g.drawImage(sprites.getSpritesSheet(), x, y, x + tileSize, y + tileSize, coords[0], coords[1], coords[2], coords[3], null);
            	}
                x += tileSize;
            }
            y += tileSize;
        }
	}
	
	private void renderGum(Graphics g)
	{
        ArrayList<Gum> gums = game.getGumList();
        int x, y;
        for (Gum gum : gums)
        {
            x = ((int) gum.getObject().getX() * tileSize) + (getWidth() - (tileSize * mazeWidth)) / 2;
            y = ((int) gum.getObject().getY() * tileSize) + (getHeight() - (tileSize * mazeHeight)) / 2;
            g.drawImage(sprites.getSpritesSheet(), x, y, x + tileSize, y + tileSize, gum.getSprite(0), gum.getSprite(1), gum.getSprite(2), gum.getSprite(3), null);  	
        }
	}
	
	private void renderPacGum(Graphics g)
	{
        ArrayList<PacGum> pacGums = game.getPacGumList();
        int x, y;
        for (PacGum pacGum : pacGums)
        {
            x = ((int) pacGum.getObject().getX() * tileSize) + (getWidth() - (tileSize * mazeWidth)) / 2;
            y = ((int) pacGum.getObject().getY() * tileSize) + (getHeight() - (tileSize * mazeHeight)) / 2;
            g.drawImage(sprites.getSpritesSheet(), x, y, x + tileSize, y + tileSize, pacGum.getSprite(0), pacGum.getSprite(1), pacGum.getSprite(2), pacGum.getSprite(3), null);  	
        }
	}
	
	private void renderPacman(Graphics g)
	{
        Pacman pacman = game.getPacman();
        double x = (pacman.getObject().getX() * tileSize - (tileSize / 2)) + (getWidth() - (tileSize * mazeWidth)) / 2;
        double y = (pacman.getObject().getY() * tileSize - (tileSize / 2)) + (getHeight() - (tileSize * mazeHeight)) / 2;
        int[] k = sprites.getPacmanCoords(pacman.getDirection());
        g.drawImage(sprites.getSpritesSheet(), (int) (x), (int) (y), (int) x + (2 * tileSize), (int) y + (2 * tileSize), k[0], k[1], k[2], k[3], null);
	}
	
	private void renderScoreBar(Graphics g)
    {
        int x = (getWidth() - mazeWidth * tileSize) / 2;
        int y = (getHeight() - tileSize);
        
        String s = new String("score " + game.getPacman().getScore());
        if (game.getCurrentState() != null) { s += " state " + game.getCurrentState().getName().getValue(); }
        if (game.getPacman().isCollision()) { s += " collision " + game.getPacman().getCollisionDirection(); }
        for (int i = 0; i < s.length(); i++)
        {
        	if (s.charAt(i) != ' ')
        	{
        		int[] k = sprites.getCharacterCoords(s.charAt(i));
        		g.drawImage(sprites.getSpritesSheet(), x + i * tileSize, y, x + i * tileSize + tileSize, y + tileSize, k[0], k[1], k[2], k[3], null);
        	}
        }
    }
    
	private void renderPause(Graphics g)
    {
        if (game.getCurrentState().getName() == StatesName.PAUSE)
        {
	        g.setColor(new Color(0, 0, 0, 200));
	        g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
	
}
