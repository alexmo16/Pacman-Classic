package com.pacman.engine;


import com.pacman.game.objects.DynamicObject;
import com.pacman.game.objects.GameObject;


public abstract class CollisionManager{
	static int[][] map = Engine.getMap();
	static int mapH = Engine.getHeight();
	static int mapW = Engine.getWidth();
	static int[] authTiles;
	static ISettings settings;

	
	
	
	public static int collisionWall(DynamicObject obj) 
	
	{
		
		
		int xMin = (int) obj.getRectangle().getMinX(); //coordonnes de l'angle haut-gauche du pacman
		int yMin = (int) obj.getRectangle().getMinY(); 
		
		int xMax = (int) obj.getRectangle().getMaxX(); //coordonnes de l'angle bas-droit du pacman
		int yMax = (int) obj.getRectangle().getMaxY();
				
		


		
		if ((xMin <= 0 & xMax <= 0 )||(xMin >= mapW - 1 & xMax >= mapW - 1 ) || (yMin <= 0 & yMax <= 0 ) || (yMin >= mapH - 1 & yMax >= mapH - 1 ))	
		{
			return 2;
		}
		
		else if (isAuth(xMin,yMin) & isAuth(xMin,yMax) & isAuth(xMax,yMin) & isAuth(xMax,yMax)) 
		{
			return 0;
		}

		return 1;


	}
		public static boolean collisionObj(GameObject obj1, GameObject obj2)
		{
			return obj1.getRectangle().intersects(obj2.getRectangle());			
		}
		
		
		private static boolean isAuth(int x,int y)
		{
			for (int i : authTiles) 
			{
				if (map[x][y] == i)
				{
					return true;
				}
			}
			return false;
		}
		
		public static void setSettings(ISettings settings)
		{
			CollisionManager.settings = settings;
			authTiles = settings.getAuthTiles();
		}


		
	}

	

