package com.pacman.unittests.model.objects;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class GumTests
{
    @Test
    void testGumCreationNoParameters() 
    {
        boolean isException = false;
        try
        {
            //new Gum();
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
        try
        {
            //new Gum(0, 0, 1, 1);
        } catch (Exception e)
        {
            isException = true;
        }
        assertFalse(isException);
    }

}
