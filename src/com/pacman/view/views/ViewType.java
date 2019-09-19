package com.pacman.view.views;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
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