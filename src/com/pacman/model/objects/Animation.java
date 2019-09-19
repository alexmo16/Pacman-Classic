package com.pacman.model.objects;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public interface Animation 
{
	public void nextSprite();
	public int  spritePerSecond();
	public long getTimeSinceLastSpriteUpdate();
	public void setTimeSinceLastSpriteUpdate(long time);
}
