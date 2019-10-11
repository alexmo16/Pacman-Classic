package com.pacman.model.objects.entities.behaviours;

import java.util.Random;

import com.pacman.model.Game;
import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.world.Direction;
import com.pacman.model.world.GhostType;

public class ChaseBehaviour implements IBehaviour
{
	private Ghost ghost;
	private Game game;
	private Random random;
    private int randomInt;
    private double pacmanX;
    private double pacmanY;
    private int down,up,left,right;
	
	public ChaseBehaviour(Ghost g, Game game) 
	{
		ghost = g;
		this.game = game;
		down = 0;
		up = 0;
		left = 0;
		right = 0;
	}
	
	@Override
	public void getNewDirection() 
	{
		
		if (ghost.getSameCorridor())
		{
			if ( ghost.getType() == GhostType.BLINKY )
			{
				game.setPacmanXBlinky(game.getPacman().getHitBoxX());
				game.setPacmanYBlinky(game.getPacman().getHitBoxY());
			} else 
			{
				game.setPacmanXInky(game.getPacman().getHitBoxX());
				game.setPacmanYInky(game.getPacman().getHitBoxY());
			}
		}
		
		if ( ghost.getType() == GhostType.BLINKY )
		{
			pacmanX = game.getPacmanXBlinky();
			pacmanY = game.getPacmanYBlinky();
		} else 
		{
			pacmanX = game.getPacmanXInky();
			pacmanY = game.getPacmanYInky();
		}
		
	    if (pacmanX != 0 && pacmanY != 0)
	    {

	        if (pacmanX == ghost.getHitBoxX())
	        {
	            if (pacmanY > ghost.getHitBoxY() && down == 0)
	            {
	                ghost.setDirection(Direction.DOWN);
	                down++;
	            } else if (pacmanY <= ghost.getHitBoxY() && up == 0)
	            {
	                ghost.setDirection(Direction.UP);
	                up++;
	            } else 
	            {
	            	if ( ghost.getType() == GhostType.BLINKY )
		    		{
		    			game.setPacmanXBlinky(0);
		    			game.setPacmanYBlinky(0);
		    		} else 
		    		{
		    			game.setPacmanXInky(0);
		    			game.setPacmanYInky(0);
		    		}
	            	random = new Random();
	    	        randomInt = random.nextInt(4);
	    	        if (Direction.values()[(randomInt+2)%4] != ghost.getDirection())
	    	        {
	    	            ghost.setDirection(Direction.values()[randomInt]);
	    	        } 
	    	        else 
	    	        {
	    	            getNewDirection();
	    	        }   
	            }
	        }else if (pacmanY == ghost.getHitBoxY())
	        {
	            if (pacmanX > ghost.getHitBoxX() && right == 0)
                {
                    ghost.setDirection(Direction.RIGHT);
                    right++;
                } else if (pacmanX <= ghost.getHitBoxX() && left == 0)
                {
                    ghost.setDirection(Direction.LEFT);
                    left++;
                } else
                {
                	if ( ghost.getType() == GhostType.BLINKY )
    	    		{
    	    			game.setPacmanXBlinky(0);
    	    			game.setPacmanYBlinky(0);
    	    		} else 
    	    		{
    	    			game.setPacmanXInky(0);
    	    			game.setPacmanYInky(0);
    	    		}
                	random = new Random();
        	        randomInt = random.nextInt(4);
        	        if (Direction.values()[(randomInt+2)%4] != ghost.getDirection())
        	        {
        	            ghost.setDirection(Direction.values()[randomInt]);
        	        } 
        	        else 
        	        {
        	            getNewDirection();
        	        }   
                }
	        }
	        
	        if (pacmanX == ghost.getHitBoxX() && pacmanY == ghost.getHitBoxY())
	        {
	        	if ( ghost.getType() == GhostType.BLINKY )
	    		{
	    			game.setPacmanXBlinky(0);
	    			game.setPacmanYBlinky(0);
	    		} else 
	    		{
	    			game.setPacmanXInky(0);
	    			game.setPacmanYInky(0);
	    		}
	        }
	        
	    } else {
	        random = new Random();
	        randomInt = random.nextInt(4);
	        if (Direction.values()[(randomInt+2)%4] != ghost.getDirection())
	        {
	            ghost.setDirection(Direction.values()[randomInt]);
	        } 
	        else 
	        {
	            getNewDirection();
	        }   
	    }
		
	}

}
