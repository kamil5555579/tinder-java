package mainApp;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;

import com.mysql.jdbc.Connection;

import utilities.SqlConnection;
import utilities.User;
import javax.swing.JProgressBar;

public class SwipePanel extends JPanel {

	
	private BufferedImage bufferedImage;
	double x,y,fi;
	int imgWidth=450;
	int imgHeight=450;
	int panelWidth;
	int panelHeight;
	Timer slideTimer = new Timer();
	
	SqlConnection sqlConn = new SqlConnection();
	private Connection conn;
	User me;
	User current;
	List<User> users = new ArrayList<User>();
	Iterator<User> it = null;
	int id;
	private JButton buttonChat, buttonSettings;
	private JLabel lblTinder, lblWait;
	private JButton reject,match;
	ImagePanel imgPanel;
	JProgressBar progressBar;

		    public SwipePanel(JPanel panel, int id) 
		    {
		    	super();
		    	this.id=id;
		    	
		    	//ustawienia panelu
		    	
		    	setBounds(0, 0, 900, 800);
				
				setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
				setLayout(null);
				
				
				// załadowanie osób
				
				//initializeMe(id); tutaj raczej niepotrzebne
				initializeOthers(id);
				
				// label Tinder
				
				lblTinder = new JLabel("Tinder");
				lblTinder.setForeground(new Color(255, 100, 153));
				lblTinder.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 50));
				lblTinder.setBounds(350, 25, 200, 100);
				add(lblTinder);
	
		        // przycisk przejscia do wiadomosci
				
				buttonChat = new JButton();
				buttonChat.setBorder(null);
				try {
				    Image img = ImageIO.read(getClass().getResource("fly3.png"));
				    buttonChat.setIcon(new ImageIcon(img));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
				buttonChat.setBounds(50, 25, 100, 100);
				buttonChat.setBackground(new Color(0,0,0,0));
				add(buttonChat);
				buttonChat.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
		            {
		                CardLayout cardLayout = (CardLayout) panel.getLayout();
		                cardLayout.previous(panel);
		            }
				});
				
				// przycisk przejscia do ustawień
				
				buttonSettings = new JButton();
				buttonSettings.setBorder(null);
				buttonSettings.setBackground(new Color(0, 0, 0,0));
				
				buttonSettings.setBounds(750, 25, 100, 100);
				try {
				    Image img4 = ImageIO.read(getClass().getResource("settings.png"));
				    buttonSettings.setIcon(new ImageIcon(img4));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
				buttonSettings.addActionListener( new ActionListener()
		        {
		            public void actionPerformed(ActionEvent e)
		            {
		                CardLayout cardLayout = (CardLayout) panel.getLayout();
		                cardLayout.next(panel);
		            }
		        });
				
		        add(buttonSettings);

				// przycisk reject

				reject = new JButton();
				reject.setBorder(null);
				try {
				    Image img = ImageIO.read(getClass().getResource("break.png"));
				    reject.setIcon(new ImageIcon(img));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
				reject.setBounds(300,650,100,100);
				reject.setBackground(new Color(0,0,0,0));
				add(reject);
				reject.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if (imgPanel!=null && imgPanel.getImage()!=null)
						imgPanel.goLeft();
						}
				});
				
				// przycisk match
				
				match = new JButton();
				match.setBorder(null);
				try {
				    Image img = ImageIO.read(getClass().getResource("full.png"));
				    match.setIcon(new ImageIcon(img));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
				match.setBounds(500,650,100,100);
				match.setBackground(new Color(0,0,0,0));
				add(match);
				match.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
					if (imgPanel!=null && imgPanel.getImage()!=null)
					imgPanel.goRight();
					}
				});
				
				// progress
				
				progressBar = new JProgressBar();
				progressBar.setForeground(new Color(255, 20, 147));
				progressBar.setBounds(350, 350, 203, 32);
				add(progressBar);
				progressBar.setIndeterminate(true);
						
				lblWait = new JLabel("Czekaj!!!");
				lblWait.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
				lblWait.setBounds(400, 325, 203, 20);
				add(lblWait);
				
		    }  
		    
		    
		    
			
		    
		    // funkcja ładująca dane użytkownika
			
			public void initializeMe(int id)
			{
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
			       	 
		            @Override
		            protected Void doInBackground() throws Exception {
		            	conn = sqlConn.connect();
		    			PreparedStatement prep;

		    			prep = conn.prepareStatement("SELECT * FROM userdata WHERE user_id =(?)");
		    			prep.setInt(1, id);
		    			ResultSet rs = prep.executeQuery();
		    			if (rs.next())
		    			{
		    				byte[] imageData = rs.getBytes("image");
		    				Image imageTemp = new ImageIcon(new ImageIcon(imageData).getImage().getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH)).getImage();
		    				me = new User(id, rs.getString("firstname"), rs.getString("lastname"), rs.getString("university"), rs.getString("gender"), rs.getInt("age"), imageTemp, rs.getString("description"));
		    			}
						return null;
		            }

		            @Override
		            protected void done() {
		                try {
		                	if (conn!= null)
		    	    			conn.close();
		                } catch (Exception ex) {
		                    ex.printStackTrace();
		                }
		            }

		       };
		       
		       worker.execute();
			}
			
			// funkcja ładująca innych użytkowników
			
			public void initializeOthers(int id)
			{
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
			       	 
		            @Override
		            protected Void doInBackground() throws Exception {
		            	conn = sqlConn.connect();
		    			PreparedStatement prep;

		    			prep = conn.prepareStatement("SELECT * FROM userdata WHERE user_id NOT IN (SELECT stranger_id FROM matches WHERE user_id = (?)) AND user_id !=(?)");
		    			prep.setInt(1, id);
		    			prep.setInt(2, id);
		    			ResultSet rs = prep.executeQuery();
		    			while(rs.next())
		    			{
		    				byte[] imageData = rs.getBytes("image");
		    				Image imageTemp = new ImageIcon(new ImageIcon(imageData).getImage().getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH)).getImage();
		    				users.add(new User(rs.getInt("user_id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("university"), rs.getString("gender"), rs.getInt("age"), imageTemp, rs.getString("description")));
		    			}
		    			
						return null;
		            }

		            @Override
		            protected void done() {
		                try {
		                	it = users.listIterator();
			    			if(it.hasNext())
							{
			    				imgPanel = new ImagePanel(id, it, SwipePanel.this);
			    				imgPanel.setBounds(0, 0, 900, 800);
			    				add(imgPanel);
			    				progressBar.setVisible(false);
			    				lblWait.setVisible(false);
								repaint();
								
							}
			    			else
			    			{
			    				progressBar.setVisible(false);
			    				lblWait.setText("zadnych nowych osób");
			    			}
		                	if (conn!= null)
		    	    			conn.close();

		                } catch (Exception ex) {
		                    ex.printStackTrace();
		                }
		            }

		       };
		       
		       worker.execute();
			}
}