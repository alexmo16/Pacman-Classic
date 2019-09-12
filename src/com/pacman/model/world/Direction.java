package com.pacman.model.world;

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
