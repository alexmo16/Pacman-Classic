package com.pacman.utils;

import java.io.File;

public class Settings
{
	public static final String TITLE = "Pac-Man";

    public static final String START_MUSIC_PATH = "." + File.separator + "assets" + File.separator + "pacman_beginning.wav",
					    	   GAME_SIREN_PATH = "." + File.separator + "assets" + File.separator + "siren.wav",
					           CHOMP_PATH = "." + File.separator + "assets" + File.separator + "pacman_chomp.wav",
					           DEATH_PATH = "." + File.separator + "assets" + File.separator + "pacman_death.wav";
 
    public static final int MIN_WINDOW_WIDTH = 800,
				 			MIN_WINDOW_HEIGHT = 600;
    
    public static final float SCALE = 1.0f;
    
    public static final double MOVEMENT = 0.1;
    
    public static int SPEED = 1;
    
    public static final double UPDATE_RATE = 1.0 / 60.0; // pour avoir 60 fps dans notre jeu.
    
    public static final double TILES_HEIGHT = 1;
    public static final double TILES_WIDTH = 1;
    
    private volatile static float musicVolume = 1f;
    private volatile static boolean musicMute = false;
    private volatile static float soundsVolume = 1f;
    private volatile static boolean soundsMute = false;
	
    
	public static boolean isMusicMute() 
	{
		synchronized ("MUSIC_KEY") 
		{
			return musicMute;
		}
	}
	
	public static void setMusicMute(boolean musicMute) 
	{
		synchronized ("MUSIC_KEY") 
		{
			Settings.musicMute = musicMute;
		}
	}
	
	public static boolean isSoundsMute() 
	{
		synchronized ("SOUND_KEY") 
		{
			return soundsMute;
		}
	}
	
	public static void setSoundsMute(boolean soundsMute) 
	{
		synchronized ("SOUND_KEY") 
		{
			Settings.soundsMute = soundsMute;
		}
	}

	public static float getMusicVolume()
	{
		synchronized("MUSIC_KEY")
		{
			return musicVolume;
		}
	}

	public static void setMusicVolume(float musicVolume)
	{
		synchronized("MUSIC_KEY")
		{
			Settings.musicVolume = musicVolume;
		}
	}

	public static float getSoundsVolume()
	{
		synchronized("SOUND_KEY")
		{
			return soundsVolume;
		}
	}

	public static void setSoundsVolume(float soundsVolume)
	{
		synchronized("SOUND_KEY")
		{
			Settings.soundsVolume = soundsVolume;
		}
	}
}
