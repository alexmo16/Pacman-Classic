package com.pacman.model.threads;

import com.pacman.utils.Lambda;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class TimerThread extends Thread
{
    private final int SLEEP_TIMER = 100;
    private int timeInMs;                           /* Time the timer should be running */
    private volatile boolean isRunning = false;     /* State of  */
    private Boolean isPause = false;
    private volatile long timerCount;
    private Lambda endCallback = () -> {};
    private int specialCallbackTime;
    private Lambda specialCallback = () -> {};

    public TimerThread(int timer)
    {
        this.timeInMs = timer * 1000;
    }

    private boolean verifStop()
    {
        return isRunning && timerCount <= timeInMs;
    }
    
    public void stopThread()
    {
        this.isRunning = false;
    }
    
    public void setPause(boolean b)
    {
    	synchronized (isPause) 
    	{
    		isPause = b;
		}
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
                
                synchronized (isPause)
                {
					if (isPause) continue;
				}
                
                synchronized (this)
				{
                	incrementTime(System.currentTimeMillis() - counterStart);
                	if (timerCount >= specialCallbackTime)
                	{
                		executeSpecialCallback();
                	}
				}
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        synchronized (endCallback)
        {
        	endCallback.run();
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
    
    public void setEndCallback(Lambda lambda)
	{
    	synchronized (endCallback)
		{
    		endCallback = lambda;
		}
	}

    /**
     * Lambda to execute when the timer has hit a specific time.
     * @param time - ms
     * @param lambda - lambda to execute at the specified time.
     */
	public void setCallbackAtTime(int time, Lambda lambda)
	{
		specialCallbackTime = time;
		specialCallback = lambda;
	}
	
	/**
	 * Lambda to execute when the timer is done.
	 */
	private void executeSpecialCallback()
	{
		synchronized (specialCallback)
		{
			specialCallback.run();
		}
	}
}
