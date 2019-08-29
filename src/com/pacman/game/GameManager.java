package com.pacman.game;

//import java.awt.Color;
//import java.awt.Font;
//import java.awt.event.KeyEvent;

import com.pacman.engine.Engine;
import com.pacman.engine.IGame;
//import com.pacman.engine.Inputs;
import com.pacman.engine.Renderer;

public class GameManager implements IGame
{
	//private int x = 0;
	//private int y = 0;
	
	@Override
	public void init()
	{	
	}
	
	@Override
	public void update(Engine engine)
	{
		/*Inputs input = engine.getInputs();
		if ( input.isKeyDown( KeyEvent.VK_UP ) )
		{
			x--;
			y--;
		}
		if ( input.isKeyDown( KeyEvent.VK_DOWN ) )
		{
			x++;
			y++;
		}*/
		
	}

	@Override
	public void render(Renderer renderer ) 
	{
		/*Font smallFont = new Font("Helvetica", Font.BOLD, 14);
		renderer.drawText( "360 no scope", smallFont, Color.white, x, y);*/
	}
}
