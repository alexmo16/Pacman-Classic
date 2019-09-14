package com.pacman.model.threads;

public class TimerThread implements Runnable
{
    private final int SLEEP_TIMER = 10;
    private int timeInMs;                           /* Time the timer should be running */
    private volatile boolean isRunning;     /* State of  */
    private long timerCount;

    public TimerThread(int timer)
    {
        this.timeInMs = timer * 1000;
        this.isRunning = false;
    }

    private boolean verifStop()
    {
        return isRunning && timerCount <= timeInMs;
    }

    
    /**
     * Private method <br/>
     * Show the time enlapsed since the timer has started<br/>
     * Format mm : ss
     * @param timerCount Time enlapsed in milliseconds
     * */
    private void showTime(int timerCount)
    {
        int minutes = timerCount / 60;
        int seconds = timerCount % 60;

        String sMinutes = String.valueOf(minutes);
        String sSeconds = String.valueOf(seconds);

        if (seconds < 10)
        {
            sSeconds = "0" + sSeconds;
        }

        System.out.println(sMinutes + " : " + sSeconds);
    }
    
    

    @Override
    public void run()
    {
        this.isRunning = true;
        this.timerCount = 0;
        int currentTime = 0;
        int previousTime = 0;

        System.out.println("Start timer thread");

        while (verifStop())
        {
            try
            {
                long counterStart = System.currentTimeMillis();
                Thread.sleep(SLEEP_TIMER);

                timerCount += System.currentTimeMillis() - counterStart;

                currentTime = (int) timerCount / 1000;
                if (currentTime > previousTime)
                {
                    previousTime = currentTime;
                    showTime(currentTime);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("Stop timer thread");

    }

}
