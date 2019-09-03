package com.pacman.engine;

import com.pacman.engine.world.WorldData;

public interface ISettings
{
    public String getTitle();

    public int getMutedButton();

    public int getPauseButton();

    public int getMinWindowWidth();

    public int getMinWindowHeight();

    public float getScale();

    public double getUpdateRate();

    public WorldData getWorldData();

    public int[] getAuthTiles();
    
    public ISpritesManager getSpritesManager();
}
