package com.pacman.engine;


import com.pacman.game.objects.DynamicObject;
import com.pacman.game.objects.GameObject;


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
		int xMin = (int) obj.getRectangle().getMinX(); //coordonnes de l'angle haut-gauche du pacman
		int yMin = (int) obj.getRectangle().getMinY(); 
		
		int xMax = (int) obj.getRectangle().getMaxX(); //coordonnes de l'angle bas-droit du pacman
		int yMax = (int) obj.getRectangle().getMaxY();
				
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
			return obj1.getRectangle().intersects(obj2.getRectangle());			
		}
	}
	

