package com.pacman.model.world;

public enum Tile
{
	FLOOR(0),
    WALL_START(1),
    WALL_END(24),
    GUM(30),
    ENERGIZER(40),
    FRUIT(50),
    PAC_MAN_START(60);

    private final int value;

    Tile(final int newValue)
    {
        value = newValue;
    }

    public int getValue() { return value; }
}