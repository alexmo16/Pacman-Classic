package com.pacman.view.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.pacman.model.world.Level;
import com.pacman.view.KeyInput;
import com.pacman.view.ViewType;

public class HelpView extends View 
{
	private static final long serialVersionUID = 5447424615297222524L;
	private static final ViewType name = ViewType.HELP;

	@Override
	public void paintComponent(Graphics g)
	{
		if (Level.getWidth() == 0 || Level.getHeight() == 0)
		{
			return;
		}
		
		super.paintComponent(g);
		
		renderBackground(g, Color.black);

		int topOffset = (getHeight() - (sFactor * 2 *  KeyInput.values().length)) /2;
		String s;
		
		// View name
		s = ViewType.HELP.getValue();
		renderCustomScaleString(g, s, (getWidth() - (s.length() * 3 * sFactor)) / 2, topOffset / 2, 3 * sFactor);
		
		// Key input list
        int idx = 0, x, y;
		for (KeyInput key : KeyInput.values())
		{
			s = KeyEvent.getKeyText(key.getValue()) + " - " + key.getDescription();
        	x = (getWidth() - (s.length() * sFactor)) / 2;
        	y = (idx * sFactor) + topOffset;
        	
    		renderString(g, s, x, y);
    		idx += 2;
		}
		
		// Back button
		s = "Back";
		x = (getWidth() - (s.length() * sFactor)) / 2;
		y = getHeight() - (topOffset / 2);
    	renderCustomScaleString(g, s, x, y, sFactor);
    	
		g.setColor(Color.yellow);
		g.fillRect(x, y + sFactor, s.length() * sFactor, sFactor / 10);
	}
	
	@Override
	public String getName()
	{
		return name.getValue();
	}

}
