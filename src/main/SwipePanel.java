package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class SwipePanel extends JPanel {

		 private JTextField txtUsername;
		 private JPanel panel_1, panel_2;
		 private JPasswordField pwdPassword;
		 private JButton btnRegister, button;
		 private JLabel lblTinder, lblX;

		    public SwipePanel(JPanel panel) 
		    {
		    	//ustawienia panelu
		    	
		    	setBounds(100, 100, 1000, 1000);
				setBackground(new Color(255, 105, 180));
				setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
				setLayout(null);

				
				
				//label Tinder
				
				lblTinder = new JLabel("Tinder");
				lblTinder.setForeground(Color.WHITE);
				lblTinder.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 30));
				lblTinder.setBounds(225, 25, 101, 50);
				add(lblTinder);
	
		        
		        // wyłącznik programu
		        
		        lblX = new JLabel("X");
				lblX.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(JOptionPane.showConfirmDialog(null, "Are you sure you want to close this application?", "Confirmation", JOptionPane.YES_NO_OPTION)==0){
							System.exit(ABORT);
						}
					}
				});
				
				lblX.setForeground(new Color(255, 255, 255));
				lblX.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
				lblX.setBounds(578, 3, 11, 17);
				add(lblX);
		        
		    }  

}
