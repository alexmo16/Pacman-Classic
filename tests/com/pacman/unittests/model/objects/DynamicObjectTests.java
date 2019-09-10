package com.pacman.unittests.model.objects;

import static com.pacman.model.objects.DynamicObject.getSpeed;
import static com.pacman.model.objects.DynamicObject.tunnel;
import static com.pacman.model.objects.DynamicObject.updatePosition;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pacman.model.objects.PacmanObject;
import com.pacman.model.world.Direction;

class DynamicObjectTests
{

    private PacmanObject testPacman;
    private PacmanObject expectedPacman;
    private double speed;
    
    @BeforeEach
    void setSpeed()
    {
        speed = getSpeed();
    }
    
    @Test
    void testUpdatePositionUp()
    {
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP);
        expectedPacman = new PacmanObject(10, 10 - speed, 10, 10, Direction.UP);
        
        updatePosition(testPacman.getRectangle(), Direction.UP);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
        
    }
    
    @Test
    void testUpdatePositionRight()
    {
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP);
        expectedPacman = new PacmanObject(10 + speed, 10, 10, 10, Direction.UP);
        
        updatePosition(testPacman.getRectangle(), Direction.RIGHT);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
        
    }
    
    @Test
    void testUpdatePositionLeft()
    {
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP);
        expectedPacman = new PacmanObject(10 - speed, 10, 10, 10, Direction.UP);
        
        updatePosition(testPacman.getRectangle(), Direction.LEFT);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
        
    }
    
    @Test
    void testUpdatePositionDown()
    {
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP);
        expectedPacman = new PacmanObject(10, 10 + speed, 10, 10, Direction.UP);
        
        updatePosition(testPacman.getRectangle(), Direction.DOWN);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
        
    }
    
    @Test
    void testTunnelUp()
    {
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP);
        expectedPacman = new PacmanObject(10,22.5, 10, 10, Direction.UP);
        
        tunnel(testPacman.getRectangle(), Direction.UP);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
    }
    
    @Test
    void testTunnelDown()
    {
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP);
        expectedPacman = new PacmanObject(10, 0.5, 10, 10, Direction.UP);
        
        tunnel(testPacman.getRectangle(), Direction.DOWN);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
    }
    
    @Test
    void testTunnelRight()
    {
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP);
        expectedPacman = new PacmanObject(0.5, 10, 10, 10, Direction.UP);
        
        tunnel(testPacman.getRectangle(), Direction.RIGHT);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
    }
    
    @Test
    void testTunnelLeft()
    {
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP);
        expectedPacman = new PacmanObject(19.5, 10, 10, 10, Direction.UP);
        
        tunnel(testPacman.getRectangle(), Direction.LEFT);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
    }

}
