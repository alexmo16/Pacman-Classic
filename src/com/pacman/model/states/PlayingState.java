package com.pacman.model.states;

import com.pacman.model.Collision;
import com.pacman.model.Game;
import com.pacman.model.objects.consumables.Consumable;
import com.pacman.model.objects.entities.Pacman;
import com.pacman.model.world.Direction;
import com.pacman.utils.IObserver;

public class PlayingState implements IGameState, IObserver<Direction>
{
	private StatesName name = StatesName.PLAY;
	
	private Game game;
	/*
	 * nextTilesPacman is pacman if he move on the same direction as before
	 * newDirectionPacman is pacman if he is at an intersection and change is direction
	 *  
	 */
	private Pacman newDirectionPacman, nextTilesPacman;
    private Direction newDirection, nextTilesDirection;
	
	public PlayingState( Game gm )
	{
		if ( gm == null )
		{
			return;
		}
		
		game = gm;
		
		game.getPacman().registerObserver(this);
        
        newDirectionPacman = new Pacman(game.getPacman().getHitBox().getX(), game.getPacman().getHitBox().getY());
        nextTilesPacman = new Pacman(game.getPacman().getHitBox().getX(), game.getPacman().getHitBox().getY());
        
        nextTilesDirection = game.getPacman().getDirection();
        newDirection = nextTilesDirection;
	}

	@Override
	public void update() 
	{
        newDirectionPacman.getHitBox().setRect(game.getPacman().getHitBox().getX(), game.getPacman().getHitBox().getY(), game.getPacman().getHitBox().getWidth(), game.getPacman().getHitBox().getHeight());
        nextTilesPacman.getHitBox().setRect(game.getPacman().getHitBox().getX(), game.getPacman().getHitBox().getY(), game.getPacman().getHitBox().getWidth(), game.getPacman().getHitBox().getHeight());
        nextTilesPacman.updatePosition(nextTilesDirection);
        newDirectionPacman.updatePosition(newDirection);

        checkConsumablesCollision();
        
        // Strategy pattern for wall collisions.
        
        Collision.executeWallStrategy(game);
	}

	@Override
	public StatesName getName()
	{
		return name;
	}
    
    /**
     * Check if pacman eats a Gum
     */
    private void checkConsumablesCollision()
    {
        for (Consumable consumable : game.getConsumables())
        {
            if (Collision.collisionObj(game.getPacman(), consumable))
            {
            	game.getPacman().eat(consumable);
            	game.getConsumables().remove(consumable);
                break;
            }
        }
    }
    
  
	
    /**
     * update of the observer
     */
	@Override
	public void update(Direction d) 
	{
		if ( nextTilesDirection == game.getPacman().getDirection() ) 
		{
			newDirection = nextTilesDirection;
		}
		
		nextTilesDirection = d;
	}
	
	private void killPacman()
	{
		game.stopInGameMusics();
		game.getPacman().looseLife();
		game.getPacman().respawn();
		game.setState(game.getPacman().getLifes() == 0 ? game.getStopState() : game.getInitState());
	}
}
