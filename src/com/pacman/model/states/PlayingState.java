package com.pacman.model.states;

import java.util.ArrayList;
import java.util.Random;

import com.pacman.model.Collision;
import com.pacman.model.Game;
import com.pacman.model.objects.consumables.PacDot;
import com.pacman.model.objects.entities.Entity;
import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.objects.entities.Pacman.Animation;
import com.pacman.model.threads.TimerThread;
import com.pacman.model.world.Direction;
import com.pacman.model.world.Level;
import com.pacman.utils.IObserver;
import com.pacman.utils.Settings;

public class PlayingState implements IGameState, IObserver<Direction>
{
    private StatesName name = StatesName.PLAY;

    private Game game;
    private Collision collision;
    private int i;
    private volatile boolean isPacmanDying = false;
    private Random random;
    private int randomInt;

    private TimerThread timerThread;

    public PlayingState(Game gm)
    {
        if (gm == null)
        {
            return;
        }

        game = gm;

        collision = game.getCollision();

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
            if (game.getPacman().getCurrentAnimation() == Animation.DYING && game.getPacman().isEndOfAnimation())
            {
                game.setState(game.getPacman().getLives() <= 0 ? game.getStopState() : game.getInitState());
                isPacmanDying = false;
            }
            return;
        }
        
        Level level = game.getCurrentLevel();
        ArrayList<PacDot> pacdots = level.getPacDots();
        if (pacdots.size() == 0)
        {
            game.setPacmanWon(true);
            game.setState(game.getStopState());
        }

        if (timerThread == null)
        {

            timerThread = new TimerThread(5);
            timerThread.start();
        }

        if (!timerThread.isAlive())
        {
            random = new Random();
            randomInt = random.nextInt(4);

            if (!((Ghost) game.getEntities().get(randomInt)).getAlive())
            {

                collision.ghostSpawn(((Ghost) game.getEntities().get(randomInt)));
                ((Ghost) game.getEntities().get(randomInt)).setSpawning();
                timerThread = null;
            }

        }

        for (Entity entity : game.getEntities())
        {
        	if (entity.getHitBox() != game.getPacman().getHitBox())
            {
        		if (((Ghost) entity).getAlive())
                {
        			collision.ghostMove(entity);

                } else if ( ((Ghost) entity).getSpawning() )
                {
                    collision.ghostSpawn(entity);
                }
            }
        }

        for (i = 0; i < Settings.SPEED; i++)
        {

            game.getNewDirectionPacman().setHitBox(game.getPacman().getHitBox());
            game.getNextTilesPacman().setHitBox(game.getPacman().getHitBox());
            game.getNextTilesPacman().updatePosition(game.getNextTilesDirection());
            game.getNewDirectionPacman().updatePosition(game.getNewDirection());

            if (collision.collisionGhost())
            {
                game.killPacman();
                return;
            }

            // Strategy pattern for wall collisions.

            collision.checkConsumablesCollision();
            collision.executeWallStrategy();
        }

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
    	timerThread = null;
        isPacmanDying = true;
        game.stopInGameMusics();
        game.getPacman().looseLive();
        game.playDeathMusic();
    }
}
