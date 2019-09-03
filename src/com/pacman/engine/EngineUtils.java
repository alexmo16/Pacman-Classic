package com.pacman.engine;

/**
 * Class de fonctions utiles pour l'engin du jeu.
 */
public final class EngineUtils
{

    /**
     * La fonction de base n'est pas utilis�, car la pr�cision est meilleure que la
     * fonction de base currentTimeMillis.
     */
    public static double getCurrentTimeInMillis()
    {
        return System.nanoTime() / 1000000000.0;
    }
}
