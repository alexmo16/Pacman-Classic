<<<<<<< HEAD
package com.pacman.unittests.game;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import com.pacman.engine.Sound;
import com.pacman.game.GameManager;
import com.pacman.engine.Window;
import com.pacman.game.Settings;

class GameManagerTests
{

    GameManager gameManager;
    Window window;
    Settings s;

    @Test
    void testLoadedMusics()
    {
        boolean isException = false;
        Sound expectedStartMusic = null;
        Sound expectedGameSirent = null;
        Sound expectedChomp = null;
        try
        {
            gameManager = new GameManager();
            s = new Settings();
            window = new Window(s);
            gameManager.init(window);

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
        assertEquals(expectedChomp.getFile(), gameManager.getChomp().getFile());
        assertFalse(isException);
    }

}
=======
package com.pacman.unittests.game;

public class GameManagerTests {

}
>>>>>>> branch 'master' of https://gitlab.com/pacman-equipe-05/pacman/
