package com.pacman.unittests.model.threads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.pacman.model.Game;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.objects.entities.Pacman;
import com.pacman.model.objects.entities.behaviours.BehaviourFactory;
import com.pacman.model.objects.entities.behaviours.IBehaviour;
import com.pacman.model.objects.entities.behaviours.IBehaviour.behavioursID;
import com.pacman.model.threads.PhysicsThread;
import com.pacman.model.world.Direction;
import com.pacman.model.world.GhostType;
import com.pacman.model.world.Level;


public class PhysicsThreadTests 
{

	private static int[] auth = {0,30,40};
	private static int[] authGhost = {30,61,62,63,64};
	private static PhysicsThread collision;

	@SuppressWarnings("unused")
	private static Level level;
	private final static String LEVEL_DATA_FILE = new String(System.getProperty("user.dir") + File.separator + "tests" + File.separator + "testAssets" + File.separator + "map.txt"); 
	private static Game game;
	
	private static BehaviourFactory ghostBehaviourFactory = new BehaviourFactory();
	
	@BeforeEach
	void generateLevel()
	{
		
		level = new Level(LEVEL_DATA_FILE, "1");
		game = Mockito.mock(Game.class);
		collision = new PhysicsThread(game);
		collision.setAuthTiles(auth);
	}
	
	@AfterEach
	void closeThread() throws InterruptedException, InterruptedByTimeoutException
	{
		if (collision == null || !collision.isAlive())
		{
		    return;
		}
		synchronized (collision)
        {
            collision.stopThread();
            collision.notifyAll();
            collision.join(5000);
            if (collision.isAlive())
            {
                collision.interrupt();
                throw new InterruptedByTimeoutException();
            }
        }
	}
	
    /*@Test
    void test_collisionWall_void() 
    {
    	Entity obj1 = Mockito.mock(Entity.class);
    	Rectangle2D.Double rectangle = Mockito.mock(Rectangle2D.Double.class);
    	Mockito.when(obj1.getHitBox()).thenReturn(rectangle);
    	Mockito.when(obj1.getHitBox().getMinX()).thenReturn(0.0);
    	Mockito.when(obj1.getHitBox().getMinY()).thenReturn(0.0);
    	Mockito.when(obj1.getHitBox().getMaxX()).thenReturn(0.9);
    	Mockito.when(obj1.getHitBox().getMaxY()).thenReturn(0.9);
    	//assertEquals("void",collision.collisionWall(obj1));  
	}
    
    @Test
    void test_collisionWall_wall() 
    {
    	Entity obj1 = Mockito.mock(Entity.class);
    	Rectangle2D.Double rectangle = Mockito.mock(Rectangle2D.Double.class);
    	Mockito.when(obj1.getHitBox()).thenReturn(rectangle);
    	Mockito.when(obj1.getHitBox().getMinX()).thenReturn(1.5);
    	Mockito.when(obj1.getHitBox().getMinY()).thenReturn(1.5);
    	Mockito.when(obj1.getHitBox().getMaxX()).thenReturn(1.9);
    	Mockito.when(obj1.getHitBox().getMaxY()).thenReturn(1.9);
    	
    	//assertEquals("wall",collision.collisionWall(obj1));
	}
    
    @Test
    void test_collisionWall_path() 
    {
    	Entity obj1 = Mockito.mock(Entity.class);
    	Rectangle2D.Double rectangle = Mockito.mock(Rectangle2D.Double.class);
    	Mockito.when(obj1.getHitBox()).thenReturn(rectangle);
    	Mockito.when(obj1.getHitBox().getMinX()).thenReturn(2.2);
    	Mockito.when(obj1.getHitBox().getMinY()).thenReturn(2.2);
    	Mockito.when(obj1.getHitBox().getMaxX()).thenReturn(2.4);
    	Mockito.when(obj1.getHitBox().getMaxY()).thenReturn(2.4);
    	
    	//assertEquals("path",collision.collisionWall(obj1));
	}*/
	
	@Test
	void test_executeWallStrategy_void()
	{
		/****middleOfATiles()****/
		Pacman pacman = new Pacman(1.0,15.0);
		Pacman pacmanNewDirection = new Pacman(0.0,15.0); 
		Pacman pacmanNextTiles = new Pacman(0.0,15.0);
		
		Mockito.when(game.getPacman()).thenReturn(pacman);
		
		/****collisionWall(game.getNewDirectionPacman());****/
		
		Mockito.when(game.getNewDirectionPacman()).thenReturn(pacmanNewDirection);
		Mockito.when(game.getNextTilesPacman()).thenReturn(pacmanNextTiles);
		Mockito.when(game.getNewDirection()).thenReturn(Direction.LEFT);
		Mockito.when(game.getNextTilesDirection()).thenReturn(Direction.LEFT);
		collision.executeWallStrategy();
		assertEquals(1.05, pacman.getHitBoxX());
		assertEquals(15.05, pacman.getHitBoxY());
	}
    
	@Test
	void test_executeWallStrategy_wall()
	{
		/****middleOfATiles()****/
		Pacman pacman = new Pacman(2.0,2.0);
		Pacman pacmanNewDirection = new Pacman(1.9,2.0); 
		Pacman pacmanNextTiles = new Pacman(1.9,2.0);
		
		Mockito.when(game.getPacman()).thenReturn(pacman);
		
		/****collisionWall(game.getNewDirectionPacman());****/
		
		Mockito.when(game.getNewDirectionPacman()).thenReturn(pacmanNewDirection);
		Mockito.when(game.getNextTilesPacman()).thenReturn(pacmanNextTiles);
		collision.executeWallStrategy();
		assertEquals(2.05, pacman.getHitBoxX());
		assertEquals(2.05, pacman.getHitBoxY());

		pacman = new Pacman(1.0,15.0);
		pacmanNewDirection = new Pacman(1.0,14.9); 
		pacmanNextTiles = new Pacman(0.0,15.0);
		Mockito.when(game.getPacman()).thenReturn(pacman);
		Mockito.when(game.getNextTilesPacman()).thenReturn(pacmanNextTiles);
		Mockito.when(game.getNewDirectionPacman()).thenReturn(pacmanNewDirection);
		Mockito.when(game.getNextTilesDirection()).thenReturn(Direction.LEFT);
		collision.executeWallStrategy();

		assertEquals(1.05, pacman.getHitBoxX());
		assertEquals(15.05, pacman.getHitBoxY());
		
		pacman = new Pacman(2.0,2.0);
		pacmanNewDirection = new Pacman(1.9,2.0);
		pacmanNextTiles = new Pacman(2.0,2.1);
		Mockito.when(game.getPacman()).thenReturn(pacman);
		Mockito.when(game.getNextTilesPacman()).thenReturn(pacmanNextTiles);
		Mockito.when(game.getNewDirectionPacman()).thenReturn(pacmanNewDirection);
		Mockito.when(game.getNewDirection()).thenReturn(Direction.RIGHT);
		Mockito.when(game.getNextTilesDirection()).thenReturn(Direction.RIGHT);
		collision.executeWallStrategy();
		assertEquals(2.05, pacman.getHitBoxX());
		assertEquals(2.05, pacman.getHitBoxY());	
	}
	
	@Test
	void test_executeWallStrategy_path()
	{
		/****middleOfATiles()****/
		Pacman pacman = new Pacman(2.0,2.0);
		Pacman pacmanNewDirection = new Pacman(2.2,2.0); 
		Pacman pacmanNextTiles = new Pacman(2.2,2.0);
		
		Mockito.when(game.getPacman()).thenReturn(pacman);
		
		Mockito.when(game.getNewDirectionPacman()).thenReturn(pacmanNewDirection);
		Mockito.when(game.getNextTilesPacman()).thenReturn(pacmanNextTiles);
		pacmanNewDirection.setDirection(Direction.RIGHT);
		pacmanNextTiles.setDirection(Direction.RIGHT);
		collision.executeWallStrategy();
		assertEquals(2.05, pacman.getHitBoxX());
		assertEquals(2.05, pacman.getHitBoxY());

		
	}
	
	
	@Test
    void test_CollisionGhost_false()
    {
    	Ghost ghost = Mockito.mock(Ghost.class);
    	Pacman pacman = Mockito.mock(Pacman.class);
    	Rectangle2D.Double hitbox = Mockito.mock(Rectangle2D.Double.class);
    	ArrayList<Entity> entities = new ArrayList<Entity>();
    	entities.add(ghost);
    	entities.add(ghost);
    	
    	
    	Mockito.when(game.getPacman()).thenReturn(pacman);
    	Mockito.when(pacman.getHitBox()).thenReturn(hitbox);
    	Mockito.when(hitbox.getCenterX()).thenReturn(1.0);
    	Mockito.when(hitbox.getCenterY()).thenReturn(1.0);
    	Mockito.when(game.getEntities()).thenReturn(entities);
    	
    	Mockito.when(pacman.getHitBox()).thenReturn(hitbox);
    	Mockito.when(hitbox.getCenterX()).thenReturn(3.0);
    	Mockito.when(hitbox.getCenterY()).thenReturn(3.0);

    	
    	Mockito.when(entities.get(0).getHitBox()).thenReturn(hitbox);
    	
    	Mockito.when(entities.get(0).getHitBox().getCenterX()).thenReturn(3.5);
    	Mockito.when(entities.get(0).getHitBox().getCenterY()).thenReturn(3.5);
    	
    	Mockito.when(((Ghost) entities.get(0)).getAlive()).thenReturn(false);
    	
    	
    	Mockito.when(ghost.getAlive()).thenReturn(false);
		
		assertNull(collision.collisionGhost());
    	
    }
	
	@Test
    void test_CollisionGhost_true()
    {
		
    	Ghost ghost = Mockito.mock(Ghost.class);
    	Pacman pacman = Mockito.mock(Pacman.class);
    	Rectangle2D.Double hitbox = Mockito.mock(Rectangle2D.Double.class);
    	ArrayList<Entity> entities = new ArrayList<Entity>();
    	entities.add(ghost);
    	entities.add(ghost);
    	
    	Mockito.when(game.getPacman()).thenReturn(pacman);
    	Mockito.when(pacman.getHitBox()).thenReturn(hitbox);
    	Mockito.when(hitbox.getCenterX()).thenReturn(1.0);
    	Mockito.when(hitbox.getCenterY()).thenReturn(1.0);
    	Mockito.when(game.getEntities()).thenReturn(entities);
    	
    	Mockito.when(pacman.getHitBox()).thenReturn(hitbox);
    	Mockito.when(hitbox.getCenterX()).thenReturn(3.0);
    	Mockito.when(hitbox.getCenterY()).thenReturn(3.0);

    	
    	Mockito.when(entities.get(0).getHitBox()).thenReturn(hitbox);
    	
    	Mockito.when(entities.get(0).getHitBox().getCenterX()).thenReturn(3.5);
    	Mockito.when(entities.get(0).getHitBox().getCenterY()).thenReturn(3.5);
    	
    	Mockito.when(((Ghost) entities.get(0)).getAlive()).thenReturn(true);
    	
    	
    	Mockito.when(ghost.getAlive()).thenReturn(true);
    	
    	Point2D.Double position = new Point2D.Double(1.0, 1.0);

    	Mockito.when(ghost.getPosition()).thenReturn(position);
    	
    	Mockito.when(pacman.getPosition()).thenReturn(position);
		
		Rectangle2D.Double hitboxGhost = Mockito.mock(Rectangle2D.Double.class);
		Rectangle2D.Double hitboxPacman = Mockito.mock(Rectangle2D.Double.class);
		
		Mockito.when(hitboxPacman.intersects(hitboxGhost)).thenReturn(true);
		Rectangle2D hitboxCollision = Mockito.mock(Rectangle2D.Double.class);
		
		Mockito.when(hitboxCollision.getHeight()).thenReturn(5.0);
		Mockito.when(hitboxCollision.getWidth()).thenReturn(1.0);
		
		Mockito.when(hitboxPacman.getHeight()).thenReturn(5.0);
		Mockito.when(hitboxPacman.getWidth()).thenReturn(1.0);
		
		assertNotNull(collision.collisionGhost());
		
    }
	
	@Test
	void test_checkPacDotCollision()
	{
	    int expectedSize = level.getPacDots().size();
		Pacman pacman = new Pacman(2.0, 2.0);
		
		Mockito.when(game.getPacman()).thenReturn(pacman);
		Mockito.when(game.getCurrentLevel()).thenReturn(level);
		collision.checkConsumablesCollision();
		assertEquals(expectedSize, level.getPacDots().size());
		
	}
	
	@Test
    void test_checkEnergizerCollision()
    {
        int expectedSize = level.getEnergizers().size();
        Pacman pacman = new Pacman(2.0, 4.0);
        
        Mockito.when(game.getPacman()).thenReturn(pacman);
        Mockito.when(game.getCurrentLevel()).thenReturn(level);
        collision.checkConsumablesCollision();
        assertEquals(expectedSize, level.getEnergizers().size());
        
    }


    @Test
    void test_ghostSpawn_BLINKY()
    {
    	
    	Ghost ghost = new Ghost(16,15,GhostType.BLINKY);
    	IBehaviour behaviour = ghostBehaviourFactory.createBehaviour(ghost, behavioursID.RANDOM, game);
        Pacman pacman = Mockito.mock(Pacman.class);
        
        ghost.setBehaviour(behaviour);
    	Mockito.when(game.getCurrentLevel()).thenReturn(Mockito.mock(Level.class));
    	Mockito.when(game.getCurrentLevel().getAuthTilesGhost()).thenReturn(authGhost);
    	Mockito.when(game.getCurrentLevel().getAuthTilesGhostRoom()).thenReturn(authGhost);
    	Mockito.when(game.getPacman()).thenReturn(pacman);
    	Mockito.when(pacman.isInvincible()).thenReturn(false);
    	
    	collision.ghostSpawn(ghost);
    	
    	assertEquals(16.15, ghost.getHitBoxX());
    }
    
    @Test
    void test_ghostSpawn_CLYDE()
    {
    	
    	Ghost ghost = new Ghost(16,15,GhostType.CLYDE);
    	IBehaviour behaviour = ghostBehaviourFactory.createBehaviour(ghost, behavioursID.RANDOM, game);
        ghost.setBehaviour(behaviour);
    	Mockito.when(game.getCurrentLevel()).thenReturn(Mockito.mock(Level.class));
    	Mockito.when(game.getCurrentLevel().getAuthTilesGhost()).thenReturn(authGhost);
    	Mockito.when(game.getCurrentLevel().getAuthTilesGhostRoom()).thenReturn(authGhost);
    	Pacman pacman = Mockito.mock(Pacman.class);
    	Mockito.when(game.getPacman()).thenReturn(pacman);
    	Mockito.when(pacman.isInvincible()).thenReturn(false);
    	
    	collision.ghostSpawn(ghost);
    	
    	assertEquals(15.95, ghost.getHitBoxX());
    }
    
    @Test
    void test_ghostSpawn_PINKY()
    {
    	
    	Ghost ghost = new Ghost(16,15,GhostType.PINKY);
    	IBehaviour behaviour = ghostBehaviourFactory.createBehaviour(ghost, behavioursID.RANDOM, game);
        ghost.setBehaviour(behaviour);
    	Mockito.when(game.getCurrentLevel()).thenReturn(Mockito.mock(Level.class));
    	Mockito.when(game.getCurrentLevel().getAuthTilesGhost()).thenReturn(authGhost);
    	Mockito.when(game.getCurrentLevel().getAuthTilesGhostRoom()).thenReturn(authGhost);
    	Pacman pacman = Mockito.mock(Pacman.class);
    	Mockito.when(game.getPacman()).thenReturn(pacman);
    	Mockito.when(pacman.isInvincible()).thenReturn(false);

    	
    	collision.ghostSpawn(ghost);
    	
    	assertEquals(16.15, ghost.getHitBoxX());
    }
    
    @Test
    void test_ghostSpawn_INKY()
    {
    	
    	Ghost ghost = new Ghost(16,15,GhostType.INKY);
    	IBehaviour behaviour = ghostBehaviourFactory.createBehaviour(ghost, behavioursID.RANDOM, game);
        ghost.setBehaviour(behaviour);
    	Mockito.when(game.getCurrentLevel()).thenReturn(Mockito.mock(Level.class));
    	Mockito.when(game.getCurrentLevel().getAuthTilesGhost()).thenReturn(authGhost);
    	Mockito.when(game.getCurrentLevel().getAuthTilesGhostRoom()).thenReturn(authGhost);
    	Pacman pacman = Mockito.mock(Pacman.class);
    	Mockito.when(game.getPacman()).thenReturn(pacman);
    	Mockito.when(pacman.isInvincible()).thenReturn(false);

    	
    	collision.ghostSpawn(ghost);
    	
    	assertEquals(15.95, ghost.getHitBoxX());
    }
    
    @Test
    void test_ghostSpawn_Gate()
    {
    	
    	Ghost ghost = new Ghost(15,15,GhostType.BLINKY);
    	IBehaviour behaviour = ghostBehaviourFactory.createBehaviour(ghost, behavioursID.RANDOM, game);
        ghost.setBehaviour(behaviour);
    	ghost.setInTheGate();
    	Mockito.when(game.getCurrentLevel()).thenReturn(Mockito.mock(Level.class));
    	Mockito.when(game.getCurrentLevel().getAuthTilesGhost()).thenReturn(authGhost);
    	Mockito.when(game.getCurrentLevel().getAuthTilesGhostRoom()).thenReturn(authGhost);
    	Pacman pacman = Mockito.mock(Pacman.class);
    	Mockito.when(game.getPacman()).thenReturn(pacman);
    	Mockito.when(pacman.isInvincible()).thenReturn(false);

    	
    	collision.ghostSpawn(ghost);
    	
    	assertEquals(15.05, ghost.getHitBoxY());
    }

    @Test
    void test_ghostSpawn_EndGate()
    {
    	
    	Ghost ghost = new Ghost(15,12,GhostType.BLINKY);
    	IBehaviour behaviour = ghostBehaviourFactory.createBehaviour(ghost, behavioursID.RANDOM, game);
        ghost.setBehaviour(behaviour);
    	ghost.setInTheGate();
    	Mockito.when(game.getCurrentLevel()).thenReturn(Mockito.mock(Level.class));
    	Mockito.when(game.getCurrentLevel().getAuthTilesGhost()).thenReturn(authGhost);
    	Mockito.when(game.getCurrentLevel().getAuthTilesGhostRoom()).thenReturn(authGhost);
    	Pacman pacman = Mockito.mock(Pacman.class);
    	Mockito.when(game.getPacman()).thenReturn(pacman);
    	Mockito.when(pacman.isInvincible()).thenReturn(false);

    	
    	collision.ghostSpawn(ghost);
    	
    	assertEquals(15.05, ghost.getHitBoxX());
    	assertEquals(12.05, ghost.getHitBoxY());
    }
    
    @Test
    void test_ghostMove_Corner()
    {
    	
    	Ghost ghost = new Ghost(2,30,GhostType.BLINKY);
    	IBehaviour behaviour = ghostBehaviourFactory.createBehaviour(ghost, behavioursID.RANDOM, game);
        ghost.setBehaviour(behaviour);
    	ghost.setAlive();
    	ghost.setDirection(Direction.LEFT);
    	Mockito.when(game.getCurrentLevel()).thenReturn(Mockito.mock(Level.class));
    	Mockito.when(game.getCurrentLevel().getAuthTilesGhost()).thenReturn(authGhost);
    	Mockito.when(game.getCurrentLevel().getAuthTilesGhostRoom()).thenReturn(authGhost);
    	Pacman pacman = Mockito.mock(Pacman.class);
    	Mockito.when(game.getPacman()).thenReturn(pacman);
    	Mockito.when(pacman.isInvincible()).thenReturn(false);
    	
    	collision.ghostMove(ghost);
    	
    	assertEquals(2.05, ghost.getHitBoxX());
    	assertEquals(29.95, ghost.getHitBoxY());
    }
    
    @Test
    void test_preparePacman()
    {
        Pacman pacman = new Pacman(3.0, 2.0);
        Pacman newDirectionPacman = new Pacman(3.0, 2.0);
        Pacman nextTilesPacman = new Pacman(3.0, 2.0);
        
        newDirectionPacman.setDirection(Direction.RIGHT);
        
        Mockito.when(game.getNewDirection()).thenReturn(Direction.LEFT);
        Mockito.when(game.getNextTilesDirection()).thenReturn(Direction.RIGHT);
        Mockito.when(game.getPacman()).thenReturn(pacman);
        Mockito.when(game.getNewDirectionPacman()).thenReturn(newDirectionPacman);  
        Mockito.when(game.getNextTilesPacman()).thenReturn(nextTilesPacman);
        collision.preparePacman();
        
        assertEquals(2.95, newDirectionPacman.getHitBoxX());
    }
 
}
