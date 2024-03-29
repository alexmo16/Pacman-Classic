package com.pacman.model.threads;

import com.pacman.controller.GameController;
import com.pacman.model.IGame;
import com.pacman.model.objects.Animation;
import com.pacman.model.objects.GameObject;
import com.pacman.utils.Settings;
import com.pacman.view.IWindow;

/**
 * RENDERTHREAD  = CREATED,
 * CREATED      = (start           							  ->RUNNING),
 * RUNNING      = (wait           							  ->WAITING
 *                |stop              						  ->TERMINATED),
 * PROCESSFRAMES = (updateAnimations->render->updateFrameTime ->RUNNING),
 * WAITING      = (notify->PROCESSFRAMES),
 * TERMINATED   = STOP.
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class RenderThread extends Thread
{
	private IGame game;
	private GameController gameController;
	private boolean isRunning = false;
	private long waitTime;
	
	public RenderThread(IGame g, GameController gc)
	{
		game = g;
		gameController = gc;
		waitTime = (long) (Settings.UPDATE_RATE * 0.9 * 1000);
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
        if (gameController == null) return;
        
        IWindow window;
        if ((window = gameController.getWindow()) == null) return;

        long beforeTime = System.currentTimeMillis(), 
        	 timeDiff = 0, 
        	 framesTime = 0;
        
        int frames = 0;
		
        isRunning = true;
        System.out.println("Start: Render Thread");
        while (isRunning) 
        {
			try
			{
				synchronized (this) 
				{
					this.wait(waitTime);
				}
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
        	if (!gameController.getTimeToRender())
            {
           		continue;
            }
        	
        	updateAnimation(System.currentTimeMillis() - beforeTime);
        	window.render();
        	
        	// updateFrameTime 
        	frames++;
        	
            timeDiff = System.currentTimeMillis() - beforeTime;
        	
            framesTime += timeDiff;
            
            if (framesTime >= 1000)
            {
            	gameController.setFps(frames);
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
