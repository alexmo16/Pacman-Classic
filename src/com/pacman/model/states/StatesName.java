package com.pacman.model.states;

public enum StatesName
{
	INIT("Init"),
    PLAY("Play"),
    STOP("Stop"),
    PAUSE("Pause"),
	RESUME("Resume");

    private final String value;

    StatesName(final String newValue)
    {
        value = newValue;
    }

    public String getValue() { return value; }
}