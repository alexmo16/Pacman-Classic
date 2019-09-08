package com.pacman.unittests.game.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.pacman.model.Settings;
import com.pacman.model.objects.PacGum;

class PacGumTests
{

    private Settings settings;
    @Test
    void testPacGumCreationNoParameters()
    {
        boolean isException = false;
        this.settings = new Settings();
        try
        {
            new PacGum();
        } catch (Exception e)
        {
            isException = true;
        }
        assertFalse(isException);
    }
    
    @Test
    void testPacGumCreationWithParameters()
    {
        boolean isException = false;
        this.settings = new Settings();
        try
        {
            new PacGum(0, 0, 1, 1, settings);
        } catch (Exception e)
        {
            isException = true;
        }
        assertFalse(isException);
    }

}
