package com.pacman.model.states;

import java.util.ArrayList;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import com.pacman.model.Game;
import com.pacman.model.objects.consumables.PacDot;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.threads.PhysicsThread;
import com.pacman.model.threads.TimerThread;
import com.pacman.model.world.Direction;
import com.pacman.model.world.Level;
import com.pacman.utils.IObserver;
import com.pacman.utils.Settings;

public class PlayingState implements IGameState, IObserver<Direction>
{
    private StatesName name = StatesName.PLAY;

    private Game game;
    private volatile boolean isPacmanDying = false;

    private LineListener deathSoundListener = new LineListener()
    {
         @Override
         public void update(LineEvent event)
         {
             if (event.getType() == LineEvent.Type.STOP)
             {        
                game.setState(game.getPacman().getLives() == 0 ? game.getStopState() : game.getInitState());
                game.stopDeathMusic();
                isPacmanDying = false;
             }
         }
    };
    
    public PlayingState(Game gm)
    {
        if (gm == null)
        {
            return;
        }

        game = gm;


        game.getPacman().registerObserver(this);

        game.getNewDirectionPacman().setHitBox(game.getPacman().getHitBox());
        game.getNextTilesPacman().setHitBox(game.getPacman().getHitBox());

        game.setNextTilesDirection(game.getPacman().getDirection());
        game.setNewDirection(game.getNextTilesDirection());

        game.getNewDirectionPacman().setAuthTiles(game.getCurrentLevel().getAuthTiles());
        game.getNextTilesPacman().setAuthTiles(game.getCurrentLevel().getAuthTiles());
    }

    @Override
    public void update()
    {
        if (isPacmanDying)
        {
            return;
        }
        
        Level level = game.getCurrentLevel();
        ArrayList<PacDot> pacdots = level.getPacDots();
        if (pacdots.size() == 0)
        {
            game.setPacmanWon(true);
            game.setState(game.getStopState());
        }

        if (game.getTimerThread() == null)
        {

            game.setTimerThread(new TimerThread(5));
            game.startTimerThread();
        }

        if (!game.getTimerThread().isAlive())
        {
            game.setPhysicsThread(new PhysicsThread(game, "GhostSpawn"));
            game.startPhysicsThread();

        }
        game.setPhysicsThread(new PhysicsThread(game, "Move"));
        game.startPhysicsThread();
        

    }

    @Override
    public StatesName getName()
    {
        return name;
    }

    /**
     * update of the observer
     */
    @Override
    public void update(Direction d)
    {

        if (game.getNewDirection() == game.getPacman().getDirection())
        {
            game.setNextTilesDirection(game.getNewDirection());
        }
        game.setNewDirection(d);

    }

    public void killPacman()
    {
    	game.setTimerThreadNull();
        isPacmanDying = true;
        game.stopInGameMusics();
        game.getPacman().looseLive();
        game.playDeathMusic(deathSoundListener);
    }
}
