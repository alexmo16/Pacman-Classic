package com.pacman.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JPanel;

import com.pacman.controller.GameController;
import com.pacman.model.IGame;
import com.pacman.model.objects.GameObject;
import com.pacman.model.objects.Sprites;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.states.StatesName;
import com.pacman.model.world.Direction;
import com.pacman.model.world.Level;
import com.pacman.model.world.Sprite;

public class GameView extends JPanel
{
	private static final long serialVersionUID = 1594565623438214915L;  
	
	private IGame game;
	private int tileSize, horizontalBorder, verticalBorder;
	private boolean debug = false;
	
	public GameView(IGame gm)
	{
		game = gm;
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		tileSize = Math.min(getHeight() / (Level.getHeight() + 4), getWidth() / Level.getWidth());
        if ( (tileSize & 1) != 0 ) { tileSize--; } // Odd tile size will break tile tileSize.

        // Offset to fit in the world 
        horizontalBorder = (getWidth() - (tileSize * Level.getWidth())) / 2;
        verticalBorder = (getHeight() - (tileSize * Level.getHeight())) / 2;
        
        renderBackground(g);
        renderGameObjects(g);
        renderEntities(g);
        renderGameStatus(g);
        renderPause(g);
        renderGameover(g);
        renderResumeTime(g);
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
            x = (obj.getHitBox().getX() * tileSize) + horizontalBorder;
            y = (obj.getHitBox().getY() * tileSize) + verticalBorder;
            g.drawImage(Sprites.getTilesSheet(), (int)x, (int)y, (int)(x + tileSize), (int)(y + tileSize), obj.getSprite().getX1(), obj.getSprite().getY1(), obj.getSprite().getX2(), obj.getSprite().getY2(), null);  
            
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
            x = (entity.getHitBox().getX() * tileSize) +  ((getWidth() - (tileSize) - (tileSize * Level.getWidth())) / 2) + (tileSize / 4);
			y = (entity.getHitBox().getY() * tileSize) + ((getHeight() - (tileSize) - (tileSize * Level.getHeight())) / 2) + (tileSize / 4);
            
            g.drawImage(Sprites.getTilesSheet(), (int)x, (int)y, (int)(x + (2 * tileSize)), (int)(y + (2 * tileSize)), entity.getSprite().getX1(), entity.getSprite().getY1(), entity.getSprite().getX2(), entity.getSprite().getY2(), null);
            
            if (debug)
            {
                g.setColor(new Color(0, 0, 100, 200));
                g.fillRect((int)x, (int)y, 2 * tileSize, 2 * tileSize);
            }
        }
	}
    
	private void renderGameStatus(Graphics g)
    {
		// Top left - Game state
        int x = tileSize + horizontalBorder;
        int y = verticalBorder / 2;
        String s = "State " + game.getCurrentState().getName().getValue();
        renderString(g, s, x, y);
		
		// Top center - Colission
        x = getWidth() / 2;
        y = verticalBorder / 2;
        s = "Collision ";
        if (game.getPacman().isCollision()) { s += game.getPacman().getCollisionDirection(); };
        renderString(g, s, x, y);
        
        // Top right - FPS
        x = getWidth() - horizontalBorder + 2 * tileSize;
        y = verticalBorder / 2;
        s = "FPS ";
        s += Integer.toString(GameController.getFps());
        renderString(g, s, x, y);
        
        // Bot left - Score
        x = tileSize + horizontalBorder;
        y = getHeight() - tileSize / 2 - (verticalBorder / 2);
        s = new String("score " + game.getPacman().getScore());
        renderString(g, s, x, y);
        
        // Bot center - Lives
        s = "Lives ";
		x = (getWidth() / 2) - (s.length() * tileSize) / 2;
        renderString(g, s, x, y);
		x = (getWidth() / 2) + (s.length() * tileSize) / 2;
        y = getHeight() - tileSize - (verticalBorder / 2);
        for (int lives = 1; lives < game.getPacman().getLives(); lives++)
        {
        	Sprite pacman = Sprites.getPacmanMovement(Direction.RIGHT, 0);
            g.drawImage(Sprites.getTilesSheet(), (int)x, (int)y, (int)(x + (2 * tileSize)), (int)(y + (2 * tileSize)), pacman.getX1(), pacman.getY1(), pacman.getX2(), pacman.getY2(), null);
            x += 2 * tileSize;
        }
        
        // Bot right - Level
        s = "Level " + game.getCurrentLevel().getName();
		x = getWidth() - horizontalBorder - (s.length() * tileSize);
        y = getHeight() - tileSize / 2 - (verticalBorder / 2);
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
	
	private void renderGameover(Graphics g)
    {
        if (game.getCurrentState().getName() == StatesName.STOP && !game.isPacmanWon())
        {
        	String message = "Gameover";
	        g.setColor(new Color(0, 0, 0, 200));
	        
	        int x = (getWidth() - (message.length() * tileSize)) / 2;
	        int y = getHeight() / 2;
	        
	        renderString(g, message, x, y);
        }
    }
	
	private void renderResumeTime(Graphics g)
	{
		if (game.getCurrentState().getName() == StatesName.RESUME)
        {
			int time = game.getResumeTime(); // seconds
			String message = Integer.toString(time);
			g.setColor(new Color(0, 0, 0, 200));
		    g.fillRect(0, 0, getWidth(), getHeight());
		    
		    int x = (getWidth() - (message.length() * tileSize)) / 2;
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
        		Sprite k = Sprites.getCharacter(message.charAt(i));
        		g.drawImage(Sprites.getTilesSheet(), x + i * tileSize, y, x + i * tileSize + tileSize, y + tileSize, k.getX1(), k.getY1(), k.getX2(), k.getY2(), null);
        	}
        }
	}
}
