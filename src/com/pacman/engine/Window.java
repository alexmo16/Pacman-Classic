package com.pacman.engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.pacman.game.Settings;

public class Window 
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
	}
	
	public void update()
	{
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		bs.show();
	}
}
