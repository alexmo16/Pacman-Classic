package com.pacman.game;

import java.awt.Rectangle;

import com.pacman.engine.Engine;
import com.pacman.engine.IGame;
import com.pacman.engine.Renderer;
import com.pacman.game.DynamicObject;

public class GameManager implements IGame
{

	Rectangle pacman = new Rectangle(10,10,50,50);
	String direction = "right";

	
	@Override
	public void init()
	{

	}
	
	@Override
	public void update(Engine engine)
	{
		direction = DynamicObject.getInstance().getNewDirection(direction);
		DynamicObject.getInstance().updatePosition(pacman, direction);

	}

	@Override
	public void render(Renderer renderer ) 
	{
		/*Font smallFont = new Font("Helvetica", Font.BOLD, 14);
		renderer.drawText( "360 no scope", smallFont, Color.white, x, y);*/
	}
}
