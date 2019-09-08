package com.pacman.unittests.engine;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Rectangle2D;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pacman.controller.CollisionController;
import com.pacman.model.objects.GameObject;


public class CollisionManagerTests 
{
	
	int[][] map = {{0,0,40,1},
			{0,1,1,1},
			{0,1,30,0},
			{0,1,30,0}};
	int[] auth = {0,30,40};

	
    @Test
    void test_collisionWall_void() 
    {
    	CollisionController.setMap(map,auth,4,4);
    	
    	GameObject obj1 = Mockito.mock(GameObject.class);
    	Rectangle2D.Double rectangle = Mockito.mock(Rectangle2D.Double.class);
    	Mockito.when(obj1.getRectangle()).thenReturn(rectangle);
    	Mockito.when(obj1.getRectangle().getMinX()).thenReturn(0.0);
    	Mockito.when(obj1.getRectangle().getMinY()).thenReturn(0.0);
    	Mockito.when(obj1.getRectangle().getMaxX()).thenReturn(0.9);
    	Mockito.when(obj1.getRectangle().getMaxY()).thenReturn(0.9);
    	assertEquals("void",CollisionController.collisionWall(obj1));
    	
       
	}
    
    @Test
    void test_collisionWall_wall() 
    {
    	CollisionController.setMap(map,auth,4,4);
    	
    	GameObject obj1 = Mockito.mock(GameObject.class);
    	Rectangle2D.Double rectangle = Mockito.mock(Rectangle2D.Double.class);
    	Mockito.when(obj1.getRectangle()).thenReturn(rectangle);
    	Mockito.when(obj1.getRectangle().getMinX()).thenReturn(1.5);
    	Mockito.when(obj1.getRectangle().getMinY()).thenReturn(1.5);
    	Mockito.when(obj1.getRectangle().getMaxX()).thenReturn(1.9);
    	Mockito.when(obj1.getRectangle().getMaxY()).thenReturn(1.9);
    	
    	
    	assertEquals("wall",CollisionController.collisionWall(obj1));
    	
       
	}
    
    @Test
    void test_collisionWall_path() 
    {
    	CollisionController.setMap(map,auth,4,4);
    	
    	GameObject obj1 = Mockito.mock(GameObject.class);
    	Rectangle2D.Double rectangle = Mockito.mock(Rectangle2D.Double.class);
    	Mockito.when(obj1.getRectangle()).thenReturn(rectangle);
    	Mockito.when(obj1.getRectangle().getMinX()).thenReturn(2.2);
    	Mockito.when(obj1.getRectangle().getMinY()).thenReturn(2.2);
    	Mockito.when(obj1.getRectangle().getMaxX()).thenReturn(2.4);
    	Mockito.when(obj1.getRectangle().getMaxY()).thenReturn(2.4);
    	
    	
    	assertEquals("path",CollisionController.collisionWall(obj1));
    	
       
	}
    

}
