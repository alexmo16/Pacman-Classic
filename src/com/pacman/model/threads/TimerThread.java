package com.pacman.model.threads;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
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
        
        System.out.println("Start: Timer Thread");
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
        System.out.println("Stop: Timer Thread");
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
