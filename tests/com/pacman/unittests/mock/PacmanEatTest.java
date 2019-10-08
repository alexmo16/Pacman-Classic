package com.pacman.unittests.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pacman.controller.GameController;
import com.pacman.model.Game;
import com.pacman.view.Window;
import com.pacman.view.menus.MenuOption;

class PacmanEatTest 
{
	private final String smallMapWithEnergizer = System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "smallMapWithEnergizer.txt";
	private final String smallMapNoEnergizer = System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "smallMapNoEnergizer.txt";

	private Robot robot;
    private Game game;
    private Window window;
    private GameController engine;
	
	@BeforeEach
	void beforeEach() throws AWTException
	{
		robot = new Robot();
        game = new Game();
        window = new Window(game);
        engine = new GameController(window, game);
        window.addGameController(engine);
        engine.start();
	}
	
	@AfterEach
	void afterEach()
	{
		engine.stopGame();
        Sleep(1000);
        assertFalse(engine.getIsRunning());	
	}
	
	@Test
	void TestPacmanEatEnergizerAndGhost()
	{
        game.setLevelDataFile(smallMapWithEnergizer);
        Sleep(1000);
        assertTrue(engine.getIsRunning());
        
        // Start game
        while (window.getMenuOption() != MenuOption.START)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        
        assertEquals(game.getPacman().getEatenGhosts(), 0);
        
        Sleep(10000);
        
        assertEquals(game.getPacman().getEatenGhosts(), 1);
        assertEquals(game.getGhosts().get(0).getAlive(), false);
        assertEquals(game.getPacman().getScore(), 250);
	}

	@Test
	void TestPacmanEatGhostAndDies()
	{
        game.setLevelDataFile(smallMapNoEnergizer);
        Sleep(1000);
        assertTrue(engine.getIsRunning());
        
        // Start game
        while (window.getMenuOption() != MenuOption.START)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        
        assertEquals(game.getPacman().getLives(), 3);
        assertEquals(game.getPacman().getEatenGhosts(), 0);
        
        Sleep(10000);
        
        assertEquals(game.getPacman().getEatenGhosts(), 0);
        assertEquals(game.getPacman().getLives(), 2);
        assertEquals(game.getPacman().getScore(), 0);
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
