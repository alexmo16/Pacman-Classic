package com.pacman.engine;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class Window implements WindowListener
{
    private JFrame frame;

    public Window(ISettings s)
    {
        frame = new JFrame(s.getTitle());
        frame.setMinimumSize(new Dimension(s.getMinWindowWidth(), s.getMinWindowHeight()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(this);
    }

    public void clear()
    {
        frame.setVisible(false);
        frame.dispose();
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
        Engine.stopGame();
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
    }
}
