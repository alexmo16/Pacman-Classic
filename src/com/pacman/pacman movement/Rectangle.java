package com.pacman.movement;


public class Rectangle {

    private int x;
	private int y;
	private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height) {
    	this.x = x;
    	this.y = y;
        this.width = width;
        this.height = height;
    }
    
	public void setLocation(int i, int y2) {
		x = i;
		y = y2;
		
	}
    public double getArea() {
        return width * height;
    }
    
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}

    @Override
    public String toString() {
        return "Rectangle["+width+","+height+"]Area:"+getArea()+", Position : "+x+","+y;
    }


}