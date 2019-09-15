package com.pacman.model.objects.entities;

import java.util.Random;

import com.pacman.model.objects.Animation;
import com.pacman.model.objects.Sprites;
import com.pacman.model.world.Direction;
import com.pacman.model.world.GhostType;
import com.pacman.model.world.Tile;

public class Ghost extends Entity implements Animation
{
    private int animationState = 0, animationCycles = 2;
	private GhostType type;
	private boolean isAlive;
	private boolean isSpawning;
	private Random random;
	private int randomInt;
	private Direction firstDirection = Direction.UP;
	private double spawnX;
	private double spawnY;
	
    public Ghost(double x, double y, GhostType t)
    {
        super(x, y, 0.9, 0.9, Direction.UP);
        spawnX = x;
        spawnY = y;
        type = t;
        isAlive = false;
        isSpawning = false;
        updateSprite();
    }
    
    @Override
    public void updateSprite()
    {
    	sprite = Sprites.getGhost(type, direction, animationState);
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
	
	public boolean getSpawning() {
		return isSpawning;
	}
	
	public void setSpawning() {
		isSpawning = true;
	}
	
	public void setNotAlive()
	{
		isAlive = false;
	}
	
	public GhostType getType()
	{
		return type;
	}
	
	public void getNewDirection() {
		random = new Random();
		randomInt = random.nextInt(4);
		setDirection(Direction.values()[randomInt]);
	}
	
	public void respawn() {
		setNotAlive();
		setDirection(firstDirection);
		hitBox.x = spawnX;
		hitBox.y = spawnY;
	}
}
