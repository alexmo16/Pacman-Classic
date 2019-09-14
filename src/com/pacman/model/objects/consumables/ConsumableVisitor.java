 package com.pacman.model.objects.consumables;

public interface ConsumableVisitor
{
	void visitEnergizer(Energizer energizer);
	void visitPacDot(PacDot pacdot);
	void visitDefault(Consumable consumable);
}
