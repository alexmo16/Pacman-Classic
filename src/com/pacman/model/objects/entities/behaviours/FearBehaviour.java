package com.pacman.model.objects.entities.behaviours;

import java.util.Random;

import com.pacman.model.Game;
import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.world.Direction;

public class FearBehaviour implements IBehaviour
{
	private Ghost ghost;
	private Game game;
	private Random random;
    private int randomInt;
	private int down, up, left, right;
	
	public FearBehaviour(Ghost g, Game game) 
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
			 if (game.getPacman().getHitBoxX() == ghost.getHitBoxX())
		        {
		            if (game.getPacman().getHitBoxY() > ghost.getHitBoxY() && up == 0)
		            {
		                ghost.setDirection(Direction.UP);
		                up++;
		            } else if (game.getPacman().getHitBoxY() <= ghost.getHitBoxY() &&down == 0)
		            {
		                ghost.setDirection(Direction.DOWN);
		                down ++;
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
		        } else if (game.getPacman().getHitBoxY() == ghost.getHitBoxY())
		        {
		            if (game.getPacman().getHitBoxX() > ghost.getHitBoxX() && left == 0)
		            {
		                ghost.setDirection(Direction.LEFT);
		                left++;
		            } else if (game.getPacman().getHitBoxX() <= ghost.getHitBoxX() && right == 0)
		            {
		                ghost.setDirection(Direction.RIGHT);
		                right++;
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
		 } else 
		 {
			 random = new Random();
		     randomInt = random.nextInt(4);
		     if (Direction.values()[(randomInt+2)%4] != ghost.getDirection())
		     {
		    	 ghost.setDirection(Direction.values()[randomInt]);
		     } else 
		     {
		    	 getNewDirection();
		     }   
		 }
       
	}

}
