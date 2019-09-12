package com.pacman.model.objects;

import java.awt.geom.Rectangle2D;

/**
 * Definie un objet qui possede une boite de collision.
 *
 */
public abstract class GameObject
{
    protected Rectangle2D.Double hitBox = null;
    protected Sprite sprite = null;

    public GameObject(double x, double y, double width, double height)
    {
    	hitBox = new Rectangle2D.Double(x, y, width, height);
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
