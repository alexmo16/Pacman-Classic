package com.pacman.view.menus;

import java.awt.Color;
import java.awt.Graphics;

import com.pacman.model.objects.Sprites;
import com.pacman.model.world.Sprite;
import com.pacman.view.utils.Renderer;

public class MainMenu extends Menu
{
	public MainMenu()
	{
		super(MenuType.MAIN_MENU,
			  new MenuOption[] {MenuOption.START,
					  			MenuOption.AUDIO,
					  			MenuOption.HELP,
					  			MenuOption.EXIT});
	}
	
	public void render(Graphics g, int w, int h, int s)
	{
		renderTitle(g, w, h, s);
		renderBody(g, w, h, s);
	}
	
	private void renderTitle(Graphics g, int w, int h, int s)
	{
		Sprite title = Sprites.getTitle();
        int xTileScale, yTileScale;

    	xTileScale = 4 * (title.getWidth() / (Sprites.getTilesize()));
    	yTileScale = 4 * (title.getHeight() / (Sprites.getTilesize()));
    
        int x = (w - (xTileScale * s)) / 2;
        int y = h / 6;
        
        g.drawImage(Sprites.getTilesSheet(), 
					(int)(x), 
					(int)(y), 
					(int)(x + (xTileScale * s)), 
					(int)(y + (yTileScale * s)), 
					title.getX1(), 
					title.getY1(), 
					title.getX2(), 
					title.getY2(), 
					null);
	}
	
	public void renderBody(Graphics g, int w, int h, int s)
	{
		int fontScale = 2 * s;
        int topOffset = (h - (fontScale * (getOptions().length + 1))) / 2;
        
        int idx = 1, x, y;
        for (MenuOption option : getOptions())
        {
        	x = (w - (option.getValue().length() * fontScale)) / 2;
        	y = (idx * fontScale) + topOffset;
        	
        	Renderer.renderString(g, option.getValue(), x, y,fontScale);
        	
        	if (option.getValue() == getCurrentSelection().getValue())
        	{
        		g.setColor(Color.yellow);
        		g.fillRect(x, y + fontScale, option.getValue().length() * fontScale, fontScale / 10);
        	}
        	
        	idx += 2;
        }
	}
}
