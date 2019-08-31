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
		int x1 = (int) obj.getMinX(); //coordonnes de l'angle haut-gauche du pacman
		int y1 = (int) obj.getMinY(); 
		
		int x2 = (int) obj.getMaxX(); //coordonnes de l'angle bas-droit du pacman
		int y2 = (int) obj.getMaxY();
		
		int caseX1 = x1/nbColumn;
		int caseY1 = y1/nbLine;
		
		int caseX2 = x2/nbColumn;
		int caseY2 = y2/nbLine;
		

		if (map[caseY1][caseX1] != 1 || map[caseY2][caseX2] != 1)
		{
			//System.out.println("collision avec mur");
			return true;
		}

		return false;
	}
		public boolean collisionObj(Rectangle obj1, Rectangle obj2)
		{
			return obj1.intersects(obj2);			
		}
	}
	

