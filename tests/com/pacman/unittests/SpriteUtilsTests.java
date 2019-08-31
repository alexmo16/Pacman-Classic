package com.pacman.unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.pacman.utils.SpriteUtils;

class SpriteUtilsTests {

	// Picture is 100 by 100 px
	private String path = System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "testSpritesSheet.png";
	
	@Test
	void test_standard()
	{
		int[][] result = SpriteUtils.getSpritesCoordsFromSheet(path, 10);
		
		assertEquals(result.length, 100);
		
		assertEquals(result[0][0], 0);
		assertEquals(result[0][1], 0);
		assertEquals(result[0][2], 10);
		assertEquals(result[0][3], 10);
		
		assertEquals(result[55][0], 50);
		assertEquals(result[55][1], 50);
		assertEquals(result[55][2], 60);
		assertEquals(result[55][3], 60);
		
		assertEquals(result[99][0], 90);
		assertEquals(result[99][1], 90);
		assertEquals(result[99][2], 100);
		assertEquals(result[99][3], 100);
	}

	@Test
	void test_wrong_file()
	{
	    assertThrows(Error.class, () -> 
	    {
	    	SpriteUtils.getSpritesCoordsFromSheet("/bad/path/test.png", 10);
	    });
	}
	
	@Test
	void test_incompatible_block_size()
	{
	    assertThrows(Error.class, () -> 
	    {
	    	SpriteUtils.getSpritesCoordsFromSheet(path, 9);
	    });
	}
	
}
