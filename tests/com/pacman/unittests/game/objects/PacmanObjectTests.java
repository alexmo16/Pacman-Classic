package com.pacman.unittests.game.objects;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;


import org.mockito.Mockito;

import org.junit.jupiter.api.Test;

import com.pacman.engine.Inputs;
import com.pacman.game.Settings;
import com.pacman.game.objects.PacmanObject;

public class PacmanObjectTests {
	
	Settings settings = new Settings();
	Inputs inputs = Mockito.mock( Inputs.class );
	PacmanObject pacmanOk = new PacmanObject(10,10,10,10,"right",settings);
	PacmanObject pacmanRightWrongPosi = new PacmanObject(10,10,10,10,"",settings);
	String direction;
	
	
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
	void test_pacman_object_creation() {
		boolean isException = false;
		try {
		      new PacmanObject(10,10,10,10,"right",settings);
		    } catch (Exception e) {
		      isException = true;
		    }
		assertFalse( isException );

	}
	
	@Test
	void test_empty_direction_pacman_object_creation() {
		boolean isException = false;
		try {
		      new PacmanObject(10,10,10,10,"",settings);
		    } catch (Exception e) {
		      isException = true;
		    }
		assertFalse( isException );

	}
	
	@Test
	void test_negative_position_pacman_object_creation() {
		boolean isException = false;
		try {
		      new PacmanObject(-10,-10,10,10,"right",settings);
		    } catch (Exception e) {
		      isException = true;
		    }
		assertFalse( isException );

	}
	
	@Test
	void test_new_direction_pacman_object() {
		pacmanOk.checkNewDirection(inputs);
		Mockito.when( inputs.isKeyDown(KeyEvent.VK_UP) ).thenReturn( true );

		assertEquals("up",pacmanOk.getDirection());

	}
}
