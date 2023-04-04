package loginSystem;

import java.awt.BasicStroke;
import java.awt.CardLayout;
import java.awt.Color;

import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
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
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.mysql.jdbc.Connection;

import mainApp.CardFrame2;
import utilities.PPasswordField;
import utilities.PTextField;
import utilities.SqlConnection;
import java.awt.SystemColor;
import javax.swing.UIManager;


public class LoginPanel extends JPanel 
{

	 private PTextField txtUsername;
	 private PPasswordField pwdPassword;
	 private JButton btnRegister, button;
	 private JLabel lblTinder;
	 private JPanel panel;
	 private SqlConnection sqlConn = new SqlConnection();
	 private Connection conn;

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
			setBorder(new LineBorder(new Color(255, 20, 147), 5, true));
			setLayout(null);
			
			//label Tinder
			
			lblTinder = new JLabel("Tinder");
			lblTinder.setForeground(Color.WHITE);
			lblTinder.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 50));
			lblTinder.setBounds(275, 12, 234, 131);
			add(lblTinder);

			//login
			
	    	/*panel_2 = new JPanel();
	    	panel_2.setBackground(new Color(255, 255, 255));
	    	panel_2.setBounds(105, 195, 470, 80);
			panel_2.setLayout(null);
			add(panel_2);
	    	*/
			
			txtUsername = new PTextField("Nazwa użytkownika");
			txtUsername.setBorder(null);
			txtUsername.setBackground(new Color(240, 240, 240));
			txtUsername.setFont(new Font("Dialog", Font.ITALIC, 18));
			txtUsername.setBounds(125, 250, 445, 55);
			txtUsername.setColumns(10);
			add(txtUsername);
			
			//hasło
			/*
			panel_1 = new JPanel();
			panel_1.setLayout(null);
			panel_1.setBackground(Color.WHITE);
			panel_1.setBounds(105, 305, 470, 80);
			add(panel_1);
			*/
			pwdPassword = new PPasswordField("Haslo");
			pwdPassword.setBorder(null);
			pwdPassword.setBackground(new Color(240, 240, 240));
			pwdPassword.setFont(new Font("Dialog", Font.ITALIC, 18));
			pwdPassword.setBounds(125, 330, 445, 55);
			add(pwdPassword);
			
			//przycisk logowania
			
			button = new JButton("Zaloguj");
			button.setBorder(null);
			button.setBackground(new Color(255, 240, 245));
			button.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
			button.setBounds(115, 423, 195, 45);
			add(button);
			
			button.addActionListener( new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e) {
					String username = txtUsername.getText();
					String password = String.valueOf(pwdPassword.getPassword());
					logIn(username, password, frame);
				}
		
			});
			
			//przejscie do rejestracji
			
			btnRegister = new JButton("Zarejestruj");
			btnRegister.setBorder(null);
			btnRegister.setBackground(new Color(255, 240, 245));
			btnRegister.setFont(new Font("Dialog", Font.BOLD, 16));
			btnRegister.setBounds(370, 423, 195, 45);
			
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
	    
	    public void logIn(String username, String password, JFrame frame) 
		{
	    	
        SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>(){
       	 
            @Override
            protected Integer doInBackground() throws Exception {
            	conn = sqlConn.connect();
    			PreparedStatement prep;

    			System.out.println(conn);
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
    				if (conn!= null)
    	    			conn.close();
    				frame.dispose();
                } catch (Exception ex) {
                	JOptionPane.showMessageDialog(
                            null,"Niepoprawny login lub hasło. Spróbuj ponownie.",
                            "Błąd logowania",
                            JOptionPane.ERROR_MESSAGE);            }
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
		      
		      g2.setPaint(new Color(240, 240, 240)); //szary
		 
		      g2.fill(new RoundRectangle2D.Double(100, 325, 485, 60, 40, 40));
		      g2.fill(new RoundRectangle2D.Double(100, 245, 485, 60, 40, 40));
		      
		      g2.setPaint(new Color(255, 240, 245)); // jasnorozowy
		      g2.fill(new RoundRectangle2D.Double(100, 420, 230, 50, 40, 40));
		      g2.fill(new RoundRectangle2D.Double(355, 420, 230, 50, 40, 40));
		      
		     }
		 }
