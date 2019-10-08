package com.pacman.unittests.model.threads;

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
	private final int JOIN_TIME = 5000;
	
	private IGame game = Mockito.mock( IGame.class );
	private IWindow window = Mockito.mock( IWindow.class );
	private GameController gc = new GameController(window, game);
	private RenderThread thread;
	
	@BeforeEach
	void createThread()
	{
		thread = new RenderThread(game, gc);
		thread.start();
		assert(thread.isAlive());
	}
	
	@AfterEach
	void stopThread() throws InterruptedException, InterruptedByTimeoutException
	{
		if (thread == null || !thread.isAlive()) return;
		thread.stopThread();
		thread.join(JOIN_TIME);
		
		if(thread.isAlive())
		{
			thread.interrupt();
			throw new InterruptedByTimeoutException();
		}
	}	
	
	@Test 
	void testNoExceptionWhenCleanStop() throws InterruptedException
	{
		Thread.sleep(WAIT_TIME);
		thread.stopThread();
		thread.join(JOIN_TIME);
		if (thread.isAlive())
		{
			fail("Should not be alive.");
		}
	}
	
	@Test
	void testRunForMoreThenOneFrame() throws InterruptedException
	{
		Thread.sleep(WAIT_TIME);
		thread.stopThread();
	}
	
	@Test
	void testUpdateAnimationObjectsWithoutAnimation() throws InterruptedException
	{
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		objects.add(new PacDot(0, 0));
		objects.add(new PacDot(1, 1));
		objects.add(new PacDot(2, 2));
		Mockito.when( game.getGameObjects() ).thenReturn( objects );
		thread.stopThread();
	}
	
	@Test
	void testUpdateAnimationObjectsWithAnimation() throws InterruptedException
	{
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		objects.add(new Energizer(0, 0));
		objects.add(new Energizer(1, 1));
		objects.add(new Energizer(2, 2));
		Mockito.when( game.getGameObjects() ).thenReturn( objects );
		Thread.sleep(WAIT_TIME);
		thread.stopThread();
	}
	
	@Test
	void testUpdateAnimationWithAnimation() throws InterruptedException
	{
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		objects.add(new Energizer(0, 0));
		Mockito.when( game.getGameObjects() ).thenReturn( objects );
		Thread.sleep(2000);
		thread.stopThread();
		thread.join(JOIN_TIME);
		if (thread.isAlive())
		{
			fail("Should not be alive.");
		}
	}
}
