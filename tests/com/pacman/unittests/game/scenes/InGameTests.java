package com.pacman.unittests.game.scenes;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.pacman.game.scenes.InGame;

public class InGameTests {
	
	@Test
	void test_InGame_creation() 
	{
		boolean isException = false;
		try 
		{
		    new InGame();
		} catch (Exception e) 
		{
		    isException = true;
		}
		assertFalse( isException );
	}

}
