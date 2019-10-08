package com.pacman.unittests.model.threads;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.channels.InterruptedByTimeoutException;

import org.junit.jupiter.api.Test;

import com.pacman.model.threads.TimerThread;

class TimerThreadTests
{
    private int time;

    @Test
    void testOn3SecondsTimer()
    {
        boolean isException = false;
        int JOIN_TIMER = 5000;
        
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
                System.out.println("java.lang.IllegalArgumentException : The timer is negative");
            }
        }
        assertTrue(isException);
    }

    @Test
	void testStopThread() throws InterruptedException
	{
    	TimerThread thread = new TimerThread(5);
		synchronized (thread)
		{
			thread.wait(100);
		}
		
		try
		{
			thread.stopThread();
			thread.join(500);
			if (thread.isAlive())
			{
				thread.interrupt();
				throw new InterruptedByTimeoutException();
			}
		}
		catch (InterruptedByTimeoutException e)
		{
			fail("Should not throw an exception.");
		}
	}
}
