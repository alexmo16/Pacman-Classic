package com.pacman.model.objects.entities;

import java.util.ArrayList;

import org.mockito.internal.invocation.RealMethod.IsIllegal;

import com.pacman.model.Sound;
import com.pacman.model.objects.Animation;
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
public class Pacman extends Entity implements IPublisher, Animation
{
	public enum Animation
	{
		IDLE(0),
		MOVING(3),
		DYING(12);
		
		private final int value;

		Animation(final int newValue)
	    {
	        value = newValue;
	    }

	    public int getValue() { return value; }
	};
	
	private Animation currentAnimation = Animation.IDLE;
	private int animationState = 0;
	private boolean endOfAnimation = false;
    private Sound chomp;
    private int score = 0;
	public final int MAX_LIFES = 3;
    private int lifes;
    private boolean collision = false;
    private Direction collisionDirection = null; 
    private Direction nextDirection = Direction.LEFT;
    public final Direction firstDirection = Direction.LEFT;
    private double spawnX;
    private double spawnY;
    private ArrayList<IObserver<Direction>> observers = new ArrayList<IObserver<Direction>>();
    private boolean isTravelling = false;
    
    private int[] authTiles;
    
   
    public Pacman(double x, double y)
    {
        super(x+0.05, y+0.05, 0.9, 0.9, Direction.LEFT);
        spawnX = x;
        spawnY = y;
		lifes = MAX_LIFES;
    	updateSprite();
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
    	switch(currentAnimation)
    	{
		case DYING:
			sprite = Sprites.getPacmanDeath(animationState);
			break;
		case IDLE:
			sprite = Sprites.getPacmanMovement(direction, 0);
			break;
		case MOVING:
			sprite = Sprites.getPacmanMovement(direction, animationState);
			break;
		default:
			break;
    	}
    }
    
	@Override
	public void nextSprite() 
	{
		endOfAnimation = (animationState + 1 == currentAnimation.getValue()) ? true : endOfAnimation;
		endOfAnimation = (animationState == 0) ? false : endOfAnimation;
		if (currentAnimation != Animation.DYING)
		{
			animationState += (endOfAnimation ? -1 : 1);
		}
		else 
		{
			animationState = animationState != (currentAnimation.getValue() - 1) ? animationState + 1 : animationState;
		}
		updateSprite();
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

	public void setCollision(boolean isInCollision, Direction oldDirection) 
	{
		Animation lastAnimation = currentAnimation;
		currentAnimation = isInCollision ? Animation.IDLE : Animation.MOVING;
		animationState = lastAnimation != currentAnimation ? 0 : animationState;
		endOfAnimation = lastAnimation != currentAnimation ? false : endOfAnimation;
		
		collision = isInCollision;
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

	public int getLives()
	{
		return lifes;
	}

	public void looseLive()
	{
		lifes--;
		currentAnimation = Animation.DYING;
		animationState = 0;
		endOfAnimation = false;
	}
	
	public void respawn()
	{
		currentAnimation = Animation.IDLE;
		animationState = 0;
		endOfAnimation = false;
		
		this.setIsTravelling(false);
		setNextDirection(firstDirection);
		setDirection(firstDirection);
		hitBox.x = spawnX;
		hitBox.y = spawnY;
	}
	
	public void resetLives()
	{
		lifes = MAX_LIFES;
	}
	
	public void resetScore()
	{
		score = 0;
	}
	
	public boolean isEndOfAnimation()
    {
        return endOfAnimation;
    }
    
    public final Animation getCurrentAnimation()
    {
        return currentAnimation;
    }
	
	public boolean getIstravelling()
	{
		return this.isTravelling;
	}
	
	public void setIsTravelling(boolean bool)
	{
		this.isTravelling = bool;
	}
	
	public void setAuthTiles(int[] tab)
	{
		this.authTiles = tab;
	}
	
	public int[] getAuthTiles()
	{
		return this.authTiles;
	}
}
