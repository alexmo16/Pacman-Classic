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
    	if (game == null || game.getGameObjects() == null) return;
    	
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
        GameController gc;
        if ((gc = GameController.getInstance(null, null)) == null) return;
        
        IWindow window;
        if ((window = gc.getWindow()) == null) return;

        long beforeTime = System.currentTimeMillis(), 
        	 timeDiff = 0, 
        	 framesTime = 0;
        
        int frames = 0;
		
        isRunning = true;
        System.out.println("Start: Render Thread");
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
        System.out.println("Stop: Render Thread");
	}


	public void stopThread() 
	{
		this.isRunning = false;
	}
}
