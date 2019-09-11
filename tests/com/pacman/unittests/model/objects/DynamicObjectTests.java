package com.pacman.unittests.model.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pacman.model.objects.entities.Pacman;
import com.pacman.model.world.Direction;

class DynamicObjectTests
{

    private Pacman testPacman;
    private Pacman expectedPacman;
    private double speed;
    
    @BeforeEach
    void setSpeed()
    {
        speed = 0.1;
    }
    
    @Test
    void testUpdatePositionUp()
    {
        testPacman = new Pacman(10, 10);
        expectedPacman = new Pacman(10, 10 - speed);
        
        testPacman.updatePosition(Direction.UP);
        
        assertEquals(expectedPacman.getHitBox().getX(), testPacman.getHitBox().getX());
        assertEquals(expectedPacman.getHitBox().getY(), testPacman.getHitBox().getY());
        
    }
    
    @Test
    void testUpdatePositionRight()
    {
        testPacman = new Pacman(10, 10);
        expectedPacman = new Pacman(10 + speed, 10);
        
        testPacman.updatePosition(Direction.RIGHT);
        
        assertEquals(expectedPacman.getHitBox().getX(), testPacman.getHitBox().getX());
        assertEquals(expectedPacman.getHitBox().getY(), testPacman.getHitBox().getY());
        
    }
    
    @Test
    void testUpdatePositionLeft()
    {
        testPacman = new Pacman(10, 10);
        expectedPacman = new Pacman(10 - speed, 10);
        
        testPacman.updatePosition(Direction.LEFT);
        
        assertEquals(expectedPacman.getHitBox().getX(), testPacman.getHitBox().getX());
        assertEquals(expectedPacman.getHitBox().getY(), testPacman.getHitBox().getY());
        
    }
    
    @Test
    void testUpdatePositionDown()
    {
        testPacman = new Pacman(10, 10);
        expectedPacman = new Pacman(10, 10 + speed);
        
        testPacman.updatePosition(Direction.DOWN);
        
        assertEquals(expectedPacman.getHitBox().getX(), testPacman.getHitBox().getX());
        assertEquals(expectedPacman.getHitBox().getY(), testPacman.getHitBox().getY());
        
    }
    
    @Test
    void testTunnelUp()
    {
        testPacman = new Pacman(10, 10);
        expectedPacman = new Pacman(10,31.6);
        
        testPacman.tunnel(Direction.UP);
        
        assertEquals(expectedPacman.getHitBox().getX(), testPacman.getHitBox().getX());
        assertEquals(expectedPacman.getHitBox().getY(), testPacman.getHitBox().getY());
    }
    
    @Test
    void testTunnelDown()
    {
        testPacman = new Pacman(10, 10);
        expectedPacman = new Pacman(10, 0.5);
        
        testPacman.tunnel(Direction.DOWN);
        
        assertEquals(expectedPacman.getHitBox().getX(), testPacman.getHitBox().getX());
        assertEquals(expectedPacman.getHitBox().getY(), testPacman.getHitBox().getY());
    }
    
    @Test
    void testTunnelRight()
    {
        testPacman = new Pacman(10, 10);
        expectedPacman = new Pacman(0.5, 10);
        
        testPacman.tunnel(Direction.RIGHT);
        
        assertEquals(expectedPacman.getHitBox().getX(), testPacman.getHitBox().getX());
        assertEquals(expectedPacman.getHitBox().getY(), testPacman.getHitBox().getY());
    }
    
    @Test
    void testTunnelLeft()
    {
        testPacman = new Pacman(10, 10);
        expectedPacman = new Pacman(28.6, 10);
        
        testPacman.tunnel(Direction.LEFT);
        
        assertEquals(expectedPacman.getHitBox().getX(), testPacman.getHitBox().getX());
        assertEquals(expectedPacman.getHitBox().getY(), testPacman.getHitBox().getY());
    }

}
