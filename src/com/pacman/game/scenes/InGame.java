package com.pacman.game.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.pacman.game.Settings;
import com.pacman.game.objects.GameObject;
import com.pacman.game.objects.Maze;
import com.pacman.game.objects.ScoreBar;



public class InGame extends JPanel
{
	private static final long serialVersionUID = -4409914743783241379L;
	
	Background background;
	GridBagConstraints gbcMainPane;
	GridBagConstraints gbcBottomPane;
	Maze maze;
	PausePane pausePane;
	static ScoreBar scoreBar;
    
	public InGame(Settings s)
	{	
        pausePane = new PausePane();
        maze = new Maze(s);
        scoreBar = new ScoreBar(s);
        background = new Background();
		
        gbcMainPane = new GridBagConstraints();
        gbcMainPane.anchor = GridBagConstraints.CENTER;
        gbcMainPane.fill = GridBagConstraints.BOTH;
        gbcMainPane.gridx = 0;
        gbcMainPane.gridy = 0;
        gbcMainPane.weightx = 1.0;
        gbcMainPane.weighty = 0.955;
        
        gbcBottomPane = new GridBagConstraints();
        gbcBottomPane.anchor = GridBagConstraints.CENTER;
        gbcBottomPane.fill = GridBagConstraints.BOTH;
        gbcBottomPane.gridx = 0;
        gbcBottomPane.gridy = 1;
        gbcBottomPane.weightx = 1.0;
        gbcBottomPane.weighty = 0.005;
        
        setLayout(new GridBagLayout());
	}
	
	
	
	public ScoreBar getScoreBar() {
		return scoreBar;
	}

	public static ScoreBar getStaticScoreBar() {
		return scoreBar;
	}
	

	public void togglePausePane()
	{
		pausePane.setVisible(!pausePane.isVisible());
	}
	
	public void init()
	{
        add(pausePane, gbcMainPane);
        add(maze, gbcMainPane);
        add(scoreBar, gbcBottomPane);
        add(background, gbcMainPane);
	}
	
	public void addGameObject(GameObject obj)
	{
		add(obj, gbcMainPane);
	}
	
	private class Background extends JPanel 
    {
		private static final long serialVersionUID = 8397625306659836208L;

		public Background()
        {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) 
        {
            super.paintComponent(g);
            g.setColor(Color.black);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

    }
    
    public class PausePane extends JPanel 
    {
		private static final long serialVersionUID = -8273676847821032527L;
		
		private JLabel label;

        public PausePane()
    	{
            setVisible(false);
            setOpaque(false);
            setBackground(new Color(0, 0, 0, 200));
            setLayout(new GridBagLayout());

            label = new JLabel("Paused");
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            Font font = label.getFont();
            font = font.deriveFont(Font.BOLD, 48f);
            label.setFont(font);
            label.setForeground(Color.WHITE);
            add(label);
        }

        @Override
        protected void paintComponent(Graphics g) 
        {
            super.paintComponent(g);
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
