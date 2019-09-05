package com.pacman.unittests.game.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.pacman.game.objects.PauseScreen;

public class PauseScreenTests {
	PauseScreen test = new PauseScreen("test");
	PauseScreen test2 = new PauseScreen("test");

	@Test
	void test_pause_screen_creation() {
		boolean isException = false;
		try {
		      new PauseScreen("pause");
		    } catch (Exception e) {
		      isException = true;
		    }
		assertFalse( isException );

	}
	
	@Test
	void test_null_value_pause_screen_creation() {
		boolean isException = false;
		try {
		      new PauseScreen(null);
		    } catch (Exception e) {
		      isException = true;
		    }
		assertFalse( isException );

	}
	
	@Test
	void test_toggle_pause_pane() {
		test.togglePausePane();

		assertNotEquals( test, test2 );

	}
	
	@Test
	void test_double_toggle_pause_pane() {
		test.togglePausePane();
		test.togglePausePane();

		assertNotEquals( test, test2 );

	}
}
