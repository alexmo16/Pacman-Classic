package com.pacman.unittests.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.pacman.utils.TimerThread;

class TimerThreadTests
{
    private int time;

    @Test
    void test()
    {
        boolean isException = false;
        int JOIN_TIMER = 500;
        
        this.time = 3;
        Thread th = new Thread(new TimerThread(time));
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

}
