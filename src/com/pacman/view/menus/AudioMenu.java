package com.pacman.view.menus;

import java.awt.Color;
import java.awt.Graphics;

import com.pacman.view.utils.Renderer;

public class AudioMenu extends Menu
{
	private int fontScale;
	private int topOffset;
	
	public AudioMenu()
	{
		super(MenuType.AUDIO,
			  new MenuOption[] {MenuOption.MUSIC_VOLUME,
					  			MenuOption.SOUND_VOLUME,
					  	   	  	MenuOption.MUTE_MUSIC,
					  	   	  	MenuOption.MUTE_SOUND,
					  	   	  	MenuOption.BACK});
	}
	
	public void render(Graphics g, int w, int h, int s)
	{
		fontScale = 2 * s;
        topOffset = (h - (fontScale * (getOptions().length + 1))) / 2;
		
		renderTitle(g, w, h, s);
		renderBody(g, w, h, s);
	}
	
	private void renderTitle(Graphics g, int w, int h, int s)
	{
		Renderer.renderString(g, getName(), (w - (getName().length() * 3 * s)) / 2, topOffset / 2, 3 * s);
	}
	
	public void renderBody(Graphics g, int w, int h, int s)
	{
        int idx = 1, x, y;
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
	}
}
