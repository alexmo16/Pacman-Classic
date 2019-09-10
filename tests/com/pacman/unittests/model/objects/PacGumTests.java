package com.pacman.unittests.model.objects;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.pacman.model.objects.PacGum;

class PacGumTests
{
    @Test
    void testPacGumCreationNoParameters()
    {
        boolean isException = false;
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
        try
        {
            new PacGum(0, 0, 1, 1);
        } catch (Exception e)
        {
            isException = true;
        }
        assertFalse(isException);
    }

}
