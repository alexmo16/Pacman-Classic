package com.pacman.unittests.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.pacman.engine.world.WorldData;

class WorldDataTest {

	final private String good = System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "worldDataTest_good.txt";
	final private String empty = System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "worldDataTest_empty.txt";
	final private String badOverhead = System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "worldDataTest_bad_overhead.txt";
	final private String badRow = System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "worldDataTest_bad_row.txt";
	final private String badCol = System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "worldDataTest_bad_col.txt";

	
	@Test
	void test_good_map() 
	{
		WorldData worldData = new WorldData(good);
		
        assertNotEquals(worldData, null);
        assertEquals(worldData.getHeight(), 3);
        assertEquals(worldData.getWidth(), 4);
        
        int [][] result = worldData.getTiles();
        
        assertEquals(result[0][0], 1);
        assertEquals(result[1][0], 2);
        assertEquals(result[2][0], 3);
        assertEquals(result[3][0], 0);
        
        assertEquals(result[0][1], 4);
        assertEquals(result[1][1], 5);
        assertEquals(result[2][1], 6);
        assertEquals(result[3][1], 11);
        
        assertEquals(result[0][2], 7);
        assertEquals(result[1][2], 8);
        assertEquals(result[2][2], 9);
        assertEquals(result[3][2], 11);
        
        assertEquals(worldData.getTile(0, 0), 1);
	}
	
	@Test
	void test_empty_map() 
	{
		WorldData result = new WorldData(empty);
		
        assertNotEquals(result, null);
        assertEquals(result.getHeight(), 0);
        assertEquals(result.getWidth(), 0);
	}
	
	@Test
	void test_bad_overhead() 
	{
		WorldData result = new WorldData(badOverhead);
		
        assertNotEquals(result, null);
        assertEquals(result.getHeight(), 0);
        assertEquals(result.getWidth(), 0);
	}
	
	@Test
	void test_bad_row() 
	{
		WorldData result = new WorldData(badRow);
		
        assertNotEquals(result, null);
        assertEquals(result.getHeight(), 0);
        assertEquals(result.getWidth(), 0);
	}
	
	@Test
	void test_bad_col() 
	{
		WorldData result = new WorldData(badCol);
		
        assertNotEquals(result, null);
        assertEquals(result.getHeight(), 0);
        assertEquals(result.getWidth(), 0);
	}
	
	@Test
	void test_find_first_instance() 
	{
		WorldData worldData = new WorldData(good);
		
        assertNotEquals(worldData, null);
        
        int[] result = worldData.findFirstInstanceOF(11);
        
        assertEquals(result[0], 3);
        assertEquals(result[1], 1);
	}
	
	@Test
	void test_find_first_instance_not_existing() 
	{
		WorldData worldData = new WorldData(good);
		
        assertNotEquals(worldData, null);
        
        int[] result = worldData.findFirstInstanceOF(20);
        
        assertEquals(result, null);
	}
	
	@Test
	void test_get_tile_out_of_range() 
	{
		WorldData worldData = new WorldData(good);
		
        assertNotEquals(worldData, null);
        
        int result = worldData.getTile(-1, 1);
        assertEquals(result, -1);
        
        result = worldData.getTile(20, 1);
        assertEquals(result, -1);
        
        result = worldData.getTile(1, -3);
        assertEquals(result, -1);
        
        result = worldData.getTile(1, 20);
        assertEquals(result, -1);
	}
}
