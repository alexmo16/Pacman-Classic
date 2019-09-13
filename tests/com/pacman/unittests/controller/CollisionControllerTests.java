package com.pacman.unittests.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.Rectangle2D;
import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pacman.model.Collision;
import com.pacman.model.objects.GameObject;
import com.pacman.model.world.Level;


public class CollisionControllerTests 
{

	private static int[] auth = {0,30,40};

	@SuppressWarnings("unused")
	private static Level level;
	private final static String LEVEL_DATA_FILE = new String(System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "map.txt"); 
	
	@BeforeAll
	static void generateLevel()
	{
		@SuppressWarnings("unused")
		Level level = new Level(LEVEL_DATA_FILE, "1");
		Collision.setAuthTiles(auth);
	}
	
    @Test
    void test_collisionWall_void() 
    {
    	GameObject obj1 = Mockito.mock(GameObject.class);
    	Rectangle2D.Double rectangle = Mockito.mock(Rectangle2D.Double.class);
    	Mockito.when(obj1.getHitBox()).thenReturn(rectangle);
    	Mockito.when(obj1.getHitBox().getMinX()).thenReturn(0.0);
    	Mockito.when(obj1.getHitBox().getMinY()).thenReturn(0.0);
    	Mockito.when(obj1.getHitBox().getMaxX()).thenReturn(0.9);
    	Mockito.when(obj1.getHitBox().getMaxY()).thenReturn(0.9);
    	assertEquals("void",Collision.collisionWall(obj1));  
	}
    
    @Test
    void test_collisionWall_wall() 
    {
    	GameObject obj1 = Mockito.mock(GameObject.class);
    	Rectangle2D.Double rectangle = Mockito.mock(Rectangle2D.Double.class);
    	Mockito.when(obj1.getHitBox()).thenReturn(rectangle);
    	Mockito.when(obj1.getHitBox().getMinX()).thenReturn(1.5);
    	Mockito.when(obj1.getHitBox().getMinY()).thenReturn(1.5);
    	Mockito.when(obj1.getHitBox().getMaxX()).thenReturn(1.9);
    	Mockito.when(obj1.getHitBox().getMaxY()).thenReturn(1.9);
    	
    	assertEquals("wall",Collision.collisionWall(obj1));
	}
    
    @Test
    void test_collisionWall_path() 
    {
    	GameObject obj1 = Mockito.mock(GameObject.class);
    	Rectangle2D.Double rectangle = Mockito.mock(Rectangle2D.Double.class);
    	Mockito.when(obj1.getHitBox()).thenReturn(rectangle);
    	Mockito.when(obj1.getHitBox().getMinX()).thenReturn(2.2);
    	Mockito.when(obj1.getHitBox().getMinY()).thenReturn(2.2);
    	Mockito.when(obj1.getHitBox().getMaxX()).thenReturn(2.4);
    	Mockito.when(obj1.getHitBox().getMaxY()).thenReturn(2.4);
    	
    	assertEquals("path",Collision.collisionWall(obj1));
	}
}
