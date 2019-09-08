package com.pacman.model.objects;

import java.awt.geom.Rectangle2D;

import com.pacman.controller.ISettings;
import com.pacman.model.world.Data;

/**
 * Definie un objet qui possede une boite de collision.
 *
 */
public abstract class GameObject
{
    protected static Data worldData;

    protected Rectangle2D.Double object = null;

    public GameObject(double x, double y, double width, double height, ISettings s)
    {
        super();
        worldData = s.getWorldData();
        this.object = new Rectangle2D.Double(x, y, width, height);
    }

    public GameObject()
    {
        super();
        worldData = null;
        this.object = new Rectangle2D.Double();
    }

    public Rectangle2D.Double getRectangle()
    {
        return this.object;
    }

	public Rectangle2D.Double getObject() 
	{
		return object;
	}
}
