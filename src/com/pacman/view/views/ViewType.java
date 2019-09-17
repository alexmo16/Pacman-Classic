package com.pacman.view.views;

public enum ViewType
{
	MAIN_MENU("MainMenu"),
	GAME("Game");

    private final String value;

    ViewType(final String newValue)
    {
        value = newValue;
    }

    public String getValue() { return value; }
}