package com.pacman.engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.pacman.game.Settings;

public class Window implements WindowListener
{
	private JFrame frame;
	private BufferedImage image;
	private Canvas canvas;
	private BufferStrategy bs;
	private Graphics g;
	
	
	public Window(Settings s)
	{
		image = new BufferedImage(s.getWidth(), s.getHeight(), BufferedImage.TYPE_INT_RGB);
		canvas = new Canvas();
		Dimension dim = new Dimension((int) (s.getWidth() * s.getScale()), (int) (s.getHeight() * s.getScale()));
		canvas.setPreferredSize(dim);
		canvas.setMaximumSize(dim);
		canvas.setMinimumSize(dim);
		
		frame = new JFrame(s.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		
		frame.addWindowListener( this );
	}
	
	public void update()
	{
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		bs.show();
	}
	
	public JFrame getFrame()
	{
		return frame;
	}
	
	public Canvas getCanvas()
	{
		return canvas;
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
