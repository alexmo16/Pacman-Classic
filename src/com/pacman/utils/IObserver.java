package com.pacman.utils;

import com.pacman.utils.IPublisher.UpdateID;

/**
 * Observer design pattern
 *
 * @param <A>
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 */
public interface IObserver
{
	public void updateObservers(UpdateID updateID);
}
