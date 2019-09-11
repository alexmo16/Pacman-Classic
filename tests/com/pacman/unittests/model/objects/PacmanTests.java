package com.pacman.unittests.model.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pacman.model.objects.consumables.PacDot;
import com.pacman.model.objects.entities.Pacman;
import com.pacman.utils.Settings;
import com.pacman.view.Input;

public class PacmanTests
{
	
	Settings settingsNull = null;
	Input inputs = Mockito.mock( Input.class );
	Pacman pacmanOk = new Pacman(10,10);
	
	@Test
	void test_void_pacman_object_creation() 
	{
		boolean isException = false;
		try 
		{
			new Pacman(0, 0);
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
		    new Pacman(0, 0);
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
		    new Pacman(10,10);
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
		    new Pacman(-10,-10);
		} catch (Exception e) 
		{
		    isException = true;
		}
		assertFalse( isException );

	}

	
	@Test
	void test__score_eat_gum() 
	{
		PacDot pacDot = new PacDot(1,1);
		pacmanOk.eat(pacDot);
		assertEquals(pacmanOk.getScore(), pacDot.getPoints());

	}


}
