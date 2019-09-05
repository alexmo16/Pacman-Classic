package com.pacman.game.objects;

import java.util.ArrayList;

import com.pacman.game.Settings;
import com.pacman.game.WorldTile;

public class Gum extends StaticObject
{

    private static final long serialVersionUID = 2208175145272051306L;

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

                if (worldData.getTile(x, y) == WorldTile.GUM.getValue())
                {

                    gumList.add(new Gum(x + 0.25, y + 0.25, 0.5, 0.5, s));
                }
            }
        }
        return gumList;
    }
    
}
