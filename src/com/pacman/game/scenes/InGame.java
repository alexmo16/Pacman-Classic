package com.pacman.game.scenes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.border.EmptyBorder;

import com.pacman.engine.objects.SceneObject;
import com.pacman.engine.world.Scene;

public class InGame extends Scene
{
    private static final long serialVersionUID = -4409914743783241379L;

    GridBagConstraints gbcBackground;
    GridBagConstraints gbcGamePane;
    GridBagConstraints gbcStatusBar;

    public InGame()
    {
    	setLayout(new GridBagLayout());
    	setBorder(new EmptyBorder(-1, -1, -1, -1));
    	
        gbcBackground = new GridBagConstraints();
        gbcBackground.anchor = GridBagConstraints.CENTER;
        gbcBackground.fill = GridBagConstraints.BOTH;
        gbcBackground.gridx = 0;
        gbcBackground.gridy = 0;
        gbcBackground.gridheight = 3;
        gbcBackground.weightx = 1.0;
        gbcBackground.weighty = 1.0;
    	
        gbcGamePane = new GridBagConstraints();
        gbcGamePane.anchor = GridBagConstraints.CENTER;
        gbcGamePane.fill = GridBagConstraints.BOTH;
        gbcGamePane.gridx = 0;
        gbcGamePane.gridy = 0;
        gbcGamePane.weightx = 1.0;
        gbcGamePane.weighty = 0.96;

        gbcStatusBar = new GridBagConstraints();
        gbcStatusBar.anchor = GridBagConstraints.CENTER;
        gbcStatusBar.fill = GridBagConstraints.BOTH;
        gbcStatusBar.gridx = 0;
        gbcStatusBar.gridy = 1;
        gbcStatusBar.weightx = 1.0;
        gbcStatusBar.weighty = 0.04;
    }

	public void addToBackground(SceneObject object)
	{
		add(object, gbcBackground);
	}
    
	public void addToGame(SceneObject object)
	{
        add(object, gbcGamePane);
	}
	
	public void addToStatusBar(SceneObject object)
	{
		add(object, gbcStatusBar);
	}
}
