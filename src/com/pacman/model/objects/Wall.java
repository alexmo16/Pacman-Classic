package com.pacman.model.objects;

import com.pacman.model.world.Tile;

public class Wall extends GameObject
{
    public Wall(double x, double y, int type)
    {
        super(x, y);
    	if (type >= Tile.WALL_START.getValue() && type <= Tile.WALL_END.getValue())
    	{
    		sprite = Sprites.getWall(type);
    	}
    }   
}
