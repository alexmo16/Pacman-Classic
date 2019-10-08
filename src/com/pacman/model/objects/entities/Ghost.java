package com.pacman.model.objects.entities;

import java.util.Random;

import com.pacman.model.objects.Animation;
import com.pacman.model.objects.Sprites;
import com.pacman.model.objects.entities.behaviours.IBehaviour;
import com.pacman.model.objects.entities.behaviours.IBehaviour.behavioursID;
import com.pacman.model.world.Direction;
import com.pacman.model.world.GhostType;
import com.pacman.utils.Settings;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class Ghost extends Entity implements Animation
{
	public enum Animation
	{
		MOVING(2),
		FRIGHTENED(2),
		BLINKING(4);
		
		private final int value;

		Animation(final int newValue)
	    {
	        value = newValue;
	    }

	    public int getValue() { return value; }
	};
	
	private Animation currentAnimation = Animation.MOVING;
    private int animationState = 0;
	private long timeSinceLastSpriteUpdate = 0;
	private GhostType type;
	private boolean isAlive;
	private volatile boolean isSpawning;
	private boolean isInTheGate;
	
	private int[] authTiles;
	private int[] authTilesRoom;
	
	private final int ghostPoint = 200;
	
	private IBehaviour behaviour;
	
	private boolean sameCorridor = false;
	
	private Random random;
	
	private int locker = 0;
	
    public Ghost(double x, double y, GhostType t)
    {
        super(x, y, Direction.UP);
        type = t;
        isAlive = false;
        isSpawning = false;
        isInTheGate = false;
        updateSprite();
    }
    
    @Override
    public void updateSprite()
    {
    	switch (currentAnimation)
    	{
    	case MOVING:
    		sprite = Sprites.getGhost(type, direction, animationState);
    		break;
    	case FRIGHTENED:
    		sprite = Sprites.getFrightenedGhost(animationState + 2);
    		break;
    	case BLINKING:
    		sprite = Sprites.getFrightenedGhost(animationState);
    		break;
    	default:
    		break;
    	}
    }
	
	@Override
	public int[] getAuthTiles()
	{
		if (this.isAlive)
		{
			return this.authTiles;
		}
		return this.authTilesRoom;
	}
	
	@Override
	public synchronized void nextSprite() 
	{
		animationState = (animationState + 1) % currentAnimation.getValue();
		updateSprite();
	}
	
	public boolean getAlive()
	{
		return isAlive;
	}
	
	public void setAlive()
	{
		isAlive = true;
	}
	
	public synchronized boolean getSpawning() 
	{
		return isSpawning;
	}
	
	public synchronized void setSpawning() 
	{
		isSpawning = true;
	}
	
	public boolean getInTheGate() 
	{
		return isInTheGate;
	}
	
	public void setInTheGate() 
	{
		isInTheGate = true;
	}
	
	public void setNotInTheGate() 
	{
		isInTheGate = false;
	}
	
	public synchronized void setNotSpawning() 
	{
		isSpawning = false;
	}
	
	public void setNotAlive()
	{
		isAlive = false;
	}
	
	public GhostType getType()
	{
		return type;
	}
	
	public void getNewDirection() 
	{
		if (behaviour != null)
		{
			behaviour.getNewDirection();
		}
	}
	
	public void respawn() 
	{
		setNotAlive();
		setNotSpawning();
		setNotInTheGate();
		setDirection(spawnDirection);
		setCurrentAnimation(Animation.MOVING);
		setPosition(spawn.x, spawn.y);
	}
	
	public void setAuthTiles(int[] tab1, int[] tab2)
	{
		this.authTiles = tab1;
		this.authTilesRoom = tab2;
	}
	
	@Override
	public double getSpeed() 
	{
		return Settings.SPEED;
	}
	
	@Override
	public int spritePerSecond()
	{
		return 4;
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

	public synchronized void setCurrentAnimation(Animation animation)
	{
		currentAnimation = animation;
	}
	
	public int getPoints()
	{
		return ghostPoint;
	}
	
	public void setBehaviour(IBehaviour b)
	{
		behaviour = b;
	}
	
	public void setSameCorridor(boolean bool)
	{
		sameCorridor = bool;
		/*if( sameCorridor)
		{
			setCurrentAnimation(Animation.FRIGHTENED);
		}
		else 
		{
			setCurrentAnimation(Animation.MOVING);
		}*/
	}
	
	public boolean getSameCorridor()
	{
		return sameCorridor;
	}
	
	public behavioursID getBehaviourID()
	{

		if ( getType() == GhostType.PINKY )
		{
			return behavioursID.AMBUSH;
		} else if ( getType() == GhostType.BLINKY )
		{
			return behavioursID.CHASE;
		} else if ( getType() == GhostType.INKY )
		{
			if (getSameCorridor())
			{
				if (locker == 0)
				{
					random = new Random();
					locker = random.nextInt(2)+1;
				}
				
				if (locker == 1)
				{
					return behavioursID.FEAR;
				} else
				{
					return behavioursID.CHASE;
				}
			} else {
				locker = 0;
				return behavioursID.RANDOM;
			}
			
			
		} else 
		{
			return behavioursID.RANDOM;
		}
	}
	
	

}
