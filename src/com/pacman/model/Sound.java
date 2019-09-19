package com.pacman.model;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.FloatControl.Type;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public class Sound
{
    private File file;
    private AudioInputStream inputStream;
    private Clip audioClip;
    private LineListener listener;
    private LineListener defaultListener;
    private boolean isRunning = false;
    private float volume;

    public Sound(String path) throws UnsupportedAudioFileException, IOException
    {
        file = new File(path).getAbsoluteFile();
        inputStream = AudioSystem.getAudioInputStream(file);
        
        Sound that = this;
        defaultListener = new LineListener()
        {
            @Override
            public void update(LineEvent event)
            {
                if (event.getType() == LineEvent.Type.STOP)
                {
                	if (listener != null)
                	{
                		listener.update(event);
                	}
                    that.stop();
                }
            }
        };
    }
    
    public File getFile()
    {
        return file;
    }

    /**
     * Play sound asynchronously.
     * @param listener
     * @return boolean
     */
    public boolean play()
    {
        if (isRunning)
        {
            return false;
        }
        try
        {
            inputStream = AudioSystem.getAudioInputStream(file);
            audioClip = AudioSystem.getClip();
            if (audioClip.isOpen())
            {
                audioClip.close();
            }

            audioClip.open(inputStream);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e)
        {
            return false;
        }
        audioClip.setFramePosition(0);
        audioClip.addLineListener(defaultListener);
        setMasterGain();
        
        isRunning = true;
        audioClip.start();
        return true;
    }

    public void stop()
    {
        if (audioClip != null)
        {
            audioClip.stop();
            audioClip.close();
            isRunning = false;
        }
        audioClip = null;
        inputStream = null;
    }

    /**
     * This will make the sound loopback until the stop function is called.
     * @return
     */
    public boolean playLoopBack()
    {
        if (isRunning)
        {
            return false;
        }
        try
        {
            inputStream = AudioSystem.getAudioInputStream(file);
            audioClip = AudioSystem.getClip();
            if (audioClip == null)
            {
                return false;
            }

            if (audioClip.isOpen())
            {
                audioClip.close();
            }

            audioClip.open(inputStream);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e)
        {
            return false;
        }
        audioClip.setFramePosition(0);
        setMasterGain();
        
        isRunning = true;
        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
        return true;
    }

    public boolean getIsRunning()
    {
        return isRunning;
    }
    
    public float getVolume()
    {
    	return volume;
    }
    
    public void setVolume(int volume)
    {
    	this.volume = (float)volume / 100f;
    	setMasterGain();
    }
    
    public void setListener(LineListener l)
    {
    	listener = l;
    }
    
    private void setMasterGain()
    {
    	if (audioClip == null)
    	{
    		return;
    	}
    	FloatControl gainControl = (FloatControl) audioClip.getControl(Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
}
