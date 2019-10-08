package com.pacman.model.world;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public enum GhostType
{
	BLINKY(Tile.BLINKY_START.getValue()),
    PINKY(Tile.PINKY_START.getValue()),
    CLYDE(Tile.CLYDE_START.getValue()),
    INKY(Tile.INKY_START.getValue());
    

    private final int value;

    GhostType(final int newValue)
    {
        value = newValue;
    }

    public int getValue() { return value; }
}
