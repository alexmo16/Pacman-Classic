package com.pacman.model.menus;

public enum MenuOption 
{
	START("Start"),
	RESUME("Resume"),
	AUDIO("Audio"),
	HELP("Help"),
	EXIT("Exit");

    private final String value;

    MenuOption(final String newValue)
    {
        value = newValue;
    }

    public String getValue() { return value; }
}
