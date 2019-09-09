package com.pacman.unittests.model.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.pacman.model.Settings;
import com.pacman.model.objects.Gum;

class GumTests
{

    private Settings settings;

    @Test
    void testGumCreationNoParameters() 
    {
        boolean isException = false;
        this.settings = new Settings();
        try
        {
            new Gum();
        } catch (Exception e)
        {
            isException = true;
        }
        assertFalse(isException);
    }

    @Test
    void testGumCreationWithParameters()
    {
        boolean isException = false;
        this.settings = new Settings();
        try
        {
            new Gum(0, 0, 1, 1, settings);
        } catch (Exception e)
        {
            isException = true;
        }
        assertFalse(isException);
    }

}
