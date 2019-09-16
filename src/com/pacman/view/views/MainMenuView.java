package com.pacman.view.views;

import java.awt.Color;
import java.awt.Graphics;

import com.pacman.model.IGame;
import com.pacman.model.menus.MenuOption;
import com.pacman.model.objects.Sprites;
import com.pacman.model.world.Level;
import com.pacman.model.world.Sprite;
import com.pacman.view.ViewType;

public class MainMenuView extends View
{	
	private static final long serialVersionUID = 1665591442723213675L;
	private static final ViewType name = ViewType.MAIN_MENU;
	
	private IGame game;
	
	public MainMenuView(IGame g)
	{
		game = g;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		if (Level.getWidth() == 0 || Level.getHeight() == 0)
		{
			return;
		}
		
		super.paintComponent(g);
		
		renderBackground(g, Color.black);
        renderTitle(g);
        renderOptions(g);
	}
	
	private void renderTitle(Graphics g)
	{
		Sprite title = Sprites.getTitle();
        int xTileScale, yTileScale;

    	xTileScale = 4 * (title.getWidth() / (Sprites.getTilesize()));
    	yTileScale = 4 * (title.getHeight() / (Sprites.getTilesize()));
    
    	
        int x = (getWidth() - (xTileScale * sFactor)) / 2;
        int y = getHeight() / 6;
        
        g.drawImage(Sprites.getTilesSheet(), 
					(int)(x), 
					(int)(y), 
					(int)(x + (xTileScale * sFactor)), 
					(int)(y + (yTileScale * sFactor)), 
					title.getX1(), 
					title.getY1(), 
					title.getX2(), 
					title.getY2(), 
					null);  
	}
	
	private void renderOptions(Graphics g)
	{
		int fontScale = 2 * sFactor;
        int topOffset = (getHeight() - (fontScale * (game.getMainMenuOptions().size() + 1))) / 2;
        
        int idx = 1, x, y;
        for (MenuOption option : game.getMainMenuOptions())
        {
        	x = (getWidth() - (option.getValue().length() * fontScale)) / 2;
        	y = (idx * fontScale) + topOffset;
        	
        	renderCustomScaleString(g, option.getValue(), x, y,fontScale);
        	
        	if (option.getValue() == game.getCurrentSelection().getValue())
        	{
        		g.setColor(Color.yellow);
        		g.fillRect(x, y + fontScale, option.getValue().length() * fontScale, fontScale / 10);
        	}
        	
        	idx += 2;
        }
	}
	
	@Override
	public String getName()
	{
		return name.getValue();
	}
}
