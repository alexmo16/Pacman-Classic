package com.pacman.controller.states;

import java.util.ArrayList;

import com.pacman.controller.CollisionController;
import com.pacman.controller.Engine;
import com.pacman.controller.GameController;
import com.pacman.controller.InputController;
import com.pacman.model.Settings;
import com.pacman.model.objects.DynamicObject;
import com.pacman.model.objects.Gum;
import com.pacman.model.objects.PacGum;
import com.pacman.model.objects.PacmanObject;
import com.pacman.model.world.Direction;
import com.pacman.utils.IObserver;

public class PlayingState implements IGameState, IObserver<Direction>
{
	private GameController gameManager;
	private Settings settings;
	private String name = "Play";
	private PacmanObject pacman;
	private PacmanObject maybeFuturPacman;
	private PacmanObject futurPacman;
	private ArrayList<Gum> gumList;
    private ArrayList<PacGum> pacGumList;
    private Direction oldDirection, direction;
	
	public PlayingState( GameController gm )
	{
		if ( gm == null )
		{
			return;
		}
		gameManager = gm;
		settings = (Settings)gameManager.getSettings();
		
		pacman = gameManager.getPacman();
        pacman.registerObserver( this );
        
        int[] startingPosition = gameManager.getStartingPosition();
        double pacmanBox = gameManager.getPacmanBox();
        
        maybeFuturPacman = new PacmanObject(startingPosition[0], startingPosition[1], pacmanBox, pacmanBox, direction, settings);
        futurPacman = new PacmanObject(startingPosition[0], startingPosition[1], pacmanBox, pacmanBox, direction, settings);
        
        direction = gameManager.getDirection();
        oldDirection = direction;
        
        gumList = gameManager.getGumList();
        pacGumList = gameManager.getPacGumList();
	}

	@Override
	public void update(Engine engine) 
	{
		InputController inputs = engine.getInputs();
		if (inputs.isKeyDown(settings.getMutedButton()))
        {
            gameManager.toggleUserMuteSounds();
        }
		
		if (gameManager.getCanPausedGame() && inputs.isKeyDown(settings.getPauseButton()))
		{
			gameManager.setState(gameManager.getPauseState());
		}

		pacman.checkNewDirection(engine.getInputs());

        maybeFuturPacman.getRectangle().setRect(pacman.getRectangle().getX(), pacman.getRectangle().getY(),
                pacman.getRectangle().getWidth(), pacman.getRectangle().getHeight());
        futurPacman.getRectangle().setRect(pacman.getRectangle().getX(), pacman.getRectangle().getY(),
                pacman.getRectangle().getWidth(), pacman.getRectangle().getHeight());
        DynamicObject.updatePosition(futurPacman.getRectangle(), direction);
        DynamicObject.updatePosition(maybeFuturPacman.getRectangle(), oldDirection);

        checkGumCollision();
        checkPacGumCollision();
        // Strategy pattern for wall collisions.
        String checkWallCollision = CollisionController.collisionWall(futurPacman);
        executeWallStrategy( checkWallCollision );
	}

	public String getName()
	{
		return name;
	}
    
    /**
     * Check if pacman eats a Gum
     */
    private void checkGumCollision()
    {
        for (Gum gum : gumList)
        {
            if (CollisionController.collisionObj(pacman, gum))
            {
                pacman.eatGum(gum, gameManager.getScoreBar());
                gumList.remove(gum);
                gum = null;
                break;
            }
        }
    }
    
    /**
     * Check if pacman eats a PacGum
     */
    private void checkPacGumCollision()
    {
        for (PacGum pacGum : pacGumList)
        {
            if (CollisionController.collisionObj(pacman, pacGum))
            {
                pacman.eatGum(pacGum, gameManager.getScoreBar());
                pacGumList.remove(pacGum);
                pacGum = null;
                break;
            }
        }
    }
    
    /**
     * Redirect to the correct strategy
     * @param collisionString
     */
    private void executeWallStrategy( String collisionString )
    {
    	if ( collisionString == "void" )
    	{
    		tunnelStrategy();
    	}
    	else if ( collisionString == "wall" )
    	{
    		oneWallStrategy();
    	}
    	else
    	{
    		noWallStrategy();
    	}
    }
    
    /**
     * Strategy if pacman hits a wall.
     */
    private void oneWallStrategy()
    {
    	String collisionString = CollisionController.collisionWall(maybeFuturPacman);
        if (collisionString == "void")
        {
            DynamicObject.tunnel(pacman.getRectangle(), oldDirection);
            pacman.setDirection(oldDirection);
            gameManager.getScoreBar().setCollision(false, oldDirection);
        }
        if (collisionString == "path")
        {
            DynamicObject.updatePosition(pacman.getRectangle(), oldDirection);
            pacman.setDirection(oldDirection);
            gameManager.getScoreBar().setCollision(false, oldDirection);
        } 
        else
        {
        	gameManager.getScoreBar().setCollision(true, oldDirection);
        }
    }
    
    /**
     * Strategy if pacman goes through the tunnel
     */
    private void tunnelStrategy()
    {
    	DynamicObject.tunnel(pacman.getRectangle(), direction);
    	gameManager.getScoreBar().setCollision(false, oldDirection);
    }
    
    /**
     * Strategy if pacman moves forward
     */
    private void noWallStrategy()
    {
    	DynamicObject.updatePosition(pacman.getRectangle(), direction);
        pacman.setDirection(direction);
        oldDirection = direction;
        gameManager.getScoreBar().setCollision(false, oldDirection);
    }
	
    /**
     * update of the observer
     */
	@Override
	public void update(Direction direction) 
	{
		oldDirection = this.direction;
		this.direction = direction;
	}
}
