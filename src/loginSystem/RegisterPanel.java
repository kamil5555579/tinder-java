package loginSystem;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;

import com.mysql.jdbc.Connection;

import utilities.PPasswordField;
import utilities.PTextField;
import utilities.SqlConnection;

class RegisterPanel extends JPanel 
{	
	private PTextField txtUsername;
	private JPanel panel_1,panel_2, panel_3;
	private PPasswordField password1;
	private JButton btnRegister;
	private PPasswordField password2;
	private JPanel panel;
	private SqlConnection sqlConn = new SqlConnection();
	private DataPanel data;
	private Connection conn;

	public RegisterPanel(JPanel panel, DataPanel data) {
		
		this.panel = panel;
		this.data = data;
		
		//ustawienia panelu
		
		setBounds(100, 100, 700, 600);
		setBackground(new Color(255, 105, 180));
		setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		setLayout(null);
		
		//login
		
		panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(105, 115, 470, 80);
		add(panel_2);
		
		txtUsername = new PTextField("Username");
		txtUsername.setFont(new Font("Dialog", Font.ITALIC, 14));
		txtUsername.setBounds(12, 12, 445, 55);
		txtUsername.setColumns(10);
		panel_2.add(txtUsername);
		
		//hasło
		
		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(105, 215, 470, 80);
		add(panel_1);
		
		password1 = new PPasswordField("Password");
		password1.setFont(new Font("Dialog", Font.ITALIC, 14));
		password1.setBounds(12, 12, 445, 55);
		panel_1.add(password1);
		
		//powtórz hasło
		
		panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(105, 315, 470, 80);
		add(panel_3);
				
		password2 = new PPasswordField("Password");
		password2.setFont(new Font("Dialog", Font.ITALIC, 14));
		password2.setBounds(12, 12, 445, 55);
		panel_3.add(password2);
		
		//label Register
		
		JLabel lblTinder = new JLabel("Register");
		lblTinder.setForeground(Color.WHITE);
		lblTinder.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 42));
		lblTinder.setBounds(255, 20, 157, 61);
		add(lblTinder);
		
		//wracanie do logowania
		
		JButton button = new JButton("Log in");
		button.setBackground(new Color(255, 240, 245));
		button.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		button.setBounds(105, 415, 225, 65);
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
		
		//przejście do uzupełniania danych
		
		btnRegister = new JButton("Register");
		btnRegister.setBackground(new Color(255, 240, 245));
		btnRegister.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		btnRegister.setBounds(350, 415, 225, 65);
		btnRegister.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {	
            	String username = txtUsername.getText();
				String password = String.valueOf(password1.getPassword());
				String pwd2 = String.valueOf(password2.getPassword());
				System.out.println(password);
				System.out.println(pwd2);
				if(pwd2.equals(password)) {
					registerIn(username, password);
				} else {
					
					JOptionPane.showMessageDialog(
                            null,"Niepoprawnie powtórzone hasło! Spróbuj ponownie.",
                            "Błąd rejestracji",
                            JOptionPane.ERROR_MESSAGE);
				}
			}
            
        });
		add(btnRegister);
		
		
		
		
	
	}
	
	// funkcja tworząca konto i przechodząca do uzupełniania danych
	
	

	
	
	public void registerIn(String username, String password) 
	{
		 SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>(){
	       	 
	            @Override
	            protected Integer doInBackground() throws Exception {
	            	conn = sqlConn.connect();
	    			PreparedStatement prep;

	    			prep = conn.prepareStatement("INSERT INTO users ( username, password) VALUES (?,?)");
	    			prep.setString(1, username);
	    			prep.setString(2, password);
	    			prep.executeUpdate();
	    			
	    			prep = conn.prepareStatement("SELECT * FROM users WHERE username =(?) AND password =(?)");
	    			prep.setString(1, username);
	    			prep.setString(2, password);
	    			ResultSet rs = prep.executeQuery();
	    			if (!rs.next()) throw new Exception();
	                return rs.getInt("id");
	            }

	            @Override
	            protected void done() {
	                try {
	                	data.setId(get());
	    				 CardLayout cardLayout = (CardLayout) panel.getLayout();
	    	             cardLayout.next(panel);
	    	             if (conn!= null)
	    	     			conn.close();
	                    
	                } catch (Exception ex) {
	                	JOptionPane.showMessageDialog(
	                            null,"Nazwa użytkownika zajęta. Wybierz inną nazwę użytkownika.",
	                            "Błąd rejestracji",
	                            JOptionPane.ERROR_MESSAGE);
	                }
	            }

	       };
	       
	       worker.execute();
	}
	
	 public void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      
	      Graphics2D g2 = (Graphics2D)g;

	      Rectangle2D r2=new Rectangle2D.Double(0,0,getWidth(),getHeight());

	      Color c0=new Color(255,0,128), c1= new Color(255,128,0);

	      GradientPaint  gp = new GradientPaint(150, 200, c1, 450, 200, c0, false);
	      g2.setPaint(gp);
	      g2.fill(r2);
	      
	    
	     }
}