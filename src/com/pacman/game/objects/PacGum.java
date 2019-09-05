package com.pacman.game.objects;

import java.util.ArrayList;

import com.pacman.game.Settings;
import com.pacman.game.WorldTile;

public class PacGum extends StaticObject
{

    private static final long serialVersionUID = 1L;

    public PacGum()
    {
        super();
    }

    public PacGum(double x, double y, double width, double height, Settings s)
    {
        super(x, y, width, height, s);
        this.points = 50;
        this.sprite = spritesManager.getPacGumCoords();
    }

    public static ArrayList<PacGum> generatePacGumList(Settings s)
    {
        ArrayList<PacGum> pacGumList = new ArrayList<PacGum>();

        for (int y = 0; y < worldData.getHeight(); y++)
        {

            for (int x = 0; x < worldData.getWidth(); x++)
            {

                if (worldData.getTile(x, y) == WorldTile.ENERGIZER.getValue())
                {

                    pacGumList.add(new PacGum(x + 0.25, y + 0.25, 0.5, 0.5, s));
                }
            }
        }
        return pacGumList;
    }

}
