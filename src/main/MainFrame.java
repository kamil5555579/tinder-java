package main;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

	public MainFrame() throws HeadlessException {
		setSize(800,800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		ImagePanel panel = new ImagePanel();
		JButton button = new JButton("left");
		this.add(button, BorderLayout.PAGE_START);
		this.add(panel, BorderLayout.CENTER);
		
		// TODO Auto-generated constructor stub
	}

	public MainFrame(GraphicsConfiguration gc) {
		super(gc);
		// TODO Auto-generated constructor stub
	}

	public MainFrame(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public MainFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}

}
