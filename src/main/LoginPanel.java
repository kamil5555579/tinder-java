package main;

import java.awt.BasicStroke;
import java.awt.CardLayout;
import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;

import com.mysql.jdbc.Connection;


public class LoginPanel extends JPanel 
{

	 private PTextField txtUsername;
	 private JPanel panel_1,panel_2;
	 private PPasswordField pwdPassword;
	 private JButton btnRegister, button;
	 private JLabel lblTinder;
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
	    	
	    	setBounds(100, 100, 700, 600);
			setBackground(new Color(255, 105, 180));
			setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
			setLayout(null);
			
			//label Tinder
			
			lblTinder = new JLabel("Tinder");
			lblTinder.setForeground(Color.WHITE);
			lblTinder.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 42));
			lblTinder.setBounds(274, 23, 127, 61);
			add(lblTinder);

			//login
			
	    	panel_2 = new JPanel();
	    	panel_2.setBackground(new Color(255, 255, 255));
	    	panel_2.setBounds(105, 195, 470, 80);
			panel_2.setLayout(null);
			add(panel_2);
	    	
			
			txtUsername = new PTextField("Username");
			txtUsername.setFont(new Font("Dialog", Font.ITALIC, 16));
			txtUsername.setBounds(12, 12, 445, 55);
			txtUsername.setColumns(10);
			panel_2.add(txtUsername);
			
			//hasło
			
			panel_1 = new JPanel();
			panel_1.setLayout(null);
			panel_1.setBackground(Color.WHITE);
			panel_1.setBounds(105, 305, 470, 80);
			add(panel_1);
			
			pwdPassword = new PPasswordField("Haslo");
			pwdPassword.setFont(new Font("Dialog", Font.ITALIC, 16));
			pwdPassword.setBounds(12, 12, 445, 55);
			panel_1.add(pwdPassword);
			
			//przycisk logowania
			
			button = new JButton("Log in");
			button.setBackground(new Color(255, 240, 245));
			button.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
			button.setBounds(105, 415, 225, 65);
			add(button);
			
			button.addActionListener( new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e) {
					String username = txtUsername.getText();
					String password = String.valueOf(pwdPassword.getPassword());
					logIn(username, password);
				}
		
			});
			
			//przejscie do rejestracji
			
			btnRegister = new JButton("Register");
			btnRegister.setBackground(new Color(255, 240, 245));
			btnRegister.setFont(new Font("Dialog", Font.BOLD, 16));
			btnRegister.setBounds(350, 415, 225, 65);
			
	        btnRegister.addActionListener( new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                CardLayout cardLayout = (CardLayout) panel.getLayout();
	                cardLayout.next(panel);
	            }
	        });
	        add(btnRegister);
	        
	      
	        
	    }  
	    
	    //funkcja sprawdzająca konto w bazie i logująca
	    
	    public void logIn(String username, String password) 
		{
	    	
        SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>(){
       	 
            @Override
            protected Integer doInBackground() throws Exception {
            	Connection conn = sqlConn.connect();
    			PreparedStatement prep;

    			prep = conn.prepareStatement("SELECT * FROM users WHERE username =(?) AND password =(?)");
    			prep.setString(1, username);
    			prep.setString(2, password);
    			ResultSet rs = prep.executeQuery();

    			if(!rs.next()) throw new Exception();
    			
                return rs.getInt("id");
            }

            @Override
            protected void done() {
                try {
                	CardFrame2 newFrame = new CardFrame2(get());
    				newFrame.setVisible(true);
                  
                } catch (Exception ex) {
                	JOptionPane.showMessageDialog(
                            null,"Uncorrect login or password. Try again.",
                            "Login o error",
                            JOptionPane.ERROR_MESSAGE);            }
            }

       };
       
       worker.execute();
		}
			
		/* 	
		 public void paint(Graphics g) {
		      super.paint(g);
		      
		      Graphics2D g2 = (Graphics2D)g;

		      //Rectangle2D r0=new Rectangle2D.Double(50,50,50,50);
		     // Rectangle2D r1=new Rectangle2D.Double(200,50,50,50);
		      Rectangle2D r2=new Rectangle2D.Double(0,0,getWidth(),getHeight());

		      Color c0=Color.magenta, c1=Color.orange;

		      //GradientPaint gp=new GradientPaint(0, 0, c0, 5, 5, c1, true);
		      //g2.setPaint(gp);
		      //g2.fill(r0);
		     //g2.setStroke(new BasicStroke(10));
		      //g2.draw(r1);

		      GradientPaint  gp = new GradientPaint(150, 200, c1, 450, 200, c0, false);
		      g2.setPaint(gp);
		      g2.fill(r2);
		     }*/
}
