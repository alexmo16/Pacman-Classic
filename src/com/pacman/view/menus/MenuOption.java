package com.pacman.view.menus;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public enum MenuOption 
{
	START("Start"),
	RESUME("Resume"),
	AUDIO("Audio"),
	HELP("Help"),
	EXIT("Exit"),
	MUSIC_VOLUME("Music volume"),
	SOUND_VOLUME("Sound volume"),
	MUTE_MUSIC("Mute music"),
	MUTE_SOUND("Mute sound"),
	BACK("Back");

    private final String value;

    MenuOption(final String newValue)
    {
        value = newValue;
    }

    public String getValue() { return value; }
}
