package com.pacman.game.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import com.pacman.game.Settings;

public class ScoreBar extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private int score = 0;
	
	
	public ScoreBar (Settings s)
	{

		s.getSpritesManager();
	    this.setMaximumSize( new Dimension(Integer.MAX_VALUE,30) );
		setBackground(Color.WHITE);
}
	
	public void setScoreBar(int score)
	{
		this.score = score;
	}
	
	public void paint(Graphics g) {
        super.paint(g);
        //Graphics2D g2d = (Graphics2D) g.create();
        g.drawString("score :"+score, 0,10);
        /*int[] k = spritesManager.getPacmanCoords();
        g.drawImage(spritesManager.getSpritesSheet(), (int)this.object.getX(), (int)this.object.getY(), 19, 19, k[0], k[1], k[2], k[3], null);

        g2d.dispose();*/
    }

}
