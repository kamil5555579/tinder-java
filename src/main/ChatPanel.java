package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ChatPanel extends JPanel {
	
	private JButton buttonSwipe;
	
	public ChatPanel(JPanel panel) {
			
				setBounds(100, 100, 1000, 1000);
				//setBackground(new Color(255, 153, 204));
				setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
				//setLayout(null);
				
				buttonSwipe = new JButton("Return");
				buttonSwipe.setBackground(new Color(255, 240, 245));
				buttonSwipe.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
				buttonSwipe.setBounds(350, 865, 225, 65);
				buttonSwipe.addActionListener( new ActionListener()
		        {
		            public void actionPerformed(ActionEvent e)
		            {
		                CardLayout cardLayout = (CardLayout) panel.getLayout();
		                cardLayout.next(panel);
		            }
		        });
		      	add(buttonSwipe);
		      	buttonSwipe.setVisible(true);
		       
		        
	}
	  
}
