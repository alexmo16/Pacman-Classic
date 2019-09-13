package com.pacman.unittests.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.Rectangle2D;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pacman.model.Collision;
import com.pacman.model.objects.GameObject;


public class CollisionControllerTests 
{
	
	int[][] map = {{0,0,40,1},
			{0,1,1,1},
			{0,1,30,0},
			{0,1,30,0}};
	int[] auth = {0,30,40};
/*
	
    @Test
    void test_collisionWall_void() 
    {
    	Collision.setMap(map,auth,4,4);
    	
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
    	Collision.setMap(map,auth,4,4);
    	
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
    	Collision.setMap(map,auth,4,4);
    	
    	GameObject obj1 = Mockito.mock(GameObject.class);
    	Rectangle2D.Double rectangle = Mockito.mock(Rectangle2D.Double.class);
    	Mockito.when(obj1.getHitBox()).thenReturn(rectangle);
    	Mockito.when(obj1.getHitBox().getMinX()).thenReturn(2.2);
    	Mockito.when(obj1.getHitBox().getMinY()).thenReturn(2.2);
    	Mockito.when(obj1.getHitBox().getMaxX()).thenReturn(2.4);
    	Mockito.when(obj1.getHitBox().getMaxY()).thenReturn(2.4);
    	
    	
    	assertEquals("path",Collision.collisionWall(obj1));
    	
       
	}
    
*/
}
