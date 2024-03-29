package com.pacman.model.objects.entities.behaviours;

import com.pacman.model.Game;
import com.pacman.model.objects.entities.Ghost;
import com.pacman.model.objects.entities.behaviours.IBehaviour.behavioursID;

public class BehaviourFactory 
{
	public IBehaviour createBehaviour(Ghost ghost, behavioursID type, Game game)
	{
		IBehaviour behaviour;
		switch (type) 
		{
		case AMBUSH:
			behaviour = new AmbushBehaviour(ghost, game);
			break;
		case CHASE:
			behaviour = new ChaseBehaviour(ghost, game);
			break;
		case FEAR:
			behaviour = new FearBehaviour(ghost, game);
			break;
		case RANDOM:
			behaviour = new RandomBehaviour(ghost);
			break;
		default:
			behaviour = null;
			break;
		
		}
		return behaviour;
	}
}
