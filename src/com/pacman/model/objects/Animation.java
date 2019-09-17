package com.pacman.model.objects;

public interface Animation 
{
	public void nextSprite();
	public int  spritePerSecond();
	public long getTimeSinceLastSpriteUpdate();
	public void setTimeSinceLastSpriteUpdate(long time);
}
