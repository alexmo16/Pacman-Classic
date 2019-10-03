package com.pacman.utils;

import java.io.File;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class Settings
{
	public static final String TITLE = "Pac-Man";

    public static final String START_MUSIC_PATH = "." + File.separator + "assets" + File.separator + "pacman_beginning.wav",
					    	   GAME_SIREN_PATH = "." + File.separator + "assets" + File.separator + "siren.wav",
					           CHOMP_PATH = "." + File.separator + "assets" + File.separator + "pacman_chomp.wav",
					           DEATH_PATH = "." + File.separator + "assets" + File.separator + "pacman_death.wav",
					           INTERMISSION_PATH = "." + File.separator + "assets" + File.separator + "pacman_intermission.wav";
 
    public static final int MIN_WINDOW_WIDTH = 800,
				 			MIN_WINDOW_HEIGHT = 600;
    
    public static final float SCALE = 1.0f;
    
    public static final double MOVEMENT = 0.1;
    
    public static int SPEED = 1;
    
    public static final double UPDATE_RATE = 1.0 / 60.0; // pour avoir 60 fps dans notre jeu.
    
    public static final double TILES_HEIGHT = 1;
    public static final double TILES_WIDTH = 1;
    
    private volatile static int musicVolume = 100;
    private volatile static boolean musicMute = false;
    private volatile static int soundsVolume = 100;
    private volatile static boolean soundsMute = false;
    private final static String MUSIC_KEY = "MK";
    private final static String SOUNDS_KEY = "SK";
	
    
	public static boolean isMusicMute() 
	{
		synchronized (MUSIC_KEY) 
		{
			return musicMute;
		}
	}
	
	public static void setMusicMute(boolean musicMute) 
	{
		synchronized (MUSIC_KEY) 
		{
			Settings.musicMute = musicMute;
		}
	}
	
	public static boolean isSoundsMute() 
	{
		synchronized (SOUNDS_KEY) 
		{
			return soundsMute;
		}
	}
	
	public static void setSoundsMute(boolean soundsMute) 
	{
		synchronized (SOUNDS_KEY) 
		{
			Settings.soundsMute = soundsMute;
		}
	}

	public static int getMusicVolume()
	{
		synchronized(MUSIC_KEY)
		{
			return musicVolume;
		}
	}

	public static void setMusicVolume(int musicVolume)
	{
		synchronized(MUSIC_KEY)
		{
			Settings.musicVolume = musicVolume;
		}
	}

	public static int getSoundsVolume()
	{
		synchronized(SOUNDS_KEY)
		{
			return soundsVolume;
		}
	}

	public static void setSoundsVolume(int soundsVolume)
	{
		synchronized(SOUNDS_KEY)
		{
			Settings.soundsVolume = soundsVolume;
		}
	}
}
