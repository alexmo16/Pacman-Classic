package com.pacman.game.objects;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.pacman.engine.Inputs;
import com.pacman.engine.Sound;
import com.pacman.game.Settings;
import com.pacman.game.SpritesManager;

/**
 * 
 * Class which implement every method for a Pacman object, such as direction or render
 *
 */
public class PacmanObject extends DynamicObject
{

    private static final long serialVersionUID = 1L;
    private final SpritesManager spritesManager;
    Settings settings = new Settings();
    Sound chomp;

    /**
     * Method used to create a new Pacman.
     *
     *
     * @return pacmanObject
     * 
     */
    public PacmanObject()
    {
        spritesManager = settings.getSpritesManager();
    }

    public PacmanObject(double x, double y, double width, double height, String direction, Settings s)
    {
        super(x, y, width, height, direction, s);
        spritesManager = settings.getSpritesManager();
    }

    /**
     * Method used to get the new direction of a pacmanObject(), by checking inputs of the User.
     *
     *
     * @return String direction = "left","right","up" or "down"
     * 
     */
    public static String getNewDirection(Inputs inputs, String direction)
    {
        if (inputs == null)
        {
            return direction;
        }

        if (inputs.isKeyDown(KeyEvent.VK_UP))
        {
            direction = "up";
        } else if (inputs.isKeyDown(KeyEvent.VK_DOWN))
        {
            direction = "down";
        } else if (inputs.isKeyDown(KeyEvent.VK_RIGHT))
        {
            direction = "right";
        } else if (inputs.isKeyDown(KeyEvent.VK_LEFT))
        {
            direction = "left";
        }
        return direction;
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
    		chomp.play( null );
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
}
