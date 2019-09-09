package com.pacman.unittests.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.jupiter.api.Test;

import com.pacman.controller.GameController;
import com.pacman.controller.SoundController;
import com.pacman.controller.WindowController;
import com.pacman.model.Settings;

class GameControllerTests
{

    GameController gameManager;
    WindowController window;
    Settings s;

    @Test
    void testLoadedMusics()
    {
        boolean isException = false;
        SoundController expectedStartMusic = null;
        SoundController expectedGameSirent = null;
        SoundController expectedChomp = null;
        try
        {
            gameManager = new GameController();
            s = new Settings();
            window = new WindowController(s);
            gameManager.init(window);

            expectedStartMusic = new SoundController("." + File.separator + "assets" + File.separator + "pacman_beginning.wav");
            expectedGameSirent = new SoundController("." + File.separator + "assets" + File.separator + "siren.wav");
            expectedChomp = new SoundController("." + File.separator + "assets" + File.separator + "pacman_chomp.wav");
        } catch (UnsupportedAudioFileException e)
        {
            isException = true;
        } catch (IOException e)
        {
            isException = true;
        }

        assertEquals(expectedStartMusic.getFile(), gameManager.getStartMusic().getFile());
        assertEquals(expectedGameSirent.getFile(), gameManager.getGameSiren().getFile());
        assertEquals(expectedChomp.getFile(), gameManager.getChomp().getFile());
        assertFalse(isException);
    }

}