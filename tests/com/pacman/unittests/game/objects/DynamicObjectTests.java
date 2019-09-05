package com.pacman.unittests.game.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pacman.game.Settings;
import static com.pacman.game.objects.DynamicObject.*;
import com.pacman.game.objects.PacmanObject;

class DynamicObjectTests
{

    private PacmanObject testPacman;
    private PacmanObject expectedPacman;
    private Settings s;
    private double speed;
    
    @BeforeEach
    void setSpeed() {
        speed = getSpeed();
    }
    
    @Test
    void testUpdatePositionUp()
    {
        s = new Settings();
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP, s);
        expectedPacman = new PacmanObject(10, 10 - speed, 10, 10, Direction.UP, s);
        
        updatePosition(testPacman.getRectangle(), Direction.UP);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
        
    }
    
    @Test
    void testUpdatePositionRight()
    {
        s = new Settings();
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP, s);
        expectedPacman = new PacmanObject(10 + speed, 10, 10, 10, Direction.UP, s);
        
        updatePosition(testPacman.getRectangle(), Direction.RIGHT);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
        
    }
    
    @Test
    void testUpdatePositionLeft()
    {
        s = new Settings();
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP, s);
        expectedPacman = new PacmanObject(10 - speed, 10, 10, 10, Direction.UP, s);
        
        updatePosition(testPacman.getRectangle(), Direction.LEFT);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
        
    }
    
    @Test
    void testUpdatePositionDown()
    {
        s = new Settings();
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP, s);
        expectedPacman = new PacmanObject(10, 10 + speed, 10, 10, Direction.UP, s);
        
        updatePosition(testPacman.getRectangle(), Direction.DOWN);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
        
    }
    
    @Test
    void testTunnelUp()
    {
        s = new Settings();
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP, s);
        expectedPacman = new PacmanObject(10,22.5, 10, 10, Direction.UP, s);
        s.getWorldData();
        
        tunnel(testPacman.getRectangle(), Direction.UP);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
    }
    
    @Test
    void testTunnelDown()
    {
        s = new Settings();
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP, s);
        expectedPacman = new PacmanObject(10, 0.5, 10, 10, Direction.UP, s);
        
        tunnel(testPacman.getRectangle(), Direction.DOWN);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
    }
    
    @Test
    void testTunnelRight()
    {
        s = new Settings();
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP, s);
        expectedPacman = new PacmanObject(0.5, 10, 10, 10, Direction.UP, s);
        
        tunnel(testPacman.getRectangle(), Direction.RIGHT);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
    }
    
    @Test
    void testTunnelLeft()
    {
        s = new Settings();
        testPacman = new PacmanObject(10, 10, 10, 10, Direction.UP, s);
        expectedPacman = new PacmanObject(19.5, 10, 10, 10, Direction.UP, s);
        
        tunnel(testPacman.getRectangle(), Direction.LEFT);
        
        assertEquals(expectedPacman.getRectangle().getX(), testPacman.getRectangle().getX());
        assertEquals(expectedPacman.getRectangle().getY(), testPacman.getRectangle().getY());
    }

}
