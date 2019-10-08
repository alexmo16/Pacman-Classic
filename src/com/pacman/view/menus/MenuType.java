package com.pacman.view.menus;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public enum MenuType 
{
	MAIN_MENU("Main Menu"),
	AUDIO("Audio"),
    HELP("Help"),
    HIGHSCORES("High Scores");

    private final String value;

    MenuType(final String newValue)
    {
        value = newValue;
    }

    public String getValue() { return value; }
}
