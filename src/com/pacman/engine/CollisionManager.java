package com.pacman.engine;


import com.pacman.game.object.DynamicObject;
import com.pacman.game.object.GameObject;


public class CollisionManager {
	
	private static CollisionManager instance;
	
	public static CollisionManager getInstance()
	{
		if ( instance == null )
		{
			instance = new CollisionManager();		
		}
		
		return instance;
	}
	
	public boolean collisionWall(DynamicObject obj, int[][] map, int nbColumn, int nbLine) 
	
	{
		
		//Rcupration des coordonnes de 2 angles opposs du pacman
		int xMin = (int) obj.getMinX(); //coordonnes de l'angle haut-gauche du pacman
		int yMin = (int) obj.getMinY(); 
		
		int xMax = (int) obj.getMaxX(); //coordonnes de l'angle bas-droit du pacman
		int yMax = (int) obj.getMaxY();
				
		int caseXMin = xMin/nbColumn;
		int caseYMin = yMin/nbLine;
		
		int caseXMax = xMax/nbColumn;
		int caseYMax = yMax/nbLine;
		


		if (map[caseXMin][caseYMin] == 0 & map[caseXMin][caseYMax] == 0 & map[caseXMax][caseYMin] == 0 & map[caseXMax][caseYMax] == 0 ) {
				return false;
		}


		return true;
	}
		public boolean collisionObj(GameObject obj1, GameObject obj2)
		{
			return obj1.intersects(obj2);			
		}
	}
	

