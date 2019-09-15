package com.pacman.model.objects;

import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.math.MathContext;

import com.pacman.model.world.Sprite;

/**
 * Definie un objet qui possede une boite de collision.
 *
 */
public abstract class GameObject
{
    protected Rectangle2D.Double hitBox;
    protected Sprite sprite;
    protected MathContext mc;
    
    public GameObject(double x, double y, double width, double height)
    {
    	hitBox = new Rectangle2D.Double(x, y, width, height);
    	mc = new MathContext(4);
    }
    
    
    public double getX()
    {
    	return new BigDecimal(hitBox.getX(),mc).doubleValue();
    }
    
    public double getY()
    {
    	return new BigDecimal(hitBox.getY(),mc).doubleValue();	
    }
    
    public Rectangle2D.Double getHitBox()
    {
        return hitBox;
    }
	
	public Sprite getSprite() 
	{
		return sprite;
	}
}
