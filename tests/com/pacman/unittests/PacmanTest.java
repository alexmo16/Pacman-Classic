/**
 * 
 */
package com.pacman.unittests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.pacman.inDev.ThisIsATest;

/**
 * @author Alexis Morel
 *
 */
class PacmanTest {

	@Test
	void twoPlusTwoIsFourTest() {
		assertEquals(4, ThisIsATest.twoPlustwoIsFour());
	}
}
