package com.pacman.model.objects.entities;

import java.util.ArrayList;

import com.pacman.model.Sound;
import com.pacman.model.objects.Sprites;
import com.pacman.model.objects.consumables.Consumable;
import com.pacman.model.world.Direction;
import com.pacman.utils.IObserver;
import com.pacman.utils.IPublisher;

/**
 * 
 * Class which implement every method for a Pacman object, such as direction or render
 * Observer design pattern
 *
 */
public class Pacman extends Entity implements IPublisher
{
    private Sound chomp;
    private int score = 0;
	private final int MAX_LIFES = 3;
    private int lifes;
    private boolean collision = false;
    private Direction collisionDirection = null; 
    private Direction nextDirection = Direction.LEFT;
    private Direction firstDirection = Direction.LEFT;
    private double spawnX;
    private double spawnY;
    private ArrayList<IObserver<Direction>> observers = new ArrayList<IObserver<Direction>>();
    
    public Pacman(double x, double y)
    {
        super(x, y, 0.9, 0.9, Direction.LEFT);
        spawnX = x;
        spawnY = y;
		lifes = MAX_LIFES;
    	sprite = Sprites.getPacmanMovement(Direction.LEFT, 0);
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
    	sprite = Sprites.getPacmanMovement(direction, 0);
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

	public int getLifes()
	{
		return lifes;
	}

	public void looseLife()
	{
		lifes--;
	}
	
	public void respawn()
	{
		setNextDirection(firstDirection);
		setDirection(firstDirection);
		hitBox.x = spawnX;
		hitBox.y = spawnY;
	}
	
	public void resetLifes()
	{
		lifes = MAX_LIFES;
	}
	
	public void resetScore()
	{
		score = 0;
	}
}
