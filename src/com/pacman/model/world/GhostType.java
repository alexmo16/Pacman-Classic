package com.pacman.model.world;

public enum GhostType
{
	BLINKY(Tile.BLINKY_START.getValue()),
    PINKY(Tile.PINKY_START.getValue()),
    INKY(Tile.INKY_START.getValue()),
    CLYDE(Tile.CLYDE_START.getValue());

    private final int value;

    GhostType(final int newValue)
    {
        value = newValue;
    }

    public int getValue() { return value; }
}
