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
	private final int WAIT_TIME_ROBOT = 200; // ms
	private final int WAIT_TIME_START_GAME = 1000; // ms
	
	@Test
	void testStartGame() throws AWTException
	{
		Game game = new Game();
        Window window = new Window(game);
		GameController engine = new GameController(window, game);
        window.addGameController(engine);
        engine.start();
        sleep(WAIT_TIME_START_GAME);
        
		assertTrue(engine.getIsRunning());
		
		Robot robot = new Robot();
        
        while (window.getMenuOption() != MenuOption.START)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        
        sleep(WAIT_TIME_ROBOT);
        
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
        sleep(WAIT_TIME_START_GAME);
        
		assertTrue(engine.getIsRunning());
		
		Robot robot = new Robot();
        
		// Audio
        while (window.getMenuOption() != MenuOption.AUDIO)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        	sleep(WAIT_TIME_ROBOT);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        sleep(WAIT_TIME_ROBOT);
        
        MenuType menuType = window.getMenuType();
        assertTrue(menuType == MenuType.AUDIO);
        while (window.getMenuOption() != MenuOption.BACK)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        	sleep(WAIT_TIME_ROBOT);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        sleep(WAIT_TIME_ROBOT);
        
        // Help
        while (window.getMenuOption() != MenuOption.HELP)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        	sleep(WAIT_TIME_ROBOT);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        sleep(WAIT_TIME_ROBOT);
        
        menuType = window.getMenuType();
        assertTrue(menuType == MenuType.HELP);
        while (window.getMenuOption() != MenuOption.BACK)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        	sleep(WAIT_TIME_ROBOT);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        while (window.getMenuOption() != MenuOption.START)
        {
        	robot.keyPress(KeyEvent.VK_UP);
        	sleep(WAIT_TIME_ROBOT);
        }
        
        // HighScore
        while (window.getMenuOption() != MenuOption.HIGHSCORES)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        	sleep(WAIT_TIME_ROBOT);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        sleep(WAIT_TIME_ROBOT);
        
        menuType = window.getMenuType();
        assertTrue(menuType == MenuType.HIGHSCORES);
        while (window.getMenuOption() != MenuOption.BACK)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        	sleep(WAIT_TIME_ROBOT);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        while (window.getMenuOption() != MenuOption.START)
        {
        	robot.keyPress(KeyEvent.VK_UP);
        	sleep(WAIT_TIME_ROBOT);
        }
        
        // Exit
        while (window.getMenuOption() != MenuOption.EXIT)
        {
        	robot.keyPress(KeyEvent.VK_DOWN);
        	sleep(WAIT_TIME_ROBOT);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        
        sleep(WAIT_TIME_ROBOT);
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
