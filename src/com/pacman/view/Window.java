package com.pacman.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.pacman.controller.GameController;
import com.pacman.model.IGame;
import com.pacman.utils.Settings;
import com.pacman.view.menus.MenuOption;
import com.pacman.view.views.GameView;
import com.pacman.view.views.MainMenuView;
import com.pacman.view.views.ViewType;

public class Window implements WindowListener, IWindow
{
    private JFrame frame = new JFrame(Settings.TITLE);
    private JPanel views =  new JPanel();
    private CardLayout layout = new CardLayout();
    	
	private GameView gameView;
	private MainMenuView mainMenuView;
	private ViewType currentView;
    
    public Window(IGame game)
    {
    	views.setLayout(layout);
    	
    	gameView = new GameView(game);
    	mainMenuView = new MainMenuView();
    
    	views.add(gameView, gameView.getName());
    	views.add(mainMenuView, mainMenuView.getName());
    	
        frame.setMinimumSize(new Dimension(Settings.MIN_WINDOW_WIDTH, Settings.MIN_WINDOW_HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.add(views);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(this);
    }

    @Override
    public void showView(ViewType type)
    {
    	layout.show(views, type.getValue());
    	currentView = type;
    }
    
    @Override
    public void addListener(KeyListener k)
    {
    	frame.addKeyListener(k);
    }
    
    @Override
    public void render()
    {
    	if (frame.getWidth() != 0 && (frame.getHeight() != 0)) { frame.repaint(); }
    }

	@Override
	public void dispose() 
	{
		frame.dispose();
	}

    @Override
	public ViewType getCurrentView() 
	{
		return currentView;
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
	
    public JFrame getFrame()
    {
        return frame;
    }

	@Override
	public void setMainMenu() 
	{
		mainMenuView.setMainMenu();
	}

	@Override
	public void setAudioMenu() 
	{
		mainMenuView.setAudioMenu();
	}

	@Override
	public void setHelpMenu() 
	{
		mainMenuView.setHelpMenu();
	}
	
	@Override
	public MenuOption getMenuOption() 
	{
		return mainMenuView.getMenuOption();
	}

	@Override
	public void nextOption()
	{
		mainMenuView.nextOption();
	}

	@Override
	public void previousOption() 
	{
		mainMenuView.previousOption();
	}

	@Override
	public void setIsGameActive(boolean state) 
	{
		mainMenuView.setIsGameActive(state);
	}
}
