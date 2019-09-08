package com.pacman.model.world;

public enum Direction
{
	RIGHT(0),
    LEFT(1),
    UP(2),
    DOWN(3);

    private final int value;

    Direction(final int newValue)
    {
        value = newValue;
    }

    public int getValue() { return value; }
}
