package com.pacman.view.menus;

import java.awt.Color;
import java.awt.Graphics;

import com.pacman.model.highscores.Highscores;
import com.pacman.model.highscores.Score;
import com.pacman.view.utils.Renderer;

public class HighscoresMenu extends Menu
{

	public HighscoresMenu() 
	{
		super(MenuType.HIGHSCORES,
			  new MenuOption[] {MenuOption.BACK});
	}

	@Override
	public void render(Graphics g, int w, int h, int s) 
	{
		int fontScale = 2 * s;
		int topOffset = (h - (fontScale * ((getOptions().length + Highscores.get().size()) + 1))) / 2;
        
		Renderer.renderString(g, getName(), (w - (getName().length() * 3 * s)) / 2, topOffset / 2, 3 * s);
		
		int idx = 1, x, y, pos = 1;
        String txt;
        for (Score score : Highscores.get())
        {
        	txt = "" + pos + " " + score.getName() + " " + score.getPoints();
        	x = (w - (txt.length() * s)) / 2;
        	y = (idx * s) + topOffset;
        	
        	Renderer.renderString(g, txt, x, y, s);
	
        	pos += 1;
        	idx += 2;
        }
           
        for (MenuOption option : getOptions())
        {
        	txt = option.getValue();
        	x = (w - (txt.length() * s)) / 2;
        	y = (idx * s) + topOffset;
        	
        	Renderer.renderString(g, txt, x, y, s);
        	
        	if (option.getValue() == getCurrentSelection().getValue())
        	{
        		g.setColor(Color.yellow);
        		g.fillRect(x, y + s, txt.length() * s, s / 10);
        	}
        	
        	idx += 2;
        }
	}

}
