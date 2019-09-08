package com.pacman.unittests.game.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import com.pacman.model.Settings;
import com.pacman.model.objects.ScoreBar;

class ScoreBarTests
{
    private ScoreBar scoreBar;
    private Settings s;

    @Test
    void testAddPointScore()
    {
        this.s = mock(Settings.class);
        this.scoreBar = new ScoreBar(s);
        
        scoreBar.addPointsScore(10);
        
        assertEquals(10, scoreBar.getScore());
    }
    
    @Test
    void testSetScore()
    {
        this.s = mock(Settings.class);
        this.scoreBar = new ScoreBar(s);
        
        scoreBar.setScore(100);
        
        assertEquals(100, scoreBar.getScore());
    }

}
