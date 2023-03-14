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
		
		JLabel fnameLabel = new JLabel("Imie");
		mainPanel.add(fnameLabel);
		
		JTextField fnameText = new JTextField();
		mainPanel.add(fnameText);
		
		JLabel lnameLabel = new JLabel("Nazwisko");
		mainPanel.add(lnameLabel);
		
		JTextField lnameText = new JTextField();
		mainPanel.add(lnameText);
		
		JLabel loginLabel = new JLabel("Login");
		mainPanel.add(loginLabel);
		
		JTextField loginText = new JTextField();
		mainPanel.add(loginText);
		
		JLabel passwordLabel = new JLabel("Hasło");
		mainPanel.add(passwordLabel);
		
		JTextField passwordText = new JTextField();
		mainPanel.add(passwordText);
		
		JButton registerButton = new JButton("zarejestruj");
		mainPanel.add(registerButton);
		registerButton.addActionListener( new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						String fname = fnameText.getText();
						String lname = lnameText.getText();
						String username = loginText.getText();
						String password = passwordText.getText();
						try {
							registerIn(fname, lname, username, password);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
			
				});
		
		
	}
	
	public void registerIn(String fname, String lname, String username, String password) throws SQLException
	{
		Connection conn = sqlConn.connect();
		PreparedStatement prep;

		prep = conn.prepareStatement("INSERT INTO users ( firstname, lastname, username, password) VALUES (?,?,?,?)");
		prep.setString(1, fname);
		prep.setString(2, lname);
		prep.setString(3, username);
		prep.setString(4, password);
		prep.executeUpdate();
		dispose();
		LoginFrame newFrame = new LoginFrame();
		newFrame.setVisible(true);
		
	}

	}


