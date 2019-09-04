package com.pacman.utils;
/**
 * Publisher of Observer design pattern
 *
 */
public interface IPublisher 
{
	public void registerObserver(IObserver<?> observer);
	public void removeObserver(IObserver<?> observer);
	public void notifyObservers();
}
