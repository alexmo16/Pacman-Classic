package com.pacman.unittests.model.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pacman.model.objects.Gum;
import com.pacman.model.objects.Pacman;
import com.pacman.model.objects.StaticObject;
import com.pacman.model.world.Direction;
import com.pacman.utils.Settings;
import com.pacman.view.Input;

public class PacmanObjectTests
{
	
	Settings settingsNull = null;
	Input inputs = Mockito.mock( Input.class );
	StaticObject obj = Mockito.mock( StaticObject.class );
	Pacman pacmanOk = new Pacman(10,10,10,10,Direction.LEFT);
	Gum obj1 = new Gum(10,10,10,10);
	Gum obj2 = new Gum(10,10,10,10);
	
	
	@Test
	void test_void_pacman_object_creation() 
	{
		boolean isException = false;
		try 
		{
			new Pacman(0, 0, 0, 0, null);
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
		    new Pacman(0, 0, 0, 0, null);
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
		    new Pacman(10,10,10,10,Direction.LEFT);
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
		    new Pacman(-10,-10,10,10,Direction.UP);
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
		pacmanOk.eatGum(obj1);

		assertEquals(obj2.getEaten(),obj1.getEaten());

	}
	
	
	@Test
	void test__score_eat_gum() 
	{
		pacmanOk.eatGum(obj1);
		assertEquals(pacmanOk.getScore(), obj1.getPoints());

	}


}
