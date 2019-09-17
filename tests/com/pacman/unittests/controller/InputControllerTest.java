package com.pacman.unittests.controller;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import com.pacman.view.Window;
import com.pacman.view.inputs.Input;

class InputControllerTest 
{
	static Input input;
	
	static Window window;
	static JFrame frame;
	
	@BeforeAll
	static void init()
	{
		window = Mockito.mock( Window.class );
		Mockito.when( window.getFrame() ).thenReturn( new JFrame() );
		input = new Input();
		frame = InputControllerTest.window.getFrame();
		frame.addKeyListener(input);
	}
}
