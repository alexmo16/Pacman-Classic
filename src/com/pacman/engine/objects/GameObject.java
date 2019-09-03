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

    protected static int mazeHeight, mazeWidth;
    protected final ISpritesManager spritesManager;
    protected static WorldData mazeData;

    protected Rectangle2D.Double object = null;

    protected double x;
    protected double y;

    public GameObject(double x, double y, double width, double height, ISettings s)
    {
        super();
        mazeHeight = s.getWorldData().getHeight();
        mazeWidth = s.getWorldData().getWidth();
        mazeData = s.getWorldData();
        this.spritesManager = s.getSpritesManager();
        this.object = new Rectangle2D.Double(x, y, width, height);
        setOpaque(false);
    }

    public GameObject()
    {
        super();
        mazeHeight = 0;
        mazeWidth = 0;
        mazeData = null;
        this.spritesManager = null;
        this.object = new Rectangle2D.Double();
        setOpaque(false);
    }

    public Rectangle2D.Double getRectangle()
    {
        return this.object;
    }
}
