package main;

import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.jdbc.Connection;

public class RegisterFrame extends JFrame {

	SqlConnection sqlConn = new SqlConnection();
	public RegisterFrame() throws HeadlessException {
		
		setSize(400,400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(6,1));
		add(mainPanel);
		
		JLabel loginLabel = new JLabel("Login");
		mainPanel.add(loginLabel);
		
		JTextField loginText = new JTextField();
		mainPanel.add(loginText);
		
		JLabel passwordLabel = new JLabel("Hasło");
		mainPanel.add(passwordLabel);
		
		JTextField passwordText = new JTextField();
		mainPanel.add(passwordText);
		
		JLabel password2Label = new JLabel("Powtórz Hasło");
		mainPanel.add(password2Label);
		
		JTextField password2Text = new JTextField();
		mainPanel.add(password2Text);
		
		JButton registerButton = new JButton("zarejestruj");
		mainPanel.add(registerButton);
		registerButton.addActionListener( new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						String username = loginText.getText();
						String password = passwordText.getText();
						try {
							registerIn(username, password);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
			
				});
		
		
	}
	
	public void registerIn(String username, String password) throws SQLException
	{
		Connection conn = sqlConn.connect();
		PreparedStatement prep;

		prep = conn.prepareStatement("INSERT INTO users ( username, password) VALUES (?,?)");
		prep.setString(1, username);
		prep.setString(2, password);
		prep.executeUpdate();
		
		prep = conn.prepareStatement("SELECT * FROM users WHERE username =(?) AND password =(?)");
		prep.setString(1, username);
		prep.setString(2, password);
		ResultSet rs = prep.executeQuery();
		if (rs.next())
		{
			dispose();
			DataFrame newFrame = new DataFrame((int) rs.getObject("id"));
			newFrame.setVisible(true);
		}
		
	}

	}


