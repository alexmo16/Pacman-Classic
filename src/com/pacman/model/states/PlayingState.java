package com.pacman.model.states;

import java.util.ArrayList;

import com.pacman.model.Collision;
import com.pacman.model.Game;
import com.pacman.model.objects.consumables.PacDot;
import com.pacman.model.world.Direction;
import com.pacman.model.world.Level;
import com.pacman.utils.IObserver;
import com.pacman.utils.Settings;

public class PlayingState implements IGameState, IObserver<Direction>
{
	private StatesName name = StatesName.PLAY;
	
	private Game game;
	private Collision collision;
	private int i;

	
	public PlayingState( Game gm )
	{
		if ( gm == null )
		{
			return;
		}
		
		game = gm;
		
		collision = game.getCollision();
		
		game.getPacman().registerObserver(this);
        
		game.getNewDirectionPacman().getHitBox().setRect(game.getPacman().getHitBox());
		game.getNextTilesPacman().getHitBox().setRect(game.getPacman().getHitBox());
        
        game.setNextTilesDirection(game.getPacman().getDirection());
        game.setNewDirection(game.getNextTilesDirection());
	}

	@Override
	public void update() 
	{
		
		for (i = 0; i < Settings.SPEED; i++ )
		{
			Level level = game.getCurrentLevel();
			ArrayList<PacDot> pacdots = level.getPacDots();
			if (pacdots.size() == 0)
			{
				game.setPacmanWon(true);
				game.setState(game.getStopState());
			}
			
			game.getNewDirectionPacman().getHitBox().setRect(game.getPacman().getHitBox());
			game.getNextTilesPacman().getHitBox().setRect(game.getPacman().getHitBox());
	        game.getNextTilesPacman().updatePosition(game.getNextTilesDirection());
	        game.getNewDirectionPacman().updatePosition(game.getNewDirection());

	        
	        // Strategy pattern for wall collisions.

	        collision.checkConsumablesCollision();
	        collision.executeWallStrategy();
		}
		
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
		if ( game.getNewDirection() == game.getPacman().getDirection() ) {
			
			game.setNextTilesDirection(game.getNewDirection());
		}
		game.setNewDirection(d);
	}
	
	public void killPacman()
	{
		game.stopInGameMusics();
		game.getPacman().looseLive();
		game.getPacman().respawn();
		game.setState(game.getPacman().getLives() == 0 ? game.getStopState() : game.getInitState());
	}
}
