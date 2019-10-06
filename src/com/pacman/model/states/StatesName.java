package com.pacman.model.states;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public enum StatesName
{
	MAIN_MENU("Main Menu"),
	INIT("Init"),
    PLAY("Play"),
    STOP("Stop"),
    PAUSE("Pause"),
    NEW_HIGHSCORE("New highscore"),
	RESUME("Resume");

    private final String value;

    StatesName(final String newValue)
    {
        value = newValue;
    }

    public String getValue() { return value; }
}
