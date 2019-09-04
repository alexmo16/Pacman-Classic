package com.pacman.utils;

/**
 * Observer design pattern
 *
 * @param <A>
 */
public interface IObserver<A>
{
	public void update( A arg );
}
