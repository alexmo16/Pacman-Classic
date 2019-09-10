package com.pacman.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class Input implements KeyListener
{
    private static final int SUPPORTED_KEYS = 256;

    private final boolean[] keys = new boolean[SUPPORTED_KEYS];
    private final boolean[] lastPressedKeys = new boolean[SUPPORTED_KEYS];
    private HashMap<String, KeyLambda> pressedCallbacks = new HashMap<String, KeyLambda>();
    private HashMap<String, KeyLambda> releasedCallbacks = new HashMap<String, KeyLambda>();
    private char typed = 0;

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
    	int keyCode = e.getKeyCode();
        if (keyCode < SUPPORTED_KEYS)
        {
            keys[keyCode] = true;
            KeyLambda lambda = pressedCallbacks.get(KeyEvent.getKeyText(keyCode));
            if (lambda != null)
            {
            	lambda.run(e);
            }
        }
    }

    /**
     * Callback call by the framework when a key is released.
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
    	int keyCode = e.getKeyCode();
        if (keyCode < SUPPORTED_KEYS)
        {
            keys[keyCode] = false;
            KeyLambda lambda = releasedCallbacks.get(KeyEvent.getKeyText(keyCode));
            if (lambda != null)
            {
            	lambda.run(e);
            }
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
    
    public void addPressedCallback( String key, KeyLambda lambda )
    {
    	pressedCallbacks.put(key, lambda);
    }
}
