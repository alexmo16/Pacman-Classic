package com.pacman.game.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

public class PauseScreen extends SceneObject
{
	private static final long serialVersionUID = -6141122120219706748L;
	
	private final JLabel label;
	
	public PauseScreen(String txt)
    {
        setVisible(false);
        setBackground(new Color(0, 0, 0, 200));
        setLayout(new GridBagLayout());

        label = new JLabel(txt);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        Font font = label.getFont();
        font = font.deriveFont(Font.BOLD, 48f);
        label.setFont(font);
        label.setForeground(Color.WHITE);
        add(label);
    }

    public void togglePausePane()
    {
        setVisible(!isVisible());
    }
	
	@Override
    protected void paintComponent(Graphics g)
    {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}