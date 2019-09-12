package com.pacman.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JPanel;

import com.pacman.model.Game;
import com.pacman.model.objects.GameObject;
import com.pacman.model.objects.Sprite;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.states.StatesName;
import com.pacman.utils.Settings;

public class GameView extends JPanel
{
	private static final long serialVersionUID = 1594565623438214915L;  
	
	private Game game;
	private int tileSize;
	private boolean debug = false;
	
	public GameView(Game gm)
	{
		game = gm;
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
        tileSize = Math.min(getHeight() / Settings.WORLD_DATA.getHeight(), getWidth() / Settings.WORLD_DATA.getWidth());
        if ( (tileSize & 1) != 0 ) { tileSize--; } // Odd tile size will break tile tileSize.

        renderBackground(g);
        renderGameObjects(g);
        renderEntities(g);
        renderStatusBar(g);
        renderPause(g);
	}
	
	private void renderBackground(Graphics g)
	{
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	private void renderGameObjects(Graphics g)
	{
        List<GameObject> gameObjects = Stream.of(game.getMaze(), game.getConsumables()).flatMap(x -> x.stream()).collect(Collectors.toList());
		
        double x, y;
        for (GameObject obj : gameObjects)
        {
        	//  Scaled position of the object      + Offset to fit in the world 
            x = (obj.getHitBox().getX() * tileSize) + ((getWidth() - (tileSize * Settings.WORLD_DATA.getWidth())) / 2);
            y = (obj.getHitBox().getY() * tileSize) + ((getHeight() - (tileSize * Settings.WORLD_DATA.getHeight())) / 2);
            g.drawImage(Settings.SPRITES.getSpritesSheet(), (int)x, (int)y, (int)(x + tileSize), (int)(y + tileSize), obj.getSprite().getX1(), obj.getSprite().getY1(), obj.getSprite().getX2(), obj.getSprite().getY2(), null);  
            
            if (debug)
            {
	            g.setColor(new Color(100, 0, 0, 200));
	            g.drawRect((int)x, (int)y, tileSize - 1, tileSize - 1);
            }
        }
	}
	
	private void renderEntities(Graphics g)
	{        
        double x, y;
        for (Entity entity : game.getEntities())
        {
        	// TODO : Merge this with the renderGameObjects function when we will fix the collisions/position code.
            x = (entity.getHitBox().getX() * tileSize) +  ((getWidth() - (tileSize) - (tileSize * Settings.WORLD_DATA.getWidth())) / 2) + (tileSize / 4);
            y = (entity.getHitBox().getY() * tileSize) + ((getHeight() - (tileSize) - (tileSize * Settings.WORLD_DATA.getHeight())) / 2) + (tileSize / 4);
            
            g.drawImage(Settings.SPRITES.getSpritesSheet(), (int)x, (int)y, (int)(x + (2 * tileSize)), (int)(y + (2 * tileSize)), entity.getSprite().getX1(), entity.getSprite().getY1(), entity.getSprite().getX2(), entity.getSprite().getY2(), null);
            
            if (debug)
            {
                g.setColor(new Color(0, 0, 100, 200));
                g.fillRect((int)x, (int)y, 2 * tileSize, 2 * tileSize);
            }
        }
	}
	
	private void renderStatusBar(Graphics g)
    {
        int x = (getWidth() - Settings.WORLD_DATA.getWidth() * tileSize) / 2;
        int y = (getHeight() - tileSize);
        
        String s = new String("score " + game.getPacman().getScore());
        if (game.getCurrentState() != null) { s += " state " + game.getCurrentState().getName().getValue(); }
        if (game.getPacman().isCollision()) { s += " collision " + game.getPacman().getCollisionDirection(); }
        renderString(g, s, x, y);
    }
    
	private void renderPause(Graphics g)
    {
        if (game.getCurrentState().getName() == StatesName.PAUSE)
        {
	        g.setColor(new Color(0, 0, 0, 200));
	        g.fillRect(0, 0, getWidth(), getHeight());
	        
	        int x = (getWidth() - (StatesName.PAUSE.getValue().length() * tileSize)) / 2;
	        int y = getHeight() / 2;
	        
	        renderString(g, StatesName.PAUSE.getValue(), x, y);
        }
    }
	
	private void renderString(Graphics g, String message, int x, int y)
	{
		for (int i = 0; i < message.length(); i++)
        {
        	if (message.charAt(i) != ' ')
        	{
        		Sprite k = Settings.SPRITES.getCharacter(message.charAt(i));
        		g.drawImage(Settings.SPRITES.getSpritesSheet(), x + i * tileSize, y, x + i * tileSize + tileSize, y + tileSize, k.getX1(), k.getY1(), k.getX2(), k.getY2(), null);
        	}
        }
	}
}
