package mainApp;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
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
	private JLabel lblTinder, lblWait;
	ImagePanel imgPanel;
	JProgressBar progressBar;
	private BufferedImage imageSettings, imageChat, imageReject, imageMatch;
	private Rectangle rectangleChat, rectangleSettings, rectangleMatch, rectangleReject;

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
				
				try {
				    imageChat = ImageIO.read(getClass().getResource("fly3.png"));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
				rectangleChat = new Rectangle(50, 25, 100, 100);
				addMouseListener(new MouseAdapter() {
					
					@Override
					 public void mouseClicked(MouseEvent e) {
						if (e.getX()>rectangleChat.getX() && e.getX()<(rectangleChat.getX()+rectangleChat.getWidth()) && e.getY()>rectangleChat.getY() && e.getY()<(rectangleChat.getHeight()+rectangleChat.getY()))
						{
							CardLayout cardLayout = (CardLayout) panel.getLayout();
							cardLayout.previous(panel);
						}
						repaint();
			        }
				});
				// przycisk przejscia do ustawień
				
				
				try {
				    imageSettings = ImageIO.read(getClass().getResource("settings.png"));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
				rectangleSettings = new Rectangle(750,25,100,100);
				addMouseListener(new MouseAdapter() {
					
					@Override
					 public void mouseClicked(MouseEvent e) {
						if (e.getX()>rectangleSettings.getX() && e.getX()<(rectangleSettings.getX()+rectangleSettings.getWidth()) && e.getY()>rectangleSettings.getY() && e.getY()<(rectangleSettings.getHeight()+rectangleSettings.getY()))
						{
							CardLayout cardLayout = (CardLayout) panel.getLayout();
							cardLayout.next(panel);
						}
						repaint();
			        }
				});
			
		      
				// przycisk reject

				try {
				    imageReject = ImageIO.read(getClass().getResource("break.png"));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
				rectangleReject = new Rectangle (300,650,100,100);
				addMouseListener(new MouseAdapter() {
					
					@Override
					 public void mouseClicked(MouseEvent e) {
						 Point point = e.getPoint();
						 if(rectangleReject.contains(point)) {
							 if (imgPanel!=null && imgPanel.getImage()!=null)
									imgPanel.goLeft();
						}
					 }
				});
				
				
				// przycisk match
				
				try {
				    imageMatch = ImageIO.read(getClass().getResource("full.png"));
				  } catch (Exception ex) {
				    System.out.println(ex);
				  }
				
				rectangleMatch = new Rectangle (500,650,100,100);
				addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseClicked(MouseEvent e) {
						Point pointMatch = e.getPoint();
						if(rectangleMatch.contains(pointMatch)) {
							if (imgPanel!=null && imgPanel.getImage()!=null)
							imgPanel.goRight();	
						}
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
			    				imgPanel.setBounds(0, 125, 900, 550);
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



public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    Graphics2D g2 = (Graphics2D)g;
    
    g2.drawImage(imageSettings ,(int) rectangleSettings.getX() , (int) rectangleSettings.getY(),(int) rectangleSettings.getWidth(), (int) rectangleSettings.getHeight(), null);
    g2.drawImage(imageChat ,(int) rectangleChat.getX() , (int) rectangleChat.getY(),(int) rectangleChat.getWidth(), (int) rectangleChat.getHeight(), null);
    g2.drawImage(imageReject ,(int) rectangleReject.getX() , (int) rectangleReject.getY(),(int) rectangleReject.getWidth(), (int) rectangleReject.getHeight(), null);
    g2.drawImage(imageMatch ,(int) rectangleMatch.getX() , (int) rectangleMatch.getY(),(int) rectangleMatch.getWidth(), (int) rectangleMatch.getHeight(), null);
   }

}
