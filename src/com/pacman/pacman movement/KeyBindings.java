package com.pacman.movement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class KeyBindings extends JFrame {
    DrawPanel drawPanel = new DrawPanel();
    
    public KeyBindings(){
        Action rightAction = new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
            	if (drawPanel.pacman.getX()+drawPanel.pacman.getWidth()<drawPanel.getPanelWidth()) {
	            	drawPanel.pacman.setLocation(drawPanel.pacman.getX()+10, drawPanel.pacman.getY());
	                drawPanel.repaint();
            	}
            }
        };
        
        Action leftAction = new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
            	if (drawPanel.pacman.getX()>0) {
            		drawPanel.pacman.setLocation(drawPanel.pacman.getX()-10, drawPanel.pacman.getY());
	                drawPanel.repaint();
            	}
            }
        };
        
        Action downAction = new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
            	if (drawPanel.pacman.getY()+drawPanel.pacman.getHeight()<drawPanel.getPanelHeight()) {
            		drawPanel.pacman.setLocation(drawPanel.pacman.getX(), drawPanel.pacman.getY()+10);
	                drawPanel.repaint();
            	}
            }
        };
        
        Action upAction = new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
            	if (drawPanel.pacman.getY()>0) {
            		drawPanel.pacman.setLocation(drawPanel.pacman.getX(), drawPanel.pacman.getY()-10);
                    drawPanel.repaint();            		
            	}

            }
        };

        InputMap inputMap = drawPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = drawPanel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        actionMap.put("rightAction", rightAction);
        
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        actionMap.put("leftAction", leftAction);
        
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        actionMap.put("downAction", downAction);
        
        inputMap.put(KeyStroke.getKeyStroke("UP"), "upAction");
        actionMap.put("upAction", upAction);

        add(drawPanel);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class DrawPanel extends JPanel {
    	int WIDTH = 800;
    	int HEIGHT = 600;
    	Rectangle pacman = new Rectangle(50, 50, 50, 50);
    	
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.YELLOW);
            g.fillRect(pacman.getX(), pacman.getY(), pacman.getHeight(), pacman.getWidth());
        }

        public Dimension getPreferredSize() {
            return new Dimension(WIDTH, HEIGHT);
        }
        
        public int getPanelWidth() {
        	return WIDTH;
        }
        
        public int getPanelHeight() {
        	return HEIGHT;
        }
        
    }
    


    public static void main(String[] args) {

        new KeyBindings();

     
    }
}
