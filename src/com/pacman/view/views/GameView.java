package com.pacman.view.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.pacman.controller.GameController;
import com.pacman.model.IGame;
import com.pacman.model.objects.GameObject;
import com.pacman.model.objects.Sprites;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.objects.entities.Pacman;
import com.pacman.model.states.StatesName;
import com.pacman.model.threads.TimerThread;
import com.pacman.model.world.Direction;
import com.pacman.model.world.Level;
import com.pacman.model.world.Sprite;
import com.pacman.utils.IObserver;
import com.pacman.utils.IPublisher.UpdateID;
import com.pacman.view.utils.Renderer;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class GameView extends View implements IObserver
{
	private static final long serialVersionUID = 1594565623438214915L;  
	private static final ViewType name = ViewType.GAME;
	
	private IGame game;
	private int horizontalBorder, verticalBorder;
	private boolean debug = false;
	private ArrayList<Point2D.Double> ghostScorePositions = new ArrayList<Point2D.Double>();
	private ArrayList<Integer> ghostKillScores = new ArrayList<Integer>();
	private final int scoreShowTime = 2;
	
	public GameView(IGame gm)
	{
		game = gm;
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		if (Level.getWidth() == 0 || Level.getHeight() == 0 || game == null || game.getCurrentState() == null)
		{
			return;
		}
		
		super.paintComponent(g);

        // Offset to fit in the world 
        horizontalBorder = (getWidth() - (sFactor * Level.getWidth())) / 2;
        verticalBorder = (getHeight() - (sFactor * Level.getHeight())) / 2;
        
        Renderer.renderBackground(g, Color.black, getWidth(), getHeight());
        renderGameObjects(g);
        renderInMazeScores(g);
        renderGameStatus(g);
        renderDebug(g);
        renderPause(g);
        renderGameover(g);
        renderResumeTime(g);
	}
	
	private void renderInMazeScores(Graphics g) 
	{
		if (game.getCurrentState() == null || game.getCurrentState().getName() != StatesName.PLAY) return;
		if (ghostScorePositions.isEmpty() || ghostKillScores.isEmpty()) return;
         
        for (Integer score : ghostKillScores)
        {    		
        	String message = Integer.toString(score);
        	Point2D.Double position = ghostScorePositions.get(ghostKillScores.indexOf(score));
        	
        	double x = (position.getX() * sFactor) + horizontalBorder;
            double y = (position.getY() * sFactor) + verticalBorder  + (sFactor / 2);
        	
            Renderer.renderString(g, message, (int)x, (int)y, (int) (sFactor / 1.5));
        }
	}

	private void renderGameObjects(Graphics g)
	{
        double x, y;
        int xTileScale, yTileScale;
        for (GameObject obj : game.getGameObjects())
        {
        	Sprite sprite = obj.getSprite();
        	if (sprite == null) continue;
        	
        	xTileScale = sprite.getWidth() / Sprites.getTilesize();
        	yTileScale = sprite.getHeight() / Sprites.getTilesize();
        	
            x = (obj.getPosition().getX() * sFactor) + horizontalBorder;
            y = (obj.getPosition().getY() * sFactor) + verticalBorder;
            
            g.drawImage(Sprites.getTilesSheet(), 
        				(int)(x), 
        				(int)(y), 
        				(int)(x + (xTileScale * sFactor)), 
        				(int)(y + (yTileScale * sFactor)), 
        				sprite.getX1(), 
        				sprite.getY1(), 
        				sprite.getX2(), 
        				sprite.getY2(), 
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
        x = horizontalBorder + sFactor;
        y = getHeight() - sFactor / 2 - (verticalBorder / 2);
        s = new String("score " + game.getPacman().getScore());
        Renderer.renderString(g, s, x, y, sFactor);
        
        // Bot center right - Lives
        s = game.getPacman().getLives() + " x ";
		x = sFactor + getWidth() / 2;
		Renderer.renderString(g, s, x, y, sFactor);
		
		x += s.length() * sFactor;
        y = getHeight() - sFactor - (verticalBorder / 2);
    	Sprite pacman = Sprites.getPacmanMovement(Direction.RIGHT, 0);
        g.drawImage(Sprites.getTilesSheet(), (int)x, (int)y, (int)(x + (2 * sFactor)), (int)(y + (2 * sFactor)), pacman.getX1(), pacman.getY1(), pacman.getX2(), pacman.getY2(), null);
        
        // Bot right - Level
        s = "Lvl " + game.getCurrentLevel().getName();
		x += 3 * sFactor;
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
        	String message = "Game over";
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
	
	/**
	 * Observer to show the score associated to the death of a ghost.
	 */
	@Override
	public void updateObservers(UpdateID updateID)
	{
		if (updateID != UpdateID.KILL_GHOST) return;
		
		Pacman pacman = game.getPacman();
		if (pacman == null) return;
		
		if (game.getCurrentState() != null && game.getCurrentState().getName() == StatesName.PLAY)
		{
			Point2D.Double position = pacman.getKillPosition(); 
			ghostScorePositions.add(position);
			Integer score = pacman.getKillScore();
			ghostKillScores.add(score);
			
			TimerThread killScoreTimer = new TimerThread(scoreShowTime);
			killScoreTimer.setEndCallback(() -> 
	    	{
	    		synchronized (this) 
	    		{
	    			ghostScorePositions.remove(ghostScorePositions.indexOf(position));
		    		ghostKillScores.remove(ghostKillScores.indexOf(score));
				}
	    	});
			killScoreTimer.start();
		}
	}
}
