package com.pacman.game;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.pacman.engine.Engine;
import com.pacman.engine.IGame;
import com.pacman.engine.Inputs;
import com.pacman.engine.Renderer;
import com.pacman.game.objects.DynamicObject;;

public class GameManager implements IGame
{

	Rectangle pacman = new Rectangle(10,10,50,50);
	String direction = "right";
	private int x = 1;
	private int buffer = 0;
	Image pacmanSprite;
	Settings settings = new Settings();
	
	private boolean isPlaying = true;
	
	@Override
	public void init()
	{
		isPlaying = true;
	}
	
	@Override
	public void update(Engine engine)
	{	
		Inputs inputs = engine.getInputs();
		if ( inputs.isKeyDown( settings.getMutedButton() ) )
		{
			Engine.toggleMute();
		}
		
		if ( inputs.isKeyDown( settings.getPauseButton() ) )
		{
			isPlaying = !isPlaying;
			Engine.setIsMuted( !isPlaying );
		}
		
		if ( isPlaying )
		{
			direction = DynamicObject.getInstance().getNewDirection(direction);
			DynamicObject.getInstance().updatePosition(pacman, direction);
		}
		
	}

	@Override
	public void render(Renderer renderer) 
	{
		buffer += 1;
		if (buffer == 10) {
			x += 1;
			if (x == 4) {
				x = 1;
			}
			buffer = 1;
		}
		try {
			pacmanSprite = ImageIO.read(new File("assets"+File.separator+"pacman_"+direction+"_"+x+".png"));
			renderer.drawImage(pacmanSprite, (int) pacman.getX(), (int) pacman.getY());
		} catch (IOException e) {
		}
	}
	
	@Override
	public Settings getSettings()
	{
		return settings;
	}
}
