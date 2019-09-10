package com.pacman.model.objects;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.pacman.model.Settings;
import com.pacman.model.Sound;
import com.pacman.model.world.Direction;
import com.pacman.utils.IObserver;
import com.pacman.utils.IPublisher;
import com.pacman.view.Input;

/**
 * 
 * Class which implement every method for a Pacman object, such as direction or render
 * Observer design pattern
 *
 */
public class PacmanObject extends DynamicObject implements IPublisher
{
    private Sound chomp;
    private static Direction direction = Direction.LEFT;
    private ArrayList<IObserver<Direction>> observers = new ArrayList<IObserver<Direction>>();

    /**
     * Method used to create a new Pacman.
     *
     *
     * @return pacmanObject
     * 
     */
    public PacmanObject(Settings settings)
    {
    	
    }

    public PacmanObject(double x, double y, double width, double height, Direction direction, Settings s)
    {
        super(x, y, width, height, direction, s);
    }

    /**
     * Method used to get the new direction of a pacmanObject(), by checking inputs of the User.
     *
     *
     * @return String direction = "left","right","up" or "down"
     * 
     */
    public void checkNewDirection(Input inputs)
    {
        if (inputs == null)
        {
            return;
        }

        Direction oldDirection = direction;
        
        if (inputs.isKeyDown(KeyEvent.VK_UP))
        {
            direction = Direction.UP;
        } else if (inputs.isKeyDown(KeyEvent.VK_DOWN))
        {
            direction = Direction.DOWN;
        } else if (inputs.isKeyDown(KeyEvent.VK_RIGHT))
        {
            direction = Direction.RIGHT;
        } else if (inputs.isKeyDown(KeyEvent.VK_LEFT))
        {
            direction = Direction.LEFT;
        }
        
        if ( oldDirection != direction )
        {
        	notifyObservers();
        }
    }

    /**
     * method used to make every move we need when Pacman eat a gum.
     *chomp sound will be played, score will be updated and the gum state will be put to "eaten"
     *
     * @return void
     * 
     */
    public void eatGum(StaticObject obj, ScoreBar scoreBar)
    {
    	if ( chomp != null )
    	{
    		chomp.play();
    	}
        scoreBar.addPointsScore(obj.getPoints());
        obj.setEaten(true);
    }
    
    public void setChompSound( Sound sound )
    {
    	chomp = sound;
    }

	@Override
	public void registerObserver(IObserver<?> observer) 
	{
		@SuppressWarnings("unchecked")
		IObserver<Direction> tempObserver = (IObserver<Direction>)observer;
		if ( tempObserver != null )
		{
			observers.add(tempObserver);
		}
	}

	@Override
	public void removeObserver(IObserver<?> observer) 
	{
		int index = observers.indexOf(observer);
		if (index >= 0)
		{
			observers.remove(index);
		}
	}

	@Override
	public void notifyObservers() 
	{
		for ( IObserver<Direction> observer : observers )
		{
			if (observer != null)
			{
				observer.update( direction );
			}
		}
	}
	
	public Direction getPacDirection() {
		return direction;
	}
}
