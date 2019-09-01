package com.pacman.unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Image;
import java.io.File;

import org.junit.jupiter.api.Test;

import com.pacman.utils.SpriteUtils;

class SpriteUtilsTests {

	// Picture is 100 by 100 px
	private String path = System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "testSpritesSheet.png";

	@Test
	void test_wrong_file()
	{
		Image result = SpriteUtils.getSpritesSheetImage("/bad/path/test.png");
		assertEquals(result, null);
	}
	
	@Test
	void test_standard()
	{
		Image image = SpriteUtils.getSpritesSheetImage(path);
		int[][] result = SpriteUtils.getSpritesCoordsFromSheet(image, 10);
		
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
	void test_incompatible_block_size()
	{
		Image image = SpriteUtils.getSpritesSheetImage(path);
    	int[][] result = SpriteUtils.getSpritesCoordsFromSheet(image, 9);
    	assertEquals(result, null);
	}
}
