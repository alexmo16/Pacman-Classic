package com.pacman.model.objects;

import java.util.ArrayList;

import com.pacman.model.Settings;
import com.pacman.model.world.Tile;

public class Gum extends StaticObject
{
    public Gum()
    {
        super();
    }

    public Gum(double x, double y, double width, double height, Settings s)
    {
        super(x, y, width, height, s);
        this.points = 10;
        this.sprite = spritesManager.getGumCoords();
    }

    public static ArrayList<Gum> generateGumList(Settings s)
    {
        ArrayList<Gum> gumList = new ArrayList<Gum>();

        for (int y = 0; y < worldData.getHeight(); y++)
        {

            for (int x = 0; x < worldData.getWidth(); x++)
            {

                if (worldData.getTile(x, y) == Tile.GUM.getValue())
                {

                    gumList.add(new Gum(x + 0.25, y + 0.25, 0.5, 0.5, s));
                }
            }
        }
        return gumList;
    }
    
}
