package com.pacman.view.views;

import java.awt.Color;
import java.awt.Graphics;

import com.pacman.model.IGame;
import com.pacman.view.utils.Renderer;

public class NewHighScoreView extends View
{
	private static final long serialVersionUID = 804878343283162688L;
	private final ViewType name = ViewType.NEW_HIGHSCORE;
	
	private final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	private final int CHAR_COUNT = 3;
	
	private int[] charsId = new int[CHAR_COUNT];
	private int selection;
	
	IGame game;
	
	public NewHighScoreView(IGame g)
	{
		game = g;
		resetName();
	}

	public void nextCharacter()
	{	
		charsId[selection] = (charsId[selection] + 1 >= CHARACTERS.length) ? 0 : charsId[selection] + 1; 
	}
	
	public void previousCharacter()
	{
		charsId[selection] = (charsId[selection] - 1 < 0) ? CHARACTERS.length - 1 : charsId[selection] - 1; 
	}
	
	public void moveSelectionRight()
	{
		selection += (selection + 1 >= CHAR_COUNT) ? 0 : 1;
	}
	
	public void moveSelectionLeft()
	{
		selection -= (selection - 1 < 0) ? 0 : 1;
	}
	
	public void resetName()
	{
		charsId[0] = 0;
		charsId[1] = 0;
		charsId[2] = 0;
		selection = 0;
	}
	
	public String getCurrentName()
	{
		String name = "";
		for (int c : charsId) { name += Character.toString(CHARACTERS[c]); }
		return name;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{		
		if (game == null) return;
		super.paintComponent(g);
        Renderer.renderBackground(g, Color.black, getWidth(), getHeight());

        int x, y;
        String txt;
		
        // View title
        txt = getName();
        x = (getWidth() - (3 * sFactor * txt.length())) / 2;
        y = getHeight() / 5;
        Renderer.renderString(g, txt, x, y, 3 * sFactor);
        
        // Score
        txt = Integer.toString(game.getPacman().getScore());
        x = (getWidth() - (2 * sFactor * txt.length())) / 2;
        y = getHeight() / 3;
        Renderer.renderString(g, txt, x, y, 2 * sFactor);
        
        // Player name
        txt = getCurrentName();
        x = (getWidth() - (2 * txt.length() * sFactor)) / 2;
        y =  getHeight() / 2;
    	Renderer.renderString(g, txt, x, y, 2 * sFactor);
        
        for (int i = 0; i < CHAR_COUNT; i++)
        {   
        	if (i == selection)
        	{
        		x = i * 2 * sFactor + (getWidth() - (txt.length() * 2 * sFactor)) / 2;
        		g.setColor(Color.yellow);
        		g.fillRect(x, y + 2 * sFactor, 2 * sFactor, sFactor / 10);
        		break;
        	}
        }
	}
	
	@Override
	public String getName() 
	{
		return name.getValue();
	}

}
