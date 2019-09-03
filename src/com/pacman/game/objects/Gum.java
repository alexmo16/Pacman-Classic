package com.pacman.game.objects;

import java.awt.Graphics;
import java.util.ArrayList;

import com.pacman.game.Settings;

public class Gum extends StaticObject
{

    private static final long serialVersionUID = 2208175145272051306L;

    public Gum()
    {
        super();
        this.points = 0;
    }

    public Gum(double x, double y, double width, double height, Settings s)
    {
        super(x, y, width, height, s);
        this.points = 10;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int size = Math.min(getWidth(), getHeight()) / mazeHeight;
        int x = (int) this.object.getX();
        int y = (int) this.object.getY();

        x = x * size + (getWidth() - (size * mazeWidth)) / 2;
        y = y * size + (getHeight() - (size * mazeHeight)) / 2;

        int[] k = spritesManager.getGumCoords();

        g.drawImage(spritesManager.getSpritesSheet(), x, y, x + size, y + size, k[0], k[1], k[2], k[3], null);

    }

    public static ArrayList<Gum> generateGumList(Settings s)
    {
        ArrayList<Gum> gumList = new ArrayList<>();

        for (int y = 0; y < mazeHeight; y++)
        {

            for (int x = 0; x < mazeWidth; x++)
            {

                if (mazeData.getTile(x, y) == 30)
                {

                    gumList.add(new Gum(x + 0.25, y + 0.25, 0.5, 0.5, s));
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
