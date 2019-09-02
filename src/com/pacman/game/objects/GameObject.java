package com.pacman.game.objects;

import java.awt.geom.Rectangle2D;

import com.pacman.game.MazeData;
import com.pacman.game.Settings;
import com.pacman.game.SpritesManager;
import javax.swing.JPanel;

public abstract class GameObject extends JPanel
{
    private static final long serialVersionUID = 5089602216528128118L;

    protected static int mazeHeight, mazeWidth;
    protected final SpritesManager spritesManager;
    protected static MazeData mazeData;

    protected Rectangle2D.Double object = null;

    protected double x;
    protected double y;

    public GameObject(double x, double y, double width, double height, Settings s)
    {
        super();
        mazeHeight = s.getMazeData().getHeight();
        mazeWidth = s.getMazeData().getWidth();
        mazeData = s.getMazeData();
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
