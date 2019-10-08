package com.pacman.model.objects.entities;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import com.pacman.model.objects.Animation;
import com.pacman.model.objects.Sprites;
import com.pacman.model.objects.consumables.Consumable;
import com.pacman.model.world.Direction;
import com.pacman.utils.IObserver;
import com.pacman.utils.IPublisher;
import com.pacman.utils.Settings;

/**
 * 
 * Class which implement every method for a Pacman object, such as direction or render
 * Observer design pattern
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
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
    private long timeSinceLastSpriteUpdate = 0;
	private int animationState = 0;
	private boolean endOfAnimation = false;
    private int score = 0;
	public  final int LIFES = 3;
    private int lifes;
    private boolean canGetBonusLife = true;
    private boolean collision = false;
    private Direction collisionDirection = null; 
    private Direction nextDirection = Direction.LEFT;
    private ArrayList<IObserver> observers = new ArrayList<IObserver>();
    private boolean isTravelling = false;   
    private boolean isInvincible = false;
    
    private int[] authTiles;
    
    private int eatenGhosts = 0;
    private Point2D.Double killPosition = new Double();
    private Integer killScore = 0;
    
   
    public Pacman(double x, double y)
    {
        super(x, y, Direction.LEFT);
		lifes = LIFES;
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
    		notifyObservers(UpdateID.CHANGE_DIRECTION);
    	}
    }
    
    public Direction getNextDirection()
    {
    	return nextDirection;
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
        score += consumable.getPoints();
        
        if (score >= 10000 && canGetBonusLife)
        {
        	canGetBonusLife = false;
        	lifes += 1;
        }
    }
    
    public void eat(Ghost ghost)
    {
    	synchronized (killScore) 
    	{
    		killScore = (int) (ghost.getPoints() * Math.pow(2, eatenGhosts));
		}
        score += killScore;
        eatenGhosts++;
        
        if (score >= 10000 && canGetBonusLife)
        {
        	canGetBonusLife = false;
        	lifes += 1;
        }
        synchronized (killPosition) 
        {
        	// We need to make a copy, because the score position will change with movement if it is a reference.
        	killPosition = (Double) ghost.getPosition().clone();
		}
        notifyObservers(UpdateID.KILL_GHOST);
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
	public synchronized void nextSprite() 
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
	public void registerObserver(IObserver observer) 
	{
		observers.add(observer);
	}

	@Override
	public void removeObserver(IObserver observer) 
	{
		int index = observers.indexOf(observer);
		if (index >= 0)
		{
			observers.remove(index);
		}
	}

	@Override
	public void notifyObservers(UpdateID updateID) 
	{
		for (IObserver observer : observers)
		{
			if (observer != null)
			{
				observer.updateObservers(updateID);
			}
		}
	}

	public synchronized void setCollision(boolean isInCollision, Direction oldDirection) 
	{
		if (currentAnimation == Animation.DYING) return;
		
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

	public synchronized void looseLive()
	{
		lifes--;
		currentAnimation = Animation.DYING;
		animationState = 0;
		endOfAnimation = false;
	}
	
	public synchronized void respawn()
	{
		currentAnimation = Animation.IDLE;
		animationState = 0;
		endOfAnimation = false;
		
		this.setIsTravelling(false);
		setNextDirection(spawnDirection);
		setDirection(spawnDirection);
		setPosition(spawn.x, spawn.y);
	}
	
	public void resetLives()
	{
		lifes = LIFES;
		canGetBonusLife = true;
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
	
	@Override
	public double getSpeed() {
		if (!isInvincible()) {
			return Settings.SPEED;
		} else {
			return Settings.INVINCIBLE_SPEED;
		}
	}
	
	public void setAuthTiles(int[] tab)
	{
		this.authTiles = tab;
	}
	
	public int[] getAuthTiles()
	{
		return this.authTiles;
	}
	
	@Override
	public int spritePerSecond()
	{
		return 8;
	}
	
	@Override
	public long getTimeSinceLastSpriteUpdate() 
	{
		return timeSinceLastSpriteUpdate;
	}
	
	@Override
	public void setTimeSinceLastSpriteUpdate(long time)
	{
		timeSinceLastSpriteUpdate = time;
	}

	public synchronized boolean isInvincible()
	{
		return isInvincible;
	}


	public synchronized void setInvincibility(boolean b)
	{
		isInvincible = b;
	}
	
	public void resetEatenGhosts()
	{
		eatenGhosts = 0;
	}
	
	public final Point2D.Double getKillPosition()
	{
		synchronized (killPosition) 
		{
			return killPosition;
		}
	}

	public Integer getKillScore() 
	{
		synchronized (killScore) 
		{
			return killScore;
		}
	}


	public final int getEatenGhosts() 
	{
		return eatenGhosts;
	}
}
