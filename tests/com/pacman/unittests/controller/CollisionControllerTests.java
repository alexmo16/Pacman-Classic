package com.pacman.unittests.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.Rectangle2D;
import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pacman.model.Collision;
import com.pacman.model.Game;
import com.pacman.model.objects.GameObject;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.world.Level;


public class CollisionControllerTests 
{

	private static int[] auth = {0,30,40};
	private static Collision collision;

	@SuppressWarnings("unused")
	private static Level level;
	private final static String LEVEL_DATA_FILE = new String(System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "map.txt"); 
	
	
	@BeforeAll
	static void generateLevel()
	{
		@SuppressWarnings("unused")
		Level level = new Level(LEVEL_DATA_FILE, "1");
		Game game = Mockito.mock(Game.class);
		collision = new Collision(game);
		collision.setAuthTiles(auth);
	}
	
    @Test
    void test_collisionWall_void() 
    {
    	Entity obj1 = Mockito.mock(Entity.class);
    	Rectangle2D.Double rectangle = Mockito.mock(Rectangle2D.Double.class);
    	Mockito.when(obj1.getHitBox()).thenReturn(rectangle);
    	Mockito.when(obj1.getHitBox().getMinX()).thenReturn(0.0);
    	Mockito.when(obj1.getHitBox().getMinY()).thenReturn(0.0);
    	Mockito.when(obj1.getHitBox().getMaxX()).thenReturn(0.9);
    	Mockito.when(obj1.getHitBox().getMaxY()).thenReturn(0.9);
    	assertEquals("void",collision.collisionWall(obj1));  
	}
    
    @Test
    void test_collisionWall_wall() 
    {
    	Entity obj1 = Mockito.mock(Entity.class);
    	Rectangle2D.Double rectangle = Mockito.mock(Rectangle2D.Double.class);
    	Mockito.when(obj1.getHitBox()).thenReturn(rectangle);
    	Mockito.when(obj1.getHitBox().getMinX()).thenReturn(1.5);
    	Mockito.when(obj1.getHitBox().getMinY()).thenReturn(1.5);
    	Mockito.when(obj1.getHitBox().getMaxX()).thenReturn(1.9);
    	Mockito.when(obj1.getHitBox().getMaxY()).thenReturn(1.9);
    	
    	assertEquals("wall",collision.collisionWall(obj1));
	}
    
    @Test
    void test_collisionWall_path() 
    {
    	Entity obj1 = Mockito.mock(Entity.class);
    	Rectangle2D.Double rectangle = Mockito.mock(Rectangle2D.Double.class);
    	Mockito.when(obj1.getHitBox()).thenReturn(rectangle);
    	Mockito.when(obj1.getHitBox().getMinX()).thenReturn(2.2);
    	Mockito.when(obj1.getHitBox().getMinY()).thenReturn(2.2);
    	Mockito.when(obj1.getHitBox().getMaxX()).thenReturn(2.4);
    	Mockito.when(obj1.getHitBox().getMaxY()).thenReturn(2.4);
    	
    	assertEquals("path",collision.collisionWall(obj1));
	}

}
