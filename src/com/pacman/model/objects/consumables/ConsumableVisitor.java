package com.pacman.model.objects.consumables;

/**
 * 
 * @authors Alexis Morel-mora2316 Felix Roy-royf3005 Jordan Ros Chantrabot-rosj2204 Andrien Lacomme-laca2111 Louis Ryckebusch-rycl2501
 *
 */
public interface ConsumableVisitor
{
	void visitEnergizer(Energizer energizer);
	void visitPacDot(PacDot pacdot);
	void visitDefault(Consumable consumable);
}
