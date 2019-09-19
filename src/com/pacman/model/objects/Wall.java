package com.pacman.model.objects;

import com.pacman.model.world.Tile;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
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
