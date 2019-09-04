package com.pacman.unittests.game.objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.pacman.game.Settings;
import com.pacman.game.objects.PacmanObject;

public class PacmanObjectTests {
	
	static Settings settings = new Settings();
	PacmanObject pacmanOk = new PacmanObject(10,10,10,10,"right",settings);
	PacmanObject pacmanRightWrongPosi = new PacmanObject(10,10,10,10,"",settings);
	String direction;
	
	
	@Test
	void test_void_pacman_object_creation() {
		boolean isException = false;
		try {
		      new PacmanObject();
		    } catch (Exception e) {
		      isException = true;
		    }
		assertTrue( isException );

	}
	
	@Test
	void test_pacman_object_creation() {
		boolean isException = false;
		try {
		      new PacmanObject(10,10,10,10,"right",settings);
		    } catch (Exception e) {
		      isException = true;
		    }
		assertTrue( isException );

	}
	
	@Test
	void test_empty_direction_pacman_object_creation() {
		boolean isException = false;
		try {
		      new PacmanObject(10,10,10,10,"",settings);
		    } catch (Exception e) {
		      isException = true;
		    }
		assertTrue( isException );

	}
	
	@Test
	void test_negative_position_pacman_object_creation() {
		boolean isException = false;
		try {
		      new PacmanObject(-10,-10,10,10,"right",settings);
		    } catch (Exception e) {
		      isException = true;
		    }
		assertTrue( isException );

	}
	
	@Test
	void test_new_direction_pacman_object() {


	}
}
