package com.pacman.controller;

import com.pacman.model.world.Data;

public interface ISettings
{
    public String getTitle();

    public int getMutedButton();

    public int getPauseButton();

    public int getMinWindowWidth();

    public int getMinWindowHeight();

    public float getScale();

    public double getUpdateRate();

    public Data getWorldData();

    public int[] getAuthTiles();
}
