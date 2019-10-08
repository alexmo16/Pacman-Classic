package com.pacman.unittests.mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;

import com.pacman.controller.GameController;
import com.pacman.model.Game;
import com.pacman.model.states.IGameState;
import com.pacman.model.states.StatesName;
import com.pacman.view.Window;
import com.pacman.view.menus.MenuOption;
import com.pacman.view.menus.MenuType;

class UISelectionTest 
{	
	@Test
	void testStartGame() throws AWTException
	{
		Game game = new Game();
        Window window = new Window(game);
		GameController engine = new GameController(window, game);
        window.addGameController(engine);
        engine.start();
        sleep(1000);
        
		assertTrue(engine.getIsRunning());
		
		Robot robot = new Robot();
        
        while (window.getMenuOption() != MenuOption.START)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        
        sleep(500);
        
        IGameState state = game.getCurrentState();
        if (state == null)
        {
        	fail();
        	return;
        }
        
        StatesName name = state.getName();
        if ( name != StatesName.INIT)
        {
        	fail();
        	return;
        }
        
        assertTrue(true);
        engine.stopGame();
		assertFalse(engine.getIsRunning());
	}
	
	@Test
	void testMenusAndExit() throws AWTException 
	{
		Game game = new Game();
        Window window = new Window(game);
		GameController engine = new GameController(window, game);
        window.addGameController(engine);
        engine.start();
        sleep(1000);
        
		assertTrue(engine.getIsRunning());
		
		Robot robot = new Robot();
        
		// Audio
        while (window.getMenuOption() != MenuOption.AUDIO)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        	sleep(100);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        sleep(100);
        
        MenuType menuType = window.getMenuType();
        assertTrue(menuType == MenuType.AUDIO);
        while (window.getMenuOption() != MenuOption.BACK)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        	sleep(100);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        sleep(100);
        
        // Help
        while (window.getMenuOption() != MenuOption.HELP)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        	sleep(100);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        sleep(100);
        
        menuType = window.getMenuType();
        assertTrue(menuType == MenuType.HELP);
        while (window.getMenuOption() != MenuOption.BACK)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        	sleep(100);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        while (window.getMenuOption() != MenuOption.START)
        {
        	robot.keyPress(KeyEvent.VK_UP);
        	sleep(100);
        }
        
        // HighScore
        while (window.getMenuOption() != MenuOption.HIGHSCORES)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        	sleep(100);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        sleep(100);
        
        menuType = window.getMenuType();
        assertTrue(menuType == MenuType.HIGHSCORES);
        while (window.getMenuOption() != MenuOption.BACK)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        	sleep(100);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        while (window.getMenuOption() != MenuOption.START)
        {
        	robot.keyPress(KeyEvent.VK_UP);
        	sleep(100);
        }
        
        // Exit
        while (window.getMenuOption() != MenuOption.EXIT)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        	sleep(100);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        
        sleep(100);
		assertFalse(engine.getIsRunning());
	}
	
    private void sleep(int timer)
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
