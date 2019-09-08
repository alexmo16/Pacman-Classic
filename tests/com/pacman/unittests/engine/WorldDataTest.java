package com.pacman.unittests.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.pacman.model.world.Data;

class WorldDataTest
{

	final private String good = System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "worldDataTest_good.txt";
	final private String empty = System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "worldDataTest_empty.txt";
	final private String badOverhead = System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "worldDataTest_bad_overhead.txt";
	final private String badRow = System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "worldDataTest_bad_row.txt";
	final private String badCol = System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "worldDataTest_bad_col.txt";

	
	@Test
	void test_good_map() 
	{
		Data worldData = new Data(good);
		
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
		Data result = new Data(empty);
		
        assertNotEquals(result, null);
        assertEquals(result.getHeight(), 0);
        assertEquals(result.getWidth(), 0);
	}
	
	@Test
	void test_bad_overhead() 
	{
		Data result = new Data(badOverhead);
		
        assertNotEquals(result, null);
        assertEquals(result.getHeight(), 0);
        assertEquals(result.getWidth(), 0);
	}
	
	@Test
	void test_bad_row() 
	{
		Data result = new Data(badRow);
		
        assertNotEquals(result, null);
        assertEquals(result.getHeight(), 0);
        assertEquals(result.getWidth(), 0);
	}
	
	@Test
	void test_bad_col() 
	{
		Data result = new Data(badCol);
		
        assertNotEquals(result, null);
        assertEquals(result.getHeight(), 0);
        assertEquals(result.getWidth(), 0);
	}
	
	@Test
	void test_find_first_instance() 
	{
		Data worldData = new Data(good);
		
        assertNotEquals(worldData, null);
        
        int[] result = worldData.findFirstInstanceOF(11);
        
        assertEquals(result[0], 3);
        assertEquals(result[1], 1);
	}
	
	@Test
	void test_find_first_instance_not_existing() 
	{
		Data worldData = new Data(good);
		
        assertNotEquals(worldData, null);
        
        int[] result = worldData.findFirstInstanceOF(20);
        
        assertEquals(result, null);
	}
	
	@Test
	void test_get_tile_out_of_range() 
	{
		Data worldData = new Data(good);
		
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
