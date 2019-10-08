package com.pacman.model.objects.entities.behaviours;

import java.util.Random;

import com.pacman.model.Game;
import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.world.Direction;

public class AmbushBehaviour implements IBehaviour 
{
	private Random random;
	private int randomInt;
	private Ghost ghost;
	private Game game;
	private int down, up, left, right;

	public AmbushBehaviour(Ghost g, Game game) 
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
			
			if ( Math.abs(game.getPacman().getHitBoxY() - ghost.getHitBoxY()) >= Math.abs(game.getPacman().getHitBoxX() - ghost.getHitBoxX()) )				
			{
				
				if ( game.getPacman().getHitBoxY() - ghost.getHitBoxY() >= 0 && down == 0 && ghost.getDirection() != Direction.UP )
				{
					ghost.setDirection(Direction.DOWN);
					down++;
				} else if ( game.getPacman().getHitBoxY() - ghost.getHitBoxY() <= 0 && up == 0 && ghost.getDirection() != Direction.DOWN )
				{
					ghost.setDirection(Direction.UP);
					up++;
				}
				else {
					if ( game.getPacman().getHitBoxX() - ghost.getHitBoxX() >= 0 && right == 0 && ghost.getDirection() != Direction.LEFT )
					{
						ghost.setDirection(Direction.RIGHT);
						right++;
					} else if ( game.getPacman().getHitBoxX() - ghost.getHitBoxX() <= 0 && left == 0 && ghost.getDirection() != Direction.RIGHT )
					{
						ghost.setDirection(Direction.LEFT);
						left++;
					} else
					{
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
				
			} else if ( Math.abs(game.getPacman().getHitBoxY() - ghost.getHitBoxY()) < Math.abs(game.getPacman().getHitBoxX() - ghost.getHitBoxX()) )				
			{
				
				if ( game.getPacman().getHitBoxX() - ghost.getHitBoxX() >= 0 && right == 0 && ghost.getDirection() != Direction.LEFT )
				{
					ghost.setDirection(Direction.RIGHT);
					right++;
				} else if ( game.getPacman().getHitBoxX() - ghost.getHitBoxX() <= 0 && left == 0 && ghost.getDirection() != Direction.RIGHT )
				{
					ghost.setDirection(Direction.LEFT);
					left++;
				}
				else {
					if ( game.getPacman().getHitBoxY() - ghost.getHitBoxY() >= 0 && down == 0 && ghost.getDirection() != Direction.UP )
					{
						ghost.setDirection(Direction.DOWN);
						down++;
					} else if ( game.getPacman().getHitBoxY() - ghost.getHitBoxY() <= 0 && up == 0 && ghost.getDirection() != Direction.DOWN )
					{
						ghost.setDirection(Direction.UP);
						up++;
					} else
					{
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

		
	}

}
