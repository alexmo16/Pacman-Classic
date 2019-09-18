package com.pacman.model.threads;

import com.pacman.controller.GameController;
import com.pacman.model.IGame;
import com.pacman.model.objects.Animation;
import com.pacman.model.objects.GameObject;
import com.pacman.view.IWindow;

public class RenderThread extends Thread
{
	private IGame game;
	private boolean isRunning = false;
	
	public RenderThread(IGame g)
	{
		game = g;
	}

    private void updateAnimation(long timeDiff) 
    {
    	if (game == null) return;
    	
		for (GameObject obj : game.getGameObjects())
		{
    		if (obj instanceof Animation)
    		{
    			Animation animation = (Animation)obj;
    			long t = animation.getTimeSinceLastSpriteUpdate() + timeDiff;
    			if (t >= (1000 / animation.spritePerSecond()))
    			{
    				animation.nextSprite();
    				t = 0;
    			}
    			animation.setTimeSinceLastSpriteUpdate(t);
    		}
		}
    }
	
	@Override
	public void run() 
	{
		isRunning = true;
        long beforeTime, timeDiff;
        int frames = 0;
        long framesTime = 0;
        
        beforeTime = System.currentTimeMillis();
        GameController gc = GameController.getInstance(null, null);
        if (gc == null) return;
        IWindow window = gc.getWindow();
        if (window == null) return;

        while (isRunning) 
        {
        	synchronized (gc)
			{
				try
				{
					gc.wait();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
    		
        	updateAnimation(System.currentTimeMillis() - beforeTime);
        	window.render();
        	frames++;
        	
            timeDiff = System.currentTimeMillis() - beforeTime;
        	
            framesTime += timeDiff;
            
            if (framesTime >= 1000)
            {
            	GameController.setFps(frames);
            	frames = 0;
            	framesTime = 0;
            }
            beforeTime = System.currentTimeMillis();
        }
	}


	public void stopThread() 
	{
		this.isRunning = false;
	}
}
