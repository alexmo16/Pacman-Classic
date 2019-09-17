package com.pacman.view.inputs;

import java.awt.event.KeyEvent;

public enum KeyInput
{
	P(KeyEvent.VK_P, "Pause"),
	R(KeyEvent.VK_R, "Resume"),
	UP(KeyEvent.VK_UP, "Move up"),
	DOWN(KeyEvent.VK_DOWN, "Move down"), 
	RIGHT(KeyEvent.VK_RIGHT, "Move right"),
	LEFT(KeyEvent.VK_LEFT, "Move left"), 
	ESCAPE(KeyEvent.VK_ESCAPE, "Open menu"),
	ENTER(KeyEvent.VK_ENTER, "Select menu option");

    private final int value;
    private final String description;

    KeyInput(final int newValue, final String desc)
    {
        value = newValue;
        description = desc;
    }

    public int getValue() { return value; }
    public String getDescription() { return description; }
}