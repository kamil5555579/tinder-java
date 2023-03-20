package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
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

class RegisterPanel extends JPanel 
{	
	private JTextField txtUsername;
	private JPanel panel_1,panel_2, panel_3;
	private JPasswordField pwdPassword;
	private JButton btnRegister;
	private JPasswordField passwordField;

	public RegisterPanel(JPanel panel) {
		
		setBounds(100, 100, 600, 400);
		setBackground(new Color(255, 105, 180));
		setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		setLayout(null);
		
		panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(125, 92, 350, 43);
		add(panel_2);
		
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Dialog", Font.ITALIC, 14));
		txtUsername.setText("Username");
		txtUsername.setBounds(12, 12, 250, 19);
		txtUsername.setColumns(10);
		panel_2.add(txtUsername);
		
		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(125, 159, 350, 43);
		add(panel_1);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setFont(new Font("Dialog", Font.ITALIC, 14));
		pwdPassword.setText("Password");
		pwdPassword.setBounds(12, 12, 250, 19);
		panel_1.add(pwdPassword);
		
		JLabel lblTinder = new JLabel("Register");
		lblTinder.setForeground(Color.WHITE);
		lblTinder.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 30));
		lblTinder.setBounds(225, 25, 112, 43);
		add(lblTinder);
		
		
		JButton button = new JButton("Log in");
		button.setBackground(new Color(255, 240, 245));
		button.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		button.setBounds(124, 279, 170, 50);
		//add(button);
		button.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.previous(panel);
            }
        });
		add(button);
		
		btnRegister = new JButton("Register");
		btnRegister.setAlignmentY(0.0f);
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
		
		JLabel lblX = new JLabel("X");
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
		
		panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(125, 224, 350, 43);
		add(panel_3);
		
		passwordField = new JPasswordField();
		passwordField.setText("Password");
		passwordField.setFont(new Font("Dialog", Font.ITALIC, 14));
		passwordField.setBounds(12, 12, 250, 19);
		panel_3.add(passwordField);
	
	}
}