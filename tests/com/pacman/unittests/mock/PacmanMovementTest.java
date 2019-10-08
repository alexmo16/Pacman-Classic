package com.pacman.unittests.mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;

import org.junit.jupiter.api.Test;

import com.pacman.controller.GameController;
import com.pacman.model.Game;
import com.pacman.view.Window;
import com.pacman.view.menus.MenuOption;

class PacmanMovementTest 
{
	private final String mapPath = System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "smallMap.txt";

	@Test
	void testMoveAllDirections() throws AWTException 
	{
		Robot robot = new Robot();
        Game game = new Game();
        Window window = new Window(game);
        GameController engine = new GameController(window, game);
        window.addGameController(engine);
        engine.start();
        game.setLevelDataFile(mapPath);
        
        Sleep(1000);
        assertTrue(engine.getIsRunning());
        
        // Start game
        while (window.getMenuOption() != MenuOption.START)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        
    	double x = game.getPacman().getPosition().getX();
    	double y = game.getPacman().getPosition().getY();
        
        Sleep(7000); 
		
		// Init direction
		assertTrue(game.getPacman().getPosition().getX() < x);
		assertTrue(game.getPacman().getPosition().getY() == y);
		x = game.getPacman().getPosition().getX();
		y = game.getPacman().getPosition().getY();
		
		// Up
		robot.keyPress(KeyEvent.VK_UP);
		Sleep(1000);
		assertTrue(game.getPacman().getPosition().getX() == x);
		assertTrue(game.getPacman().getPosition().getY() < y);
		x = game.getPacman().getPosition().getX();
		y = game.getPacman().getPosition().getY();
		
		// Right
		robot.keyPress(KeyEvent.VK_RIGHT);
		Sleep(1000);
		assertTrue(game.getPacman().getPosition().getX() > x);
		assertTrue(game.getPacman().getPosition().getY() == y);
		x = game.getPacman().getPosition().getX();
		y = game.getPacman().getPosition().getY();
		
		// Down
		robot.keyPress(KeyEvent.VK_DOWN);
		Sleep(1000);
		assertTrue(game.getPacman().getPosition().getX() == x);
		assertTrue(game.getPacman().getPosition().getY() > y);
		x = game.getPacman().getPosition().getX();
		y = game.getPacman().getPosition().getY();
		
		// Left
		robot.keyPress(KeyEvent.VK_LEFT);
		Sleep(1000);
		assertTrue(game.getPacman().getPosition().getX() < x);
		assertTrue(game.getPacman().getPosition().getY() == y);
		
		engine.stopGame();
		
        Sleep(1000);
        assertFalse(engine.getIsRunning());
	}   
	
    private void Sleep(int timer)
    {
        try
        {
            Thread.sleep(timer);
        }
        catch(InterruptedException e)
        {
            assertTrue(false, "Sleep error");
        }
    }
}
