package com.pacman.unittests.model.threads;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pacman.controller.GameController;
import com.pacman.model.IGame;
import com.pacman.model.objects.GameObject;
import com.pacman.model.objects.consumables.Energizer;
import com.pacman.model.objects.consumables.PacDot;
import com.pacman.model.threads.RenderThread;
import com.pacman.view.IWindow;

public class RenderThreadTests 
{
	private final int WAIT_TIME = 100;
	private final int JOIN_TIME = 500;
	
	private IGame game = Mockito.mock( IGame.class );
	private IWindow window = Mockito.mock( IWindow.class );
	private GameController gc = GameController.getInstance(window, game);
	private RenderThread thread;
	
	@BeforeEach
	void createThread()
	{
		thread = new RenderThread(game);
		thread.start();
		assert(thread.isAlive());
	}
	
	@AfterEach
	void stopThread() throws InterruptedException, InterruptedByTimeoutException
	{
		if (thread == null || !thread.isAlive()) return;
		synchronized (thread)
		{
			thread.stopThread();
			thread.notifyAll();
			thread.join(JOIN_TIME);
			if(thread.isAlive())
			{
				thread.interrupt();
				throw new InterruptedByTimeoutException();
			}
		}
	}	
	
	@Test
	void testInterruptWhenWaiting() throws InterruptedException
	{	
		synchronized (thread)
		{
			thread.wait(WAIT_TIME);
		}
		
		try
		{
			thread.stopThread();
			thread.join(JOIN_TIME);
			if (thread.isAlive())
			{
				thread.interrupt();
				throw new InterruptedByTimeoutException();
			}
		}
		catch (InterruptedByTimeoutException e)
		{
			assertNotNull(e);
			return;
		}
		fail("Should had an exception.");
	}
	
	@Test 
	void testNoExceptionWhenCleanStop() throws InterruptedException
	{
		synchronized (thread)
		{
			thread.wait(WAIT_TIME);
			
			thread.stopThread();
			synchronized(gc)
			{
				gc.notify();
			}
			thread.join(JOIN_TIME);
			if (thread.isAlive())
			{
				fail("Should not be alive.");
			}
		}
	}
	
	@Test
	void testRunForMoreThenOneFrame() throws InterruptedException
	{
		synchronized (thread)
		{
			thread.wait(2000);
			synchronized(gc)
			{
				gc.notify();
			}
			thread.stopThread();
		}
	}
	
	@Test
	void testUpdateAnimationObjectsWithoutAnimation() throws InterruptedException
	{
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		objects.add(new PacDot(0, 0));
		objects.add(new PacDot(1, 1));
		objects.add(new PacDot(2, 2));
		Mockito.when( game.getGameObjects() ).thenReturn( objects );
		synchronized (thread)
		{
			synchronized(gc)
			{
				gc.notify();
			}
			thread.stopThread();
		}
	}
	
	@Test
	void testUpdateAnimationObjectsWithAnimation() throws InterruptedException
	{
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		objects.add(new Energizer(0, 0));
		objects.add(new Energizer(1, 1));
		objects.add(new Energizer(2, 2));
		Mockito.when( game.getGameObjects() ).thenReturn( objects );
		synchronized (thread)
		{
			synchronized(gc)
			{
				gc.notify();
			}
			thread.stopThread();
		}
	}
	
	@Test
	void testUpdateAnimationWithAnimation() throws InterruptedException
	{
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		objects.add(new Energizer(0, 0));
		Mockito.when( game.getGameObjects() ).thenReturn( objects );
		synchronized (thread)
		{
			synchronized(gc)
			{
				gc.notify();
			}
			thread.wait(1000);
			synchronized(gc)
			{
				gc.notify();
			}
			thread.wait(1000);
			thread.stopThread();
			synchronized(gc)
			{
				gc.notify();
			}
			thread.join(JOIN_TIME);
			if (thread.isAlive())
			{
				fail("Should not be alive.");
			}
		}
	}
}
