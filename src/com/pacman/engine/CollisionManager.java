package com.pacman.engine;

import java.awt.Rectangle;


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
	
	public boolean collisionWall(Rectangle obj, int[][] map, int nbColumn, int nbLine) 
	
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
		
		System.out.println(map[caseXMax][caseYMax]);
		System.out.println(map[caseXMin][caseYMin]);

		if (map[caseXMin][caseYMin] == 0 & map[caseXMin][caseYMax] == 0 & map[caseXMax][caseYMin] == 0 & map[caseXMax][caseYMax] == 0 ) {
				return false;
		}


		return true;
	}
		public boolean collisionObj(Rectangle obj1, Rectangle obj2)
		{
			return obj1.intersects(obj2);			
		}
	}
	

