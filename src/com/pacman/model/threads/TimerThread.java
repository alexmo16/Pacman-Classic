package com.pacman.model.threads;

public class TimerThread extends Thread
{
    private final int SLEEP_TIMER = 100;
    private int timeInMs;                           /* Time the timer should be running */
    private volatile boolean isRunning;     /* State of  */
    private volatile long timerCount;

    public TimerThread(int timer)
    {
        this.timeInMs = timer * 1000;
        this.isRunning = false;
    }

    private boolean verifStop()
    {
        return isRunning && timerCount <= timeInMs;
    }
    
    public void stopThread()
    {
        this.isRunning = false;
    }

    @Override
    public void run()
    {
        this.isRunning = true;
        this.timerCount = 0;

        while (verifStop())
        {
            try
            {
                long counterStart = System.currentTimeMillis();
                sleep(SLEEP_TIMER);
                
                synchronized (this)
				{
                	incrementTime(System.currentTimeMillis() - counterStart);
				}
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public synchronized long getTime()
    {
    	return timerCount;
    }
    
    public synchronized void incrementTime(long time)
    {
    	timerCount += time;
    }
}
