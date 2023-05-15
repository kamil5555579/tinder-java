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
import java.awt.geom.RoundRectangle2D;
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
		
		txtUsername = new PTextField("Nazwa użytkownika");
		txtUsername.setFont(new Font("Dialog", Font.ITALIC, 18));
		txtUsername.setBounds(125, 183, 445, 55);
		txtUsername.setBackground(new Color(240, 240, 240));
		txtUsername.setBorder(null);
		txtUsername.setColumns(10);
		add(txtUsername);
		
		//hasło
		
		password1 = new PPasswordField("Hasło");
		password1.setFont(new Font("Dialog", Font.ITALIC, 18));
		password1.setBackground(new Color(240, 240, 240));
		password1.setBorder(null);
		password1.setBounds(125, 265, 445, 55);
		add(password1);
		
		//powtórz hasło
				
		password2 = new PPasswordField("Powtórz hasło");
		password2.setFont(new Font("Dialog", Font.ITALIC, 18));
		password2.setBackground(new Color(240, 240, 240));
		password2.setBorder(null);
		password2.setBounds(125, 343, 445, 55);
		add(password2);
		
		//label Register
		
		JLabel lblTinder = new JLabel("Rejestracja");
		lblTinder.setForeground(Color.WHITE);
		lblTinder.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 42));
		lblTinder.setBounds(231, 22, 272, 89);
		add(lblTinder);
		
		//wracanie do logowania
		
		JButton button = new JButton("Zaloguj");
		button.setBackground(new Color(255, 240, 245));
		button.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		button.setBorder(null);
		button.setBounds(115, 423, 195, 45);
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
		
		btnRegister = new JButton("Zarejestruj");
		btnRegister.setBackground(new Color(255, 240, 245));
		btnRegister.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		btnRegister.setBounds(370, 423, 195, 45);
		btnRegister.setBorder(null);
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
	      
	      g2.setPaint(new Color(240, 240, 240)); //szary
	 
	      g2.fill(new RoundRectangle2D.Double(100, 340, 485, 60, 40, 40));
	      g2.fill(new RoundRectangle2D.Double(100, 260, 485, 60, 40, 40));
	      g2.fill(new RoundRectangle2D.Double(100, 180, 485, 60, 40, 40));
	      
	      g2.setPaint(new Color(255, 240, 245)); // jasnorozowy
	      g2.fill(new RoundRectangle2D.Double(100, 420, 230, 50, 40, 40));
	      g2.fill(new RoundRectangle2D.Double(355, 420, 230, 50, 40, 40));

	      
	    
	     }
}