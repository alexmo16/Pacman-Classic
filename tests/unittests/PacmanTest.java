/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import inDev.Pacman;

/**
 * @author Alexis Morel
 *
 */
class PacmanTest {

	@Test
	void twoPlusTwoIsFourTest() {
		assertEquals(4, Pacman.twoPlustwoIsFour());
	}
}
