package com.pacman.utils;

import java.awt.event.KeyEvent;
import java.io.File;

public class Settings
{
	public static final String TITLE = "Pac-Man";

    public static final String START_MUSIC_PATH = "." + File.separator + "assets" + File.separator + "pacman_beginning.wav",
					    	   GAME_SIREN_PATH = "." + File.separator + "assets" + File.separator + "siren.wav",
					           CHOMP_PATH = "." + File.separator + "assets" + File.separator + "pacman_chomp.wav",
					           DEATH_PATH = "." + File.separator + "assets" + File.separator + "pacman_death.wav";
 
    public static final int MUTED_BUTTON = KeyEvent.VK_M, 
    				 		PAUSE_BUTTON = KeyEvent.VK_P,
    				 		RESUME_BUTTON = KeyEvent.VK_R,
				 			MIN_WINDOW_WIDTH = 800,
				 			MIN_WINDOW_HEIGHT = 600;

    public static final float SCALE = 1.0f;
    
    public static final double MOVEMENT = 0.1;
    
    public static int SPEED = 1;
    
    public static final double UPDATE_RATE = 1.0 / 60.0; // pour avoir 60 fps dans notre jeu.
    
    public static final double TILES_HEIGHT = 1;
    public static final double TILES_WIDTH = 1;
    
    public volatile static float musicVolume = 1f;
    public volatile static float soundsVolume = 1f;
}
