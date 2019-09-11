package com.pacman.model.objects.entities;

import java.util.ArrayList;

import com.pacman.model.Sound;
import com.pacman.model.objects.consumables.Consumable;
import com.pacman.model.world.Direction;
import com.pacman.utils.IObserver;
import com.pacman.utils.IPublisher;
import com.pacman.utils.Settings;

/**
 * 
 * Class which implement every method for a Pacman object, such as direction or render
 * Observer design pattern
 *
 */
public class Pacman extends Entity implements IPublisher
{
    private Sound chomp;
    private int score;
    private boolean collision;
    private Direction collisionDirection; 
    private Direction nextDirection = Direction.LEFT;
    private ArrayList<IObserver<Direction>> observers = new ArrayList<IObserver<Direction>>();

    public Pacman(double x, double y)
    {
        super(x, y, 0.9, 0.9, Direction.LEFT);
    	score = 0;
    	collision = false;
    	collisionDirection = null;
    	sprite = Settings.SPRITES.getPacmanCoords(direction);
    }

    public void setNextDirection(Direction dir)
    {
    	if (dir == null)
    	{
    		return;
    	}
    	
    	Direction oldDirection = nextDirection;
    	nextDirection = dir;
    	
    	if (oldDirection != nextDirection)
    	{
    		notifyObservers();
    	}
    }
    
    /**
     * method used to make every move we need when Pacman eat a gum.
     *chomp sound will be played, score will be updated and the gum state will be put to "eaten"
     *
     * @return void
     * 
     */
    public void eat(Consumable consumable)
    {
    	if ( chomp != null )
    	{
    		chomp.play();
    	}
    	
        score += consumable.getPoints();
    }
    
    public void setChompSound( Sound sound )
    {
    	chomp = sound;
    }
    
    @Override
    public void updateSprite()
    {
    	sprite = Settings.SPRITES.getPacmanCoords(direction);
    }
    
	@Override
	public void registerObserver(IObserver<?> observer) 
	{
		@SuppressWarnings("unchecked")
		IObserver<Direction> tempObserver = (IObserver<Direction>)observer;
		if ( tempObserver != null )
		{
			observers.add(tempObserver);
		}
	}

	@Override
	public void removeObserver(IObserver<?> observer) 
	{
		int index = observers.indexOf(observer);
		if (index >= 0)
		{
			observers.remove(index);
		}
	}

	@Override
	public void notifyObservers() 
	{
		for ( IObserver<Direction> observer : observers )
		{
			if (observer != null)
			{
				observer.update( nextDirection );
			}
		}
	}

	public void setCollision(boolean b, Direction oldDirection) 
	{
		collision = b;
		collisionDirection = oldDirection;
	}

	public int getScore() 
	{
		return score;
	}

	public boolean isCollision() 
	{
		return collision;
	}

	public Direction getCollisionDirection() 
	{
		return collisionDirection;
	}
}
