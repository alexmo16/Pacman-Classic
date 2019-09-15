package com.pacman.model.world;

public enum Tile
{
	FLOOR(0),
    WALL_START(1),
    DOOR_1(2),
    DOOR_2(14),
    DOOR_3(15),
    DOOR_4(16),
    WALL_END(24),
    GUM(30),
    ENERGIZER(40),
    FRUIT(50),
    PAC_MAN_START(60),
	BLINKY_START(61),
	PINKY_START(62),
	INKY_START(63),
	CLYDE_START(64),
    TUNNEL(70);

    private final int value;

    Tile(final int newValue)
    {
        value = newValue;
    }

    public int getValue() { return value; }
}