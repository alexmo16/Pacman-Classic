package com.pacman.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JPanel;

import com.pacman.model.Game;
import com.pacman.model.objects.GameObject;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.states.StatesName;
import com.pacman.utils.Settings;

public class GameView extends JPanel
{
	private static final long serialVersionUID = 1594565623438214915L;  
	
	private Game game;
	private int scaling;
	
	
	public GameView(Game gm)
	{
		game = gm;
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
        scaling = Math.min(getHeight() / Settings.WORLD_DATA.getHeight(), getWidth() / Settings.WORLD_DATA.getWidth());
        if ( (scaling & 1) != 0 ) { scaling--; } // Odd tile size will break tile scaling.
		
        renderBackground(g);
        renderGameObjects(g);
        renderEntities(g);
        renderStatusBar(g);
        renderPause(g);
        renderGameover(g);
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
            x = (obj.getHitBox().getX() * scaling) + ((getWidth() - (scaling * Settings.WORLD_DATA.getWidth())) / 2);
            y = (obj.getHitBox().getY() * scaling) + ((getHeight() - (scaling * Settings.WORLD_DATA.getHeight())) / 2);
            g.drawImage(Settings.SPRITES.getSpritesSheet(), (int)x, (int)y, (int)(x + scaling), (int)(y + scaling), obj.getSprite(0), obj.getSprite(1), obj.getSprite(2), obj.getSprite(3), null);  	
        }
	}
	
	private void renderEntities(Graphics g)
	{        
        double x, y;
        for (Entity entity : game.getEntities())
        {
        	// TODO : Merge this with the renderGameObjects function when we will fix the collisions/position code.
            x = (entity.getHitBox().getX() * scaling) +  ((getWidth() - (scaling) - (scaling * Settings.WORLD_DATA.getWidth())) / 2) + (scaling / 4);
            y = (entity.getHitBox().getY() * scaling) + ((getHeight() - (scaling) - (scaling * Settings.WORLD_DATA.getHeight())) / 2) + (scaling / 4);
            g.drawImage(Settings.SPRITES.getSpritesSheet(), (int)x, (int)y, (int)(x + (2 * scaling)), (int)(y + (2 * scaling)), entity.getSprite(0), entity.getSprite(1), entity.getSprite(2), entity.getSprite(3), null);
        }
	}
	
	private void renderStatusBar(Graphics g)
    {
        int x = (getWidth() - Settings.WORLD_DATA.getWidth() * scaling) / 2;
        int y = (getHeight() - scaling);
        
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
	        
	        int x = (getWidth() - (StatesName.PAUSE.getValue().length() * scaling)) / 2;
	        int y = getHeight() / 2;
	        
	        renderString(g, StatesName.PAUSE.getValue(), x, y);
        }
    }
	
	private void renderGameover(Graphics g)
    {
        if (game.getCurrentState().getName() == StatesName.STOP)
        {
        	String message = "Gameover";
	        g.setColor(new Color(0, 0, 0, 200));
	        
	        int x = (getWidth() - (message.length() * scaling)) / 2;
	        int y = getHeight() / 2;
	        
	        renderString(g, message, x, y);
        }
    }
	
	private void renderString(Graphics g, String message, int x, int y)
	{
		for (int i = 0; i < message.length(); i++)
        {
        	if (message.charAt(i) != ' ')
        	{
        		int[] k = Settings.SPRITES.getCharacterCoords(message.charAt(i));
        		g.drawImage(Settings.SPRITES.getSpritesSheet(), x + i * scaling, y, x + i * scaling + scaling, y + scaling, k[0], k[1], k[2], k[3], null);
        	}
        }
	}
}
