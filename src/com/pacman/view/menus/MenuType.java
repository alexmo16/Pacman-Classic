package com.pacman.view.menus;

public enum MenuType 
{
	MAIN_MENU("Main Menu"),
	AUDIO("Audio"),
    HELP("Help");

    private final String value;

    MenuType(final String newValue)
    {
        value = newValue;
    }

    public String getValue() { return value; }
}
