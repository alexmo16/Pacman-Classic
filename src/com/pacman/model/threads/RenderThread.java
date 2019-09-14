package com.pacman.model.threads;

import com.pacman.model.IGame;
import com.pacman.model.objects.Animation;
import com.pacman.model.objects.GameObject;

public class RenderThread implements Runnable
{
	private IGame game;
	private Thread thread;
	private int delay = 100;
	
	public RenderThread(IGame g)
	{
		game = g;
	}

	
    private void update() 
    {
    	for (GameObject obj : game.getGameObjects())
    	{
    		if (obj instanceof Animation)
    		{
    			Animation animation = (Animation)obj;
    			animation.nextSprite();
    		}
    	}
    }
    
    public void start()
    {
    	thread = new Thread(this);
    	thread.start();
    }
	
	@Override
	public void run() 
	{
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {

        	update();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = delay - timeDiff;

            if (sleep < 0) 
            {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                String msg = String.format("Thread interrupted: %s", e.getMessage());
            }

            beforeTime = System.currentTimeMillis();
        }
	}
}
