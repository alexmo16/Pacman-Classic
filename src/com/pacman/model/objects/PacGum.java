package com.pacman.model.objects;

import java.util.ArrayList;

import com.pacman.model.world.Tile;

public class PacGum extends StaticObject
{
    public PacGum()
    {
        super();
    }

    public PacGum(double x, double y, double width, double height)
    {
        super(x, y, width, height);
        this.points = 50;
        this.sprite = spritesManager.getPacGumCoords();
    }

    public static ArrayList<PacGum> generatePacGumList()
    {
        ArrayList<PacGum> pacGumList = new ArrayList<PacGum>();

        for (int y = 0; y < worldData.getHeight(); y++)
        {

            for (int x = 0; x < worldData.getWidth(); x++)
            {

                if (worldData.getTile(x, y) == Tile.ENERGIZER.getValue())
                {

                    pacGumList.add(new PacGum(x + 0.25, y + 0.25, 0.5, 0.5));
                }
            }
        }
        return pacGumList;
    }

}
