package com.pacman.model.objects.entities;

import java.util.Random;

import com.pacman.model.objects.Animation;
import com.pacman.model.objects.Sprites;
import com.pacman.model.world.Direction;
import com.pacman.model.world.GhostType;

public class Ghost extends Entity implements Animation
{
    private int animationState = 0, animationCycles = 2;
	private GhostType type;
	private boolean isAlive;
	private volatile boolean isSpawning;
	private boolean isInTheGate;
	private Random random;
	private int randomInt;
	
	private int[] authTiles;
	private int[] authTilesRoom;
	
    public Ghost(double x, double y, GhostType t )
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
    	sprite = Sprites.getGhost(type, direction, animationState);
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
	public void nextSprite() 
	{
		animationState = (animationState + 1) % animationCycles;
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
	
	public synchronized boolean getSpawning() {
		return isSpawning;
	}
	
	public synchronized void setSpawning() {
		isSpawning = true;
	}
	
	public boolean getInTheGate() {
		return isInTheGate;
	}
	
	public void setInTheGate() {
		isInTheGate = true;
	}
	
	public void setNotInTheGate() {
		isInTheGate = false;
	}
	
	public synchronized void setNotSpawning() {
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
		random = new Random();
		randomInt = random.nextInt(4);
		if (Direction.values()[(randomInt+2)%4] != getDirection())
		{
			setDirection(Direction.values()[randomInt]);
		} 
		else 
		{
			getNewDirection();
		}
	}
	
	public void respawn() 
	{
		setNotAlive();
		setNotSpawning();
		setNotInTheGate();
		setDirection(spawnDirection);
		setPosition(spawn.x, spawn.y);
	}
	
	public void setAuthTiles(int[] tab1, int[] tab2)
	{
		this.authTiles = tab1;
		this.authTilesRoom = tab2;
	}
}
