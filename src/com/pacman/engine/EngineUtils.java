package com.pacman.engine;

public final class EngineUtils {
	public static double getCurrentTimeInMillis()
	{
		return System.nanoTime() / 1000000000.0;
	}
}
