package com.pacman.unittests.model.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.pacman.model.objects.ScoreBar;

class ScoreBarTests
{
    private ScoreBar scoreBar;

    @Test
    void testAddPointScore()
    {
        this.scoreBar = new ScoreBar();
        
        scoreBar.addPointsScore(10);
        
        assertEquals(10, scoreBar.getScore());
    }
    
    @Test
    void testSetScore()
    {
        this.scoreBar = new ScoreBar();
        
        scoreBar.setScore(100);
        
        assertEquals(100, scoreBar.getScore());
    }

}
