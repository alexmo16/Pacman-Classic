package com.pacman.game;

import java.awt.event.KeyEvent;
import java.io.File;

import com.pacman.engine.ISettings;
import com.pacman.engine.world.WorldData;

public class Settings implements ISettings
{
    private final String title = "Pac-Man";

    // Sound related
    private final String startMusicPath = "." + File.separator + "assets" + File.separator + "pacman_beginning.wav";
    private final String gameSirenPath = "." + File.separator + "assets" + File.separator + "siren.wav";
    private final String chompPath = "." + File.separator + "assets" + File.separator + "pacman_chomp.wav";

   
    private final int mutedButton = KeyEvent.VK_M, pauseButton = KeyEvent.VK_P, minWindowWidth = 800,
            minWindowHeight = 600;

    private final float scale = 1.0f;

    private final double updateRate = 1.0 / 60.0; // pour avoir 60 fps dans notre jeu.

    private final WorldData mazeData = new WorldData(
            new String(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "map.txt"));

    private final SpritesManager spritesManager = new SpritesManager(
            System.getProperty("user.dir") + File.separator + "assets" + File.separator + "pacmanTiles.png", 48);

    private final int[] authTiles =
    { WorldTile.FLOOR.getValue(), WorldTile.GUM.getValue(), WorldTile.ENERGIZER.getValue(), WorldTile.FRUIT.getValue(), WorldTile.PAC_MAN_START.getValue() };

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public int getMutedButton()
    {
        return mutedButton;
    }

    @Override
    public int getPauseButton()
    {
        return pauseButton;
    }

    @Override
    public int getMinWindowWidth()
    {
        return minWindowWidth;
    }

    @Override
    public int getMinWindowHeight()
    {
        return minWindowHeight;
    }

    @Override
    public float getScale()
    {
        return scale;
    }

    @Override
    public double getUpdateRate()
    {
        return updateRate;
    }

    @Override
    public WorldData getWorldData()
    {
        return mazeData;
    }

    @Override
    public SpritesManager getSpritesManager()
    {
        return spritesManager;
    }

    @Override
    public int[] getAuthTiles()
    {
        return authTiles;
    }

	public String getStartMusicPath() {
		return startMusicPath;
	}

	public String getGameSirenPath() {
		return gameSirenPath;
	}

	public String getChompPath() {
		return chompPath;
	}
}
