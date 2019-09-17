package com.pacman.model.menus;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class HelpMenu
{
    private final static HashMap<Integer, String> controls = new HashMap<Integer, String>();
    
    public HelpMenu()
    {
    	controls.put(KeyEvent.VK_M, "Mute");
    	//KeyEvent.VK_M
    }

}
