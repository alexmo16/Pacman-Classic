package com.pacman.engine;

/**
 * Utils functions for the engine.
 */
public final class EngineUtils
{

    /**
     * The System's function to get the current time in millis is has less precision than System.nanoTime.
     */
    public static double getCurrentTimeInMillis()
    {
        return System.nanoTime() / 1000000000.0;
    }
}
