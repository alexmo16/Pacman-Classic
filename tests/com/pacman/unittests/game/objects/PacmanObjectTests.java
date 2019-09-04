package com.pacman.unittests.game.objects;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;


import org.mockito.Mockito;

import org.junit.jupiter.api.Test;

import com.pacman.engine.Inputs;
import com.pacman.game.Settings;
import com.pacman.game.objects.DynamicObject.Direction;
import com.pacman.game.objects.PacmanObject;

public class PacmanObjectTests {
	
	Settings settings = new Settings();
	Settings settingsNull = null;
	Inputs inputs = Mockito.mock( Inputs.class );
	PacmanObject pacmanOk = new PacmanObject(10,10,10,10,Direction.LEFT,settings);
	
	
	@Test
	void test_void_pacman_object_creation() {
		boolean isException = false;
		try {
		      new PacmanObject(settings);
		    } catch (Exception e) {
		      isException = true;
		    }
		assertFalse( isException );

	}
	
	@Test
	void test_no_settings_pacman_object_creation() {
		boolean isException = false;
		try {
		      new PacmanObject(null);
		    } catch (Exception e) {
		      isException = true;
		    }
		assertFalse( isException );

	}
	
	@Test
	void test_pacman_object_creation() {
		boolean isException = false;
		try {
		      new PacmanObject(10,10,10,10,Direction.LEFT,settings);
		    } catch (Exception e) {
		      isException = true;
		    }
		assertFalse( isException );

	}
	
	
	@Test
	void test_negative_position_pacman_object_creation() {
		boolean isException = false;
		try {
		      new PacmanObject(-10,-10,10,10,Direction.UP,settings);
		    } catch (Exception e) {
		      isException = true;
		    }
		assertFalse( isException );

	}
	
	
	@Test
	void test_up_direction_pacman_object() {
		Mockito.when( inputs.isKeyDown(KeyEvent.VK_UP) ).thenReturn( true );
		pacmanOk.checkNewDirection(inputs);

		System.out.println(pacmanOk.getPacDirection());
		assertEquals(Direction.UP,pacmanOk.getPacDirection());

	}
	
	@Test
	void test_down_direction_pacman_object() {
		Mockito.when( inputs.isKeyDown(KeyEvent.VK_DOWN) ).thenReturn( true );
		pacmanOk.checkNewDirection(inputs);

		System.out.println(pacmanOk.getPacDirection());
		assertEquals(Direction.DOWN,pacmanOk.getPacDirection());

	}
	
	@Test
	void test_left_direction_pacman_object() {
		Mockito.when( inputs.isKeyDown(KeyEvent.VK_LEFT) ).thenReturn( true );
		pacmanOk.checkNewDirection(inputs);

		System.out.println(pacmanOk.getPacDirection());
		assertEquals(Direction.LEFT,pacmanOk.getPacDirection());

	}
	
	@Test
	void test_right_direction_pacman_object() {
		Mockito.when( inputs.isKeyDown(KeyEvent.VK_RIGHT) ).thenReturn( true );
		pacmanOk.checkNewDirection(inputs);

		System.out.println(pacmanOk.getPacDirection());
		assertEquals(Direction.RIGHT,pacmanOk.getPacDirection());

	}
}
