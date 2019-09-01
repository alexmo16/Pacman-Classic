package com.pacman.engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window implements WindowListener
{
	private JFrame frame;
	private Canvas canvas;
	private BufferStrategy bs;
	private Graphics g;
	
	
	public Window(ISettings s)
	{
		canvas = new Canvas();
		frame = new JFrame(s.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		
		frame.addWindowListener( this );
	}
	
	public void update()
	{
		bs.show();
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
	
	public Canvas getCanvas()
	{
		return canvas;
	}
	
	public Graphics getGraphics()
	{
		return g;
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
