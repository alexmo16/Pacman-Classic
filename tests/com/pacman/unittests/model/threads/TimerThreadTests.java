package com.pacman.unittests.model.threads;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.pacman.model.threads.TimerThread;

class TimerThreadTests
{
    private int time;

    @Test
    void testOn3SecondsTimer()
    {
        boolean isException = false;
        int JOIN_TIMER = 500;
        
        this.time = 3;
        Thread th = new TimerThread(time);
        th.start();
        synchronized (th)
        {
            try
            {
                th.wait(time * 1000 + 500);
                th.join(JOIN_TIMER);
            } catch (Exception e)
            {
                isException = true;
            }
        }
        assertFalse(isException);
        
    }
    
    @Test
    void testOnNegativeTimer()
    {
        boolean isException = false;
        int JOIN_TIMER = 500;
        this.time = -1;
        
        Thread th = new TimerThread(time);
        th.start();
        
        synchronized (th)
        {
            try
            {
                th.wait(time * 1000 + 500);
                th.join(JOIN_TIMER);
            } catch (Exception e)
            {
                isException = true;
                e.printStackTrace();
                System.out.println("The timer is negative");
            }
        }
        assertTrue(isException);
    }

}
