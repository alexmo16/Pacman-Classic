package com.pacman.game.objects;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.pacman.engine.Inputs;
import com.pacman.engine.Sound;
import com.pacman.game.Settings;
import com.pacman.game.SpritesManager;
import com.pacman.utils.IObserver;
import com.pacman.utils.IPublisher;

/**
 * 
 * Class which implement every method for a Pacman object, such as direction or render
 * Observer design pattern
 *
 */
public class PacmanObject extends DynamicObject implements IPublisher
{

    private static final long serialVersionUID = 1L;
    private SpritesManager spritesManager;
    private Settings settings;
    private Sound chomp;
    private static Direction direction = Direction.DOWN.LEFT;
    private ArrayList<IObserver<Direction>> observers = new ArrayList<IObserver<Direction>>();

    /**
     * Method used to create a new Pacman.
     *
     *
     * @return pacmanObject
     * 
     */
    public PacmanObject( Settings settings )
    {
    	this.settings = settings;
    	if ( settings != null )
    	{
    		spritesManager = settings.getSpritesManager();
    	}
    }

    public PacmanObject(double x, double y, double width, double height, Direction direction, Settings s)
    {
        super(x, y, width, height, direction, s);
        settings = s;
        if ( settings != null )
        {
        	spritesManager = settings.getSpritesManager();
        }
    }

    /**
     * Method used to get the new direction of a pacmanObject(), by checking inputs of the User.
     *
     *
     * @return String direction = "left","right","up" or "down"
     * 
     */
    public void checkNewDirection(Inputs inputs)
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

    
    /**
     * method which override the paint method, used to paint Pacman assets and animation, depending on Pacman direction
     *
     * @return void
     * 
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        int tileSize = Math.min(getHeight() / worldData.getHeight(), getWidth() / worldData.getWidth());
        if ( (tileSize & 1) != 0 ) { tileSize--; }

        double x = (object.getX() * tileSize - (tileSize / 2)) + (getWidth() - (tileSize * worldData.getWidth())) / 2;
        double y = (object.getY() * tileSize - (tileSize / 2)) + (getHeight() - (tileSize * worldData.getHeight())) / 2;
        int[] k = spritesManager.getPacmanCoords(this.getDirection());
        
        g.drawImage(spritesManager.getSpritesSheet(), (int) (x), (int) (y), (int) x + (2 * tileSize), (int) y + (2 * tileSize),
                    k[0], k[1], k[2], k[3], null);
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
