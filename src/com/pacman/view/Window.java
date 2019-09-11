package com.pacman.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.pacman.controller.GameController;
import com.pacman.utils.Settings;

public class Window implements WindowListener
{
    private JFrame frame;

    public Window()
    {
        frame = new JFrame(Settings.TITLE);
        frame.setMinimumSize(new Dimension(Settings.MIN_WINDOW_WIDTH, Settings.MIN_WINDOW_HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(this);
    }

    public void addListener(KeyListener k)
    {
    	frame.addKeyListener(k);
    }
    
    public void setContainer(JPanel container)
    {
    	frame.getContentPane().removeAll();
        frame.add(container);
        frame.pack();
    }
    
    public void render()
    {
    	frame.repaint();
    }
    
    public JFrame getFrame()
    {
        return frame;
    }

    
    @Override
    public void windowOpened(WindowEvent e)
    {
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        GameController.stopGame();
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
    	GameController.pauseGame();
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
    	GameController.resumeGame();
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
    	GameController.resumeGame();
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
    	GameController.pauseGame();
    }
}
