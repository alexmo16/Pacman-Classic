package com.pacman.view.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class Input implements KeyListener
{
    private static final int SUPPORTED_KEYS = 256;

    private HashMap<String, KeyLambda> pressedCallbacks = new HashMap<String, KeyLambda>();
    private HashMap<String, KeyLambda> releasedCallbacks = new HashMap<String, KeyLambda>();
    private char typed = 0;

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
            KeyLambda lambda = releasedCallbacks.get(KeyEvent.getKeyText(keyCode));
            if (lambda != null)
            {
            	lambda.run(e);
            }
        }
    }
    
    public void addPressedCallback( String key, KeyLambda lambda )
    {
    	pressedCallbacks.put(key, lambda);
    }
}
