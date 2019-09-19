package com.pacman.utils;
/**
 * Publisher of Observer design pattern
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public interface IPublisher 
{
	public void registerObserver(IObserver<?> observer);
	public void removeObserver(IObserver<?> observer);
	public void notifyObservers();
}
