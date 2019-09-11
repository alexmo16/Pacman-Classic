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
	private Pacman maybeFuturPacman, futurPacman;
    private Direction oldDirection, direction;
	
	public PlayingState( Game gm )
	{
		if ( gm == null )
		{
			return;
		}
		
		game = gm;
		
		game.getPacman().registerObserver(this);
        
        maybeFuturPacman = new Pacman(game.getPacman().getHitBox().getX(), game.getPacman().getHitBox().getY());
        futurPacman = new Pacman(game.getPacman().getHitBox().getX(), game.getPacman().getHitBox().getY());
        
        direction = game.getPacman().getDirection();
        oldDirection = direction;
	}

	@Override
	public void update() 
	{
        if (game.getPacman().getLifes() <= 0)
		{
			game.setState(game.getStopState());
		}

        maybeFuturPacman.getHitBox().setRect(game.getPacman().getHitBox().getX(), game.getPacman().getHitBox().getY(), game.getPacman().getHitBox().getWidth(), game.getPacman().getHitBox().getHeight());
        futurPacman.getHitBox().setRect(game.getPacman().getHitBox().getX(), game.getPacman().getHitBox().getY(), game.getPacman().getHitBox().getWidth(), game.getPacman().getHitBox().getHeight());
        futurPacman.updatePosition(direction);
        maybeFuturPacman.updatePosition(oldDirection);

        checkConsumablesCollision();
        
        // Strategy pattern for wall collisions.
        String checkWallCollision = Collision.collisionWall(futurPacman);
        executeWallStrategy( checkWallCollision );
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
     * Redirect to the correct strategy
     * @param collisionString
     */
    private void executeWallStrategy( String collisionString )
    {
    	if ( collisionString == "void" )
    	{
    		tunnelStrategy();
    	}
    	else if ( collisionString == "wall" )
    	{
    		oneWallStrategy();
    	}
    	else
    	{
    		noWallStrategy();
    	}
    }
    
    /**
     * Strategy if pacman hits a wall.
     */
    private void oneWallStrategy()
    {
    	String collisionString = Collision.collisionWall(maybeFuturPacman);
        if (collisionString == "void")
        {
        	game.getPacman().tunnel(oldDirection);
        	game.getPacman().setDirection(oldDirection);
        	game.getPacman().setCollision(false, oldDirection);
        }
        if (collisionString == "path")
        {
        	game.getPacman().updatePosition(oldDirection);
        	game.getPacman().setDirection(oldDirection);
        	game.getPacman().setCollision(false, oldDirection);
        } 
        else
        {
        	game.getPacman().setCollision(true, oldDirection);
        }
    }
    
    /**
     * Strategy if pacman goes through the tunnel
     */
    private void tunnelStrategy()
    {
    	game.getPacman().tunnel(direction);
    	game.getPacman().setCollision(false, oldDirection);
    }
    
    /**
     * Strategy if pacman moves forward
     */
    private void noWallStrategy()
    {
    	game.getPacman().updatePosition(direction);
    	game.getPacman().setDirection(direction);
        oldDirection = direction;
        game.getPacman().setCollision(false, oldDirection);
    }
	
    /**
     * update of the observer
     */
	@Override
	public void update(Direction d) 
	{
		oldDirection = direction;
		direction = d;
	}
}
