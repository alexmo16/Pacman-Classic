package com.pacman.engine;


import com.pacman.game.objects.DynamicObject;
import com.pacman.game.objects.GameObject;


public class CollisionManager {
	int[][] map = Engine.getMap();
	int mapH = Engine.getHeight();
	int mapW = Engine.getWidth();
	
	
	//singleton
	private static CollisionManager instance;
	
	public static CollisionManager getInstance()
	{
		if ( instance == null )
		{
			instance = new CollisionManager();		
		}
		
		return instance;
	}
	
	
	
	public int collisionWall(DynamicObject obj) 
	
	{
		
		
		int xMin = (int) obj.getRectangle().getMinX(); //coordonnes de l'angle haut-gauche du pacman
		int yMin = (int) obj.getRectangle().getMinY(); 
		
		int xMax = (int) obj.getRectangle().getMaxX(); //coordonnes de l'angle bas-droit du pacman
		int yMax = (int) obj.getRectangle().getMaxY();
				
		


		
		if ((xMin == 0 & xMax == 0 )||(xMin == mapW - 1 & xMax == mapW - 1 ) || (yMin == 0 & yMax == 0 ) || (yMin == mapH - 1 & yMax == mapH - 1 ))	
		{
			return 2;
		}
		
		else if (map[xMin][yMin] == 0 & map[xMin][yMax] == 0 & map[xMax][yMin] == 0 & map[xMax][yMax] == 0 ) 
		{
			return 0;
		}

		return 1;


	}
		public boolean collisionObj(GameObject obj1, GameObject obj2)
		{
			return obj1.getRectangle().intersects(obj2.getRectangle());			
		}
	}
	

