package com.pacman.view.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.pacman.view.inputs.KeyInput;
import com.pacman.view.utils.Renderer;

public class HelpMenu extends Menu
{
	private int fontScale;
	private int topOffset;
	
	public HelpMenu()
	{
		super(MenuType.HELP,
			  new MenuOption[] {MenuOption.BACK});
	}
	
	public void render(Graphics g, int w, int h, int s)
	{
		fontScale = 2 * s;
        topOffset = (h - (fontScale * (getOptions().length + KeyInput.values().length + 1))) / 2;
		
		renderTitle(g, w, h, s);
		renderBody(g, w, h, s);
	}
	
	private void renderTitle(Graphics g, int w, int h, int s)
	{
		Renderer.renderString(g, getName(), (w - (getName().length() * 3 * s)) / 2, topOffset / 2, 3 * s);
	}
	
	public void renderBody(Graphics g, int w, int h, int s)
	{
		// Key input list
		String k;
        int idx = 0, x, y;
		for (KeyInput key : KeyInput.values())
		{
			k = KeyEvent.getKeyText(key.getValue()) + "-" + key.getDescription();
        	x = (w - (k.length() * s)) / 2;
        	y = (idx * s) + topOffset;
        	
        	Renderer.renderString(g, k, x, y, s);
    		idx += 2;
		}
		
		// Back button
        for (MenuOption option : getOptions())
        {
        	x = (w - (option.getValue().length() * s)) / 2;
        	y = (idx * s) + topOffset;
        	
        	Renderer.renderString(g, option.getValue(), x, y, s);
        	
        	if (option.getValue() == getCurrentSelection().getValue())
        	{
        		g.setColor(Color.yellow);
        		g.fillRect(x, y + s, option.getValue().length() * s, s / 10);
        	}
        	idx += 2;
        }
		// x = (w- (op.length() * s)) / 2;
		//y = h - (topOffset / 2);
		//Renderer.renderString(g, k, x, y, s);
	}
}
