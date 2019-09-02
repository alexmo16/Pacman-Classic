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



public class InGame extends JPanel
{
	private static final long serialVersionUID = -4409914743783241379L;
	
	Background background;
	GridBagConstraints gbc;
	Maze maze;
	PausePane pausePane;
    
	public InGame(Settings s)
	{	
        pausePane = new PausePane();
        maze = new Maze(s);
        background = new Background();
		
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        
        setLayout(new GridBagLayout());

        // TODO : Make this work with key
        //MouseAdapter handler = new MouseAdapter() {
        //    @Override
        //    public void mouseClicked(MouseEvent e) {
        //        pausePane.setVisible(!pausePane.isVisible());
        //    }
        //};
	}
	
	public void init()
	{
        add(pausePane, gbc);
        add(maze, gbc);
        add(background, gbc);
	}
	
	public void addGameObject(GameObject obj)
	{
		add(obj, gbc);
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
