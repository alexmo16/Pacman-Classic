package com.pacman.unittests.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.jupiter.api.Test;

import com.pacman.controller.GameController;
import com.pacman.model.Game;
import com.pacman.model.Sound;
import com.pacman.view.Window;

class GameControllerTests
{

    Game gameManager;
    Window window;
    

    @Test
    void testLoadedMusics() throws InterruptedException
    {
        boolean isException = false;
        Sound expectedStartMusic = null;
        Sound expectedGameSirent = null;
        Sound expectedChomp = null;
        GameController gameController = null;
        try
        {
            gameManager = new Game();
            window = new Window(gameManager);
            gameController = new GameController(window, gameManager);
            gameController.start();
            Thread.sleep(500);

            expectedStartMusic = new Sound("." + File.separator + "assets" + File.separator + "pacman_beginning.wav");
            expectedGameSirent = new Sound("." + File.separator + "assets" + File.separator + "siren.wav");
            expectedChomp = new Sound("." + File.separator + "assets" + File.separator + "pacman_chomp.wav");
        } catch (UnsupportedAudioFileException e)
        {
            isException = true;
        } catch (IOException e)
        {
            isException = true;
        }

        assertEquals(expectedStartMusic.getFile(), gameManager.getStartMusic().getFile());
        assertEquals(expectedGameSirent.getFile(), gameManager.getGameSiren().getFile());
        assertEquals(expectedChomp.getFile(), gameManager.getChomps()[0].getFile());
        assertFalse(isException);
        if (gameController != null)
        {
        	gameController.stopGame();
        }
    }

}