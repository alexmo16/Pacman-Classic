package com.pacman.view.views;

import java.awt.Color;
import java.awt.Graphics;

import com.pacman.controller.GameController;
import com.pacman.model.IGame;
import com.pacman.model.objects.GameObject;
import com.pacman.model.objects.Sprites;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.states.StatesName;
import com.pacman.model.world.Direction;
import com.pacman.model.world.Level;
import com.pacman.model.world.Sprite;
import com.pacman.view.utils.Renderer;

public class GameView extends View
{
	private static final long serialVersionUID = 1594565623438214915L;  
	private static final ViewType name = ViewType.GAME;
	
	private IGame game;
	private int horizontalBorder, verticalBorder;
	private boolean debug = false;
	
	public GameView(IGame gm)
	{
		game = gm;
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		if (Level.getWidth() == 0 || Level.getHeight() == 0)
		{
			return;
		}
		
		super.paintComponent(g);

        // Offset to fit in the world 
        horizontalBorder = (getWidth() - (sFactor * Level.getWidth())) / 2;
        verticalBorder = (getHeight() - (sFactor * Level.getHeight())) / 2;
        
        Renderer.renderBackground(g, Color.black, getWidth(), getHeight());
        renderGameObjects(g);
        renderGameStatus(g);
        renderDebug(g);
        renderPause(g);
        renderGameover(g);
        renderResumeTime(g);
	}
	
	private void renderGameObjects(Graphics g)
	{
        double x, y;
        int xTileScale, yTileScale;
        for (GameObject obj : game.getGameObjects())
        {
        	xTileScale = obj.getSprite().getWidth() / Sprites.getTilesize();
        	yTileScale = obj.getSprite().getHeight() / Sprites.getTilesize();
        	
            x = (obj.getPosition().getX() * sFactor) + horizontalBorder;
            y = (obj.getPosition().getY() * sFactor) + verticalBorder;
            
            g.drawImage(Sprites.getTilesSheet(), 
        				(int)(x), 
        				(int)(y), 
        				(int)(x + (xTileScale * sFactor)), 
        				(int)(y + (yTileScale * sFactor)), 
        				obj.getSprite().getX1(), 
        				obj.getSprite().getY1(), 
        				obj.getSprite().getX2(), 
        				obj.getSprite().getY2(), 
        				null);  
        }
	}
    
	private void renderDebug(Graphics g)
	{
		if (debug)
		{
	        double x, y;
	        int xTileScale, yTileScale;
	        for (GameObject obj : game.getGameObjects())
	        {	        	
	            // Grid of hitbox (red)
	            x = (obj.getHitBox().getX() * sFactor) + horizontalBorder;
	            y = (obj.getHitBox().getY() * sFactor) + verticalBorder;
	        	
	            g.setColor(new Color(100, 0, 0, 200));
	            g.drawRect((int)x, (int)y, (int)(obj.getHitBox().getWidth() * sFactor) - 1, (int)(obj.getHitBox().getHeight() * sFactor) - 1);
	            
	            // Sprite drawing zone (green)
	            if (obj instanceof Entity)
	            {
		        	xTileScale = obj.getSprite().getWidth() / Sprites.getTilesize();
		        	yTileScale = obj.getSprite().getHeight() / Sprites.getTilesize();
		        	
		            x = (obj.getPosition().getX() * sFactor) + horizontalBorder;
		            y = (obj.getPosition().getY() * sFactor) + verticalBorder;
		            
		            g.setColor(new Color(0,100,0, 200));
		            g.fillRect((int)x, (int)y, (int)(xTileScale * sFactor), (int)(yTileScale * sFactor));
	            }
	        
	        }
		}
	}
	
	private void renderGameStatus(Graphics g)
    {
		if (game == null || game.getCurrentState() == null)
		{
			return;
		}
			
		// Top left - Game state
        int x = sFactor + horizontalBorder;
        int y = verticalBorder / 2;
        String s = "State " + game.getCurrentState().getName().getValue();
        Renderer.renderString(g, s, x, y, sFactor);
		
		// Top center - Colission
        x = getWidth() / 2;
        y = verticalBorder / 2;
        s = "Collision ";
        if (game.getPacman().isCollision()) { s += game.getPacman().getCollisionDirection(); };
        Renderer.renderString(g, s, x, y, sFactor);
        
        // Top right - FPS
        x = getWidth() - horizontalBorder + 2 * sFactor;
        y = verticalBorder / 2;
        s = "FPS ";
        s += Integer.toString(GameController.getFps());
        Renderer.renderString(g, s, x, y, sFactor);
        
        // Bot left - Score
        x = sFactor + horizontalBorder;
        y = getHeight() - sFactor / 2 - (verticalBorder / 2);
        s = new String("score " + game.getPacman().getScore());
        Renderer.renderString(g, s, x, y, sFactor);
        
        // Bot center - Lives
        s = "Lives ";
		x = (getWidth() / 2) - (s.length() * sFactor) / 2;
		Renderer.renderString(g, s, x, y, sFactor);
		x = (getWidth() / 2) + (s.length() * sFactor) / 2;
        y = getHeight() - sFactor - (verticalBorder / 2);
        for (int lives = 1; lives < game.getPacman().getLives(); lives++)
        {
        	Sprite pacman = Sprites.getPacmanMovement(Direction.RIGHT, 0);
            g.drawImage(Sprites.getTilesSheet(), (int)x, (int)y, (int)(x + (2 * sFactor)), (int)(y + (2 * sFactor)), pacman.getX1(), pacman.getY1(), pacman.getX2(), pacman.getY2(), null);
            x += 2 * sFactor;
        }
        
        // Bot right - Level
        s = "Level " + game.getCurrentLevel().getName();
		x = getWidth() - horizontalBorder - (s.length() * sFactor);
        y = getHeight() - sFactor / 2 - (verticalBorder / 2);
        Renderer.renderString(g, s, x, y, sFactor);
    }
	
	private void renderPause(Graphics g)
    {
        if (game.getCurrentState().getName() == StatesName.PAUSE)
        {
	        g.setColor(new Color(0, 0, 0, 200));
	        g.fillRect(0, 0, getWidth(), getHeight());
	        
	        int x = (getWidth() - (StatesName.PAUSE.getValue().length() * sFactor)) / 2;
	        int y = getHeight() / 2;
	        
	        Renderer.renderString(g, StatesName.PAUSE.getValue(), x, y, sFactor);
        }
    }
	
	private void renderGameover(Graphics g)
    {
        if (game.getCurrentState().getName() == StatesName.STOP && !game.isPacmanWon())
        {
        	String message = "Gameover";
	        g.setColor(new Color(0, 0, 0, 200));
	        
	        int x = (getWidth() - (message.length() * sFactor)) / 2;
	        int y = getHeight() / 2;
	        
	        Renderer.renderString(g, message, x, y, sFactor);
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
		    
		    int x = (getWidth() - (message.length() * sFactor)) / 2;
		    int y = getHeight() / 2;
		    
		    Renderer.renderString(g, message, x, y, sFactor);
        }
	}
	
	@Override
	public String getName()
	{
		return name.getValue();
	}
}
