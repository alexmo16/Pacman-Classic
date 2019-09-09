package com.pacman.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener
{

    private WindowController window;

    private static final int SUPPORTED_KEYS = 256;

    private final boolean[] keys = new boolean[SUPPORTED_KEYS];
    private final boolean[] lastPressedKeys = new boolean[SUPPORTED_KEYS];

    private char typed = 0;

    public Input(WindowController window)
    {
        this.window = window;
        this.window.getFrame().addKeyListener(this);
    }

    /**
     * Copy the currently pressed keys int the lastPressedKeys buffer.
     */
    public void update()
    {
        System.arraycopy(keys, 0, lastPressedKeys, 0, SUPPORTED_KEYS);
        typed = (char) 0;
    }

    public boolean isKeyDown(int keyCode)
    {
        return keys[keyCode] && !lastPressedKeys[keyCode];
    }

    public boolean isKeyUp(int keyCode)
    {
        return !keys[keyCode] && lastPressedKeys[keyCode];
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        if (e != null)
        {
            typed = e.getKeyChar();
        }
    }

    public char getTyped()
    {
        return typed;
    }

    /**
     * Callback call by the framework when a key is pressed.
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() < SUPPORTED_KEYS)
        {
            keys[e.getKeyCode()] = true;
        }
    }

    /**
     * Callback call by the framework when a key is released.
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() < SUPPORTED_KEYS)
        {
            keys[e.getKeyCode()] = false;
        }
    }

    public final boolean[] getCurrentKeys()
    {
        return keys;
    }

    public final boolean[] getLastKeys()
    {
        return lastPressedKeys;
    }
}
