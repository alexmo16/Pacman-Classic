package com.pacman.engine.objects;

import java.awt.geom.Rectangle2D;

import com.pacman.engine.ISettings;
import com.pacman.engine.ISpritesManager;
import com.pacman.engine.world.WorldData;

/**
 * Definie un objet qui possede une boite de collision.
 *
 */
public abstract class GameObject extends SceneObject
{
    private static final long serialVersionUID = 5089602216528128118L;

    protected final ISpritesManager spritesManager;
    protected static WorldData worldData;

    protected Rectangle2D.Double object = null;

    protected double x;
    protected double y;

    public GameObject(double x, double y, double width, double height, ISettings s)
    {
        super();
        worldData = s.getWorldData();
        this.spritesManager = s.getSpritesManager();
        this.object = new Rectangle2D.Double(x, y, width, height);
    }

    public GameObject()
    {
        super();
        worldData = null;
        this.spritesManager = null;
        this.object = new Rectangle2D.Double();
    }

    public Rectangle2D.Double getRectangle()
    {
        return this.object;
    }
}
