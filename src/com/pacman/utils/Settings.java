package com.pacman.utils;

import java.awt.event.KeyEvent;
import java.io.File;

import com.pacman.model.world.Data;
import com.pacman.model.world.Tile;

public final class Settings
{
	public static final String TITLE = "Pac-Man";

    public static final String START_MUSIC_PATH = "." + File.separator + "assets" + File.separator + "pacman_beginning.wav",
					    	   GAME_SIREN_PATH = "." + File.separator + "assets" + File.separator + "siren.wav",
					           CHOMP_PATH = "." + File.separator + "assets" + File.separator + "pacman_chomp.wav";
   
    public static final int MUTED_BUTTON = KeyEvent.VK_M, 
    				 		PAUSE_BUTTON = KeyEvent.VK_P,
    				 		RESUME_BUTTON = KeyEvent.VK_R,
				 			MIN_WINDOW_WIDTH = 800,
				 			MIN_WINDOW_HEIGHT = 600;

    public static final float SCALE = 1.0f;

    public static final double UPDATE_RATE = 1.0 / 60.0; // pour avoir 60 fps dans notre jeu.

    public static final Data WORLD_DATA = new Data(new String(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "map.txt"));
    
    public static final int[] AUTH_TILES = { Tile.FLOOR.getValue(), Tile.GUM.getValue(), Tile.ENERGIZER.getValue(), Tile.FRUIT.getValue(), Tile.PAC_MAN_START.getValue() };
}
