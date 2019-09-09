package com.pacman.unittests.model.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pacman.controller.Input;
import com.pacman.model.Settings;
import com.pacman.model.objects.Gum;
import com.pacman.model.objects.PacmanObject;
import com.pacman.model.objects.ScoreBar;
import com.pacman.model.objects.StaticObject;
import com.pacman.model.world.Direction;

public class PacmanObjectTests
{
	
	Settings settings = new Settings();
	Settings settingsNull = null;
	Input inputs = Mockito.mock( Input.class );
	StaticObject obj = Mockito.mock( StaticObject.class );
	ScoreBar scoreBar = new ScoreBar(settings);
	ScoreBar scoreBar2 = new ScoreBar(settings);
	PacmanObject pacmanOk = new PacmanObject(10,10,10,10,Direction.LEFT,settings);
	Gum obj1 = new Gum(10,10,10,10,settings);
	Gum obj2 = new Gum(10,10,10,10,settings);
	
	
	@Test
	void test_void_pacman_object_creation() 
	{
		boolean isException = false;
		try 
		{
			new PacmanObject(settings);
		} catch (Exception e)
		{
			isException = true;
		}
		assertFalse( isException );

	}
	
	@Test
	void test_no_settings_void_pacman_object_creation()
	{
		boolean isException = false;
		try 
		{
		    new PacmanObject(null);
		} catch (Exception e) 
		{
		    isException = true;
		}
		assertFalse( isException );

	}
	
	@Test
	void test_pacman_object_creation() 
	{
		boolean isException = false;
		try 
		{
		    new PacmanObject(10,10,10,10,Direction.LEFT,settings);
		} catch (Exception e) 
		{
		    isException = true;
		}
		assertFalse( isException );

	}
	
	
	@Test
	void test_negative_position_pacman_object_creation() 
	{
		boolean isException = false;
		try 
		{
		    new PacmanObject(-10,-10,10,10,Direction.UP,settings);
		} catch (Exception e) 
		{
		    isException = true;
		}
		assertFalse( isException );

	}
	
	
	@Test
	void test_up_direction_pacman_object() 
	{
		Mockito.when( inputs.isKeyDown(KeyEvent.VK_UP) ).thenReturn( true );
		pacmanOk.checkNewDirection(inputs);

		assertEquals(Direction.UP,pacmanOk.getPacDirection());

	}
	
	@Test
	void test_down_direction_pacman_object() 
	{
		Mockito.when( inputs.isKeyDown(KeyEvent.VK_DOWN) ).thenReturn( true );
		pacmanOk.checkNewDirection(inputs);

		assertEquals(Direction.DOWN,pacmanOk.getPacDirection());

	}
	
	@Test
	void test_left_direction_pacman_object() 
	{
		Mockito.when( inputs.isKeyDown(KeyEvent.VK_LEFT) ).thenReturn( true );
		pacmanOk.checkNewDirection(inputs);

		assertEquals(Direction.LEFT,pacmanOk.getPacDirection());

	}
	
	@Test
	void test_right_direction_pacman_object()
	{
		Mockito.when( inputs.isKeyDown(KeyEvent.VK_RIGHT) ).thenReturn( true );
		pacmanOk.checkNewDirection(inputs);

		assertEquals(Direction.RIGHT,pacmanOk.getPacDirection());

	}
	
	@Test
	void test_null_direction_pacman_object() 
	{
		Mockito.when( inputs ).thenReturn( null );
		pacmanOk.checkNewDirection(inputs);

		assertEquals(Direction.LEFT,pacmanOk.getPacDirection());

	}
	
	@Test
	void test__set_eaten_eat_gum() 
	{
		obj2.setEaten(true);
		pacmanOk.eatGum(obj1,scoreBar);

		assertEquals(obj2.getEaten(),obj1.getEaten());

	}
	
	
	@Test
	void test__score_eat_gum() 
	{
		scoreBar2.addPointsScore(obj2.getPoints());
		pacmanOk.eatGum(obj1,scoreBar);

		assertEquals(scoreBar2.getScore(),scoreBar.getScore());

	}


}
