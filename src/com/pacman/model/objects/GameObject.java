package com.pacman.model.objects;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.math.MathContext;

import com.pacman.model.world.Sprite;

/**
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public abstract class GameObject
{
	protected Point2D.Double position;
    protected Rectangle2D.Double hitBox;
    protected Point2D.Double spriteOffset;
    protected Sprite sprite;
    protected MathContext mc = new MathContext(4);;
    
    public GameObject(double x, double y)
    {
    	position = new Point2D.Double(x, y);
    	hitBox = new Rectangle2D.Double(x, y, 1, 1);
    	spriteOffset = new Point2D.Double(0, 0);
    }
    
    public GameObject(double x, double y, Rectangle2D.Double hb)
    {
    	position = new Point2D.Double(x, y);
    	hitBox = hb;
    	spriteOffset = new Point2D.Double(0, 0);
    }
    
    public GameObject(double x, double y, Point2D.Double so, Rectangle2D.Double hb)
    {
    	position = new Point2D.Double(x - so.x, y - so.y);
    	hitBox = hb;
    	spriteOffset = so;
    }
    
    public double getHitBoxX()
    {
    	return new BigDecimal(hitBox.getX() ,mc).doubleValue();
    }
    
    public double getHitBoxY()
    {
    	return new BigDecimal(hitBox.getY() ,mc).doubleValue();	
    }
    
    public Rectangle2D.Double getHitBox()
    {
        return hitBox;
    }
	
    public void setHitBox(Rectangle2D.Double hb)
    {
        hitBox.setRect(hb);
    }
    
    public Point2D.Double getPosition()
    {
    	return position;
    }
    
   public void setPosition(double x, double y)
   {
	   position.x = x - spriteOffset.x ;
	   position.y = y - spriteOffset.y ;
	   hitBox.setRect(x, y, hitBox.getWidth(), hitBox.getHeight());
   }
    
	public Sprite getSprite() 
	{
		return sprite;
	}
}
