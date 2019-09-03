package com.pacman.game;

public enum WorldTile
{
	FLOOR(0),
    WALL_START(1),
    WALL_END(24),
    GUM(30),
    ENERGIZER(40),
    FRUIT(50),
    PAC_MAN_START(60);

    private final int value;

    WorldTile(final int newValue)
    {
        value = newValue;
    }

    public int getValue() { return value; }
}