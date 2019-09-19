package com.pacman.model.world;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public enum Direction
{
	RIGHT(0),
    DOWN(1),
    LEFT(2),
    UP(3);

    private final int value;

    Direction(final int newValue)
    {
        value = newValue;
    }

    public int getValue() { return value; }
}
