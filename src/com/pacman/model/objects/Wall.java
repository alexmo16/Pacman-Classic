package com.pacman.model.objects;

import com.pacman.model.world.Tile;
import com.pacman.utils.Settings;

public class Wall extends GameObject
{
    public Wall(double x, double y, int type)
    {
        super(x + 0.25, y + 0.25, 0.5, 0.5);
    	if (type >= Tile.WALL_START.getValue() && type <= Tile.WALL_END.getValue())
    	{
    		sprite = Settings.SPRITES.getWall(type);
    	}
    }   
}
