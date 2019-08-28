package com.pacman.engine;

/**
 * Class de fonctions utiles pour l'engin du jeu.
 * @author Alexis Morel
 */
public final class EngineUtils {
	
	/**
	 *  La fonction de base n'est pas utilisé, car la précision est meilleure que la fonction de base currentTimeMillis.
	 * @author Alexis Morel
	 */
	public static double getCurrentTimeInMillis()
	{
		return System.nanoTime() / 1000000000.0;
	}
}
