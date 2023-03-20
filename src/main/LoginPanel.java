package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.mysql.jdbc.Connection;

public class LoginPanel extends JPanel 
{

	 private JTextField txtUsername;
	 private JPanel panel_1, panel_2;
	 private JPasswordField pwdPassword;
	 private JButton btnRegister, button;
	 private JLabel lblTinder, lblX;
	 private JPanel panel;
	 private SqlConnection sqlConn = new SqlConnection();
	 

	    public LoginPanel(JPanel panel, JFrame frame) 
	    {
	    	this.panel = panel;
	    	
	    	//żeby działały prompty
	    	
	    	frame.addWindowFocusListener(new WindowAdapter() {
	    	    public void windowGainedFocus(WindowEvent e) {
	    	        lblTinder.requestFocusInWindow();
	    	    }
	    	});
	    	
	    	//ustawienia panelu
	    	
	    	setBounds(100, 100, 600, 400);
			setBackground(new Color(255, 105, 180));
			setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
			setLayout(null);
			
			//label Tinder
			
			lblTinder = new JLabel("Tinder");
			lblTinder.setForeground(Color.WHITE);
			lblTinder.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 30));
			lblTinder.setBounds(225, 25, 101, 50);
			add(lblTinder);

			//login
			
	    	panel_2 = new JPanel();
	    	panel_2.setBackground(new Color(255, 255, 255));
	    	panel_2.setBounds(125, 92, 350, 66);
			panel_2.setLayout(null);
			add(panel_2);
	    	
			
			txtUsername = new PTextField("Username");
			txtUsername.setFont(new Font("Dialog", Font.ITALIC, 14));
			txtUsername.setBounds(12, 12, 250, 42);
			txtUsername.setColumns(10);
			panel_2.add(txtUsername);
			
			//hasło
			
			panel_1 = new JPanel();
			panel_1.setLayout(null);
			panel_1.setBackground(Color.WHITE);
			panel_1.setBounds(125, 183, 350, 66);
			add(panel_1);
			
			pwdPassword = new PPasswordField("Haslo");
			pwdPassword.setFont(new Font("Dialog", Font.ITALIC, 14));
			pwdPassword.setBounds(12, 12, 250, 42);
			panel_1.add(pwdPassword);
			/*
			char passwordChar = pwdPassword.getEchoChar();
			pwdPassword.setEchoChar ((char) 0);
			pwdPassword.setText("Enter password");
			pwdPassword.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				pwdPassword.setText("");
				pwdPassword.setEchoChar(passwordChar);
			}

			@Override
			public void focusLost(FocusEvent e) {
				
			}
			}); */

			
			//przycisk logowania
			
			button = new JButton("Log in");
			button.setBackground(new Color(255, 240, 245));
			button.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
			button.setBounds(124, 279, 170, 50);
			add(button);
			
			button.addActionListener( new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e) {
					String username = txtUsername.getText();
					String password = String.valueOf(pwdPassword.getPassword());
					try {
						logIn(username, password);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
		
			});
			
			//przejscie do rejestracji
			
			btnRegister = new JButton("Register");
			btnRegister.setBackground(new Color(255, 240, 245));
			btnRegister.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
			btnRegister.setBounds(306, 279, 170, 50);
			
	        btnRegister.addActionListener( new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                CardLayout cardLayout = (CardLayout) panel.getLayout();
	                cardLayout.next(panel);
	            }
	        });
	        add(btnRegister);
	        
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
	    
	    //funkcja sprawdzająca konto w bazie i logująca
	    
	    public void logIn(String username, String password) throws SQLException
		{
			Connection conn = sqlConn.connect();
			PreparedStatement prep;

			prep = conn.prepareStatement("SELECT * FROM users WHERE username =(?) AND password =(?)");
			prep.setString(1, username);
			prep.setString(2, password);
			ResultSet rs = prep.executeQuery();
			if (rs.next())
			{
				MainFrame newFrame = new MainFrame((int) rs.getObject("id"));
				newFrame.setVisible(true);
			}
			else
				System.out.println("zle");
		}


}
