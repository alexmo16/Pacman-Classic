package com.pacman.game.objects;

import java.awt.Graphics;
import java.util.ArrayList;

import com.pacman.game.Settings;
import com.pacman.game.WorldTile;

public class PacGum extends StaticObject
{

    private static final long serialVersionUID = 1L;

    public PacGum()
    {
        super();
        this.points = 0;
    }

    public PacGum(double x, double y, double width, double height, Settings s)
    {
        super(x, y, width, height, s);
        this.points = 50;
    }

    @Override
    protected void paintComponent(Graphics g)
    {   
        int tileSize = Math.min(getHeight() / worldData.getHeight(), getWidth() / worldData.getWidth());
        if ( (tileSize & 1) != 0 ) { tileSize--; }
        
        int x = ((int)object.getX() * tileSize) + (getWidth() - (tileSize * worldData.getWidth())) / 2;
        int y = ((int)object.getY() * tileSize) + (getHeight() - (tileSize * worldData.getHeight())) / 2;
        int[] k = spritesManager.getPacGumCoords();

        g.drawImage(spritesManager.getSpritesSheet(), x, y, x + tileSize, y + tileSize, k[0], k[1], k[2], k[3], null);
    }

    public static ArrayList<PacGum> generatePacGumList(Settings s)
    {
        ArrayList<PacGum> gumList = new ArrayList<>();

        for (int y = 0; y < worldData.getHeight(); y++)
        {

            for (int x = 0; x < worldData.getWidth(); x++)
            {

                if (worldData.getTile(x, y) == WorldTile.ENERGIZER.getValue())
                {

                    gumList.add(new PacGum(x + 0.25, y + 0.25, 0.5, 0.5, s));
                }
            }
        }
        return gumList;
    }

    @Override
    public String toString()
    {
        return "x : " + this.object.getX() + "\t Y : " + this.object.getY();
    }

}
