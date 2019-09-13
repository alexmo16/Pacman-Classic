package com.pacman.model.states;

import com.pacman.model.Collision;
import com.pacman.model.Game;
import com.pacman.model.world.Direction;
import com.pacman.utils.IObserver;

public class PlayingState implements IGameState, IObserver<Direction>
{
	private StatesName name = StatesName.PLAY;
	
	private Game game;
	private Collision collision;
	/*
	 * nextTilesPacman is pacman if he move on the same direction as before
	 * newDirectionPacman is pacman if he is at an intersection and change is direction
	 *  
	 */
	//private Pacman newDirectionPacman, nextTilesPacman;
    private Direction newDirection, nextTilesDirection;
	
	public PlayingState( Game gm )
	{
		if ( gm == null )
		{
			return;
		}
		
		game = gm;
		
		game.getPacman().registerObserver(this);
        
		game.getNewDirectionPacman().getHitBox().setRect(game.getPacman().getHitBox());
		game.getNextTilesPacman().getHitBox().setRect(game.getPacman().getHitBox());
        //newDirectionPacman = new Pacman(game.getPacman().getHitBox().getX(), game.getPacman().getHitBox().getY());
        //nextTilesPacman = new Pacman(game.getPacman().getHitBox().getX(), game.getPacman().getHitBox().getY());
        
        nextTilesDirection = game.getPacman().getDirection();
        newDirection = nextTilesDirection;
	}

	@Override
	public void update() 
	{
		game.getNewDirectionPacman().getHitBox().setRect(game.getPacman().getHitBox());
		game.getNextTilesPacman().getHitBox().setRect(game.getPacman().getHitBox());
        //newDirectionPacman.getHitBox().setRect(game.getPacman().getHitBox().getX(), game.getPacman().getHitBox().getY(), game.getPacman().getHitBox().getWidth(), game.getPacman().getHitBox().getHeight());
        //nextTilesPacman.getHitBox().setRect(game.getPacman().getHitBox().getX(), game.getPacman().getHitBox().getY(), game.getPacman().getHitBox().getWidth(), game.getPacman().getHitBox().getHeight());
        game.getNextTilesPacman().updatePosition(nextTilesDirection);
        game.getNewDirectionPacman().updatePosition(newDirection);

        
        
        // Strategy pattern for wall collisions.
        collision = new Collision();
        collision.checkConsumablesCollision(game);
        collision.executeWallStrategy(game);
	}

	@Override
	public StatesName getName()
	{
		return name;
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
