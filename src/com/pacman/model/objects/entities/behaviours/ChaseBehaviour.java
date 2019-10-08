package com.pacman.model.objects.entities.behaviours;

import java.util.Random;

import com.pacman.model.Game;
import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.world.Direction;

public class ChaseBehaviour implements IBehaviour
{
	private Ghost ghost;
	private Game game;
	private Random random;
    private int randomInt;
	
	public ChaseBehaviour(Ghost g, Game game) 
	{
		ghost = g;
		this.game = game;
	}
	
	@Override
	public void getNewDirection() 
	{
	    if (ghost.getSameCorridor())
	    {
	        if (game.getPacman().getHitBoxX() == ghost.getHitBoxX())
	        {
	            if (game.getPacman().getHitBoxY() > ghost.getHitBoxY())
	            {
	                ghost.setDirection(Direction.DOWN);
	            } else
	            {
	                ghost.setDirection(Direction.UP);
	            }
	        }else if (game.getPacman().getHitBoxY() == ghost.getHitBoxY())
	        {
	            if (game.getPacman().getHitBoxX() > ghost.getHitBoxX())
                {
                    ghost.setDirection(Direction.RIGHT);
                } else
                {
                    ghost.setDirection(Direction.LEFT);
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
