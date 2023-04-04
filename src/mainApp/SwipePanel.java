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

public class SwipePanel extends JPanel {

	
	private BufferedImage bufferedImage;
	double x,y,fi;
	int imgWidth=400;
	int imgHeight=400;
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
	private JLabel lblTinder;
	private JButton reject,match;

		    public SwipePanel(JPanel panel, JFrame frame, int id) 
		    {
		    	super();
		    	this.id=id;
		    	
		    	//ustawienia panelu
		    	
		    	setBounds(100, 100, 1000, 1000);
				//setBackground(new Color(225, 85, 160));
				setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
				setLayout(null);
				
				panelWidth=getWidth();
				panelHeight=getHeight();
				x = this.getWidth()/2 - imgWidth/2;
				y = this.getHeight()/2 - imgHeight/2;
				fi=0;
				
				// załadowanie osób
				
				//initializeMe(id); tutaj raczej niepotrzebne
				initializeOthers(id);
				
				// label Tinder
				
				lblTinder = new JLabel("Tinder");
				lblTinder.setForeground(new Color(255, 100, 153));
				lblTinder.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 50));
				lblTinder.setBounds(400, 20, 151, 72);
				add(lblTinder);
	
		        // przycisk przejscia do wiadomosci
				
				buttonChat = new JButton("Messages");
				buttonChat.setBackground(new Color(255, 240, 245));
				buttonChat.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
				buttonChat.setBounds(100, 800, 300, 65);
				buttonChat.addActionListener( new ActionListener()
		        {
		            public void actionPerformed(ActionEvent e)
		            {
		                CardLayout cardLayout = (CardLayout) panel.getLayout();
		                cardLayout.previous(panel);
		            }
		        });
				add(buttonChat);
				
				
				// przycisk przejscia do ustawień
				
				buttonSettings = new JButton("Settings");
				buttonSettings.setBackground(new Color(255, 240, 245));
				buttonSettings.setFont(new Font("Dialog", Font.BOLD, 16));
				buttonSettings.setBounds(600, 800, 300, 65);
				
				buttonSettings.addActionListener( new ActionListener()
		        {
		            public void actionPerformed(ActionEvent e)
		            {
		                CardLayout cardLayout = (CardLayout) panel.getLayout();
		                cardLayout.next(panel);
		            }
		        });
		        add(buttonSettings);

				// przesuwanie obrazka myszką
		        
		        addMouseMotionListener(new MouseMotionAdapter() {
		        	public void mouseDragged(MouseEvent e) {
		        		if(bufferedImage!=null)
		        		{
							x=e.getX()- imgWidth/2;
							
							if(e.getX() >= panelWidth/2)
							{
								y= (3*panelWidth/4 - 0.5*e.getX()- imgHeight/2);
								fi= (0.25*(e.getX() - panelWidth/2));
							}
							else
								y= (0.5*e.getX() +panelWidth/4 - imgHeight/2);
								fi= (0.25*(e.getX() - panelWidth/2));
							repaint();
		        		}}
				});
				
				//dalsza animacja obrazka
				
				addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						if(bufferedImage!=null)
		        		{
							if(x >= panelWidth-imgWidth)
								goRight();				
							else if(x <=0)
								goLeft();
							else
							{
								x = panelWidth/2 - imgWidth/2;
			           		 	y = panelHeight/2- imgHeight/2;
			           		 	fi=0;
			           		 	repaint();
							}
					}}
				});
		       
				// przycisk reject

				reject = new JButton("reject");
				reject.setBounds(300,740,100,50);
				add(reject);
				reject.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if (bufferedImage!=null)
						goLeft();
					}
				});
				
				// przycisk match
				
				match = new JButton("match");
				match.setBounds(450,740,100,50);
				add(match);
				match.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
					if (bufferedImage!=null)
					goRight();
					}
				});
				repaint();		   
				}  
		    
		    // rysowanie obrazka lub napisu
		    
		    public void paintComponent(Graphics g) {
		    	if(bufferedImage!=null)
		    	{
					Graphics2D g2d = (Graphics2D) g;
					g2d.clearRect(0, 0, panelWidth, panelHeight);
					double rotationRequired = Math.toRadians (fi);
					double locationX = imgWidth / 2;
					double locationY = imgHeight / 2;
					AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
					AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
					
					// Drawing the rotated image at the required drawing locations
					g2d.drawImage(op.filter(bufferedImage, null), (int) x, (int) y, null);
		    	}
		    	else
		    		g.drawChars(("Czekaj ładujemy osoby").toCharArray(), 0, 21, (int) x+(imgWidth/4), (int) y+(imgHeight/4));
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
		    				me = new User(id, rs.getString("firstname"), rs.getString("lastname"), rs.getString("university"), rs.getString("gender"), rs.getInt("age"), imageTemp);
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
		    				users.add(new User(rs.getInt("user_id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("university"), rs.getString("gender"), rs.getInt("age"), imageTemp));
		    			}
		    			
						return null;
		            }

		            @Override
		            protected void done() {
		                try {
		                	it = users.listIterator();
			    			if(it.hasNext())
							{
			    				current=it.next();
								setImage(current.getImage());
								repaint();
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
			
			// ustawienie zdjęcia
			
			void setImage(Image image)
			{
				bufferedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
				bufferedImage.getGraphics().drawImage(image, 0, 0 , null);
			}
			
			// dodanie matcha do bazy danych
			
			void match(User current, boolean decision)
			{
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
			       	 
		            @Override
		            protected Void doInBackground() throws Exception {
		            	conn = sqlConn.connect();
		    			PreparedStatement prep;
						prep = conn.prepareStatement("INSERT INTO matches (user_id, stranger_id, interest) VALUES (?,?,?)");
						prep.setInt(1, id);
						prep.setInt(2, current.getId());
						prep.setBoolean(3, decision);
						prep.executeUpdate();
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
			
			// animacja lotu w prawo
			
			void goRight()
			{
				slideTimer.schedule(new TimerTask() {
					 public void run() {
						 SwingUtilities.invokeLater(new Runnable() {  //InvokeLater
			                 @Override
			                 public void run() {
			                	 x+=1;
			                	 y-=0.5;
			                	 fi+=0.25;
			                	 repaint();
			                	 if(x>=panelWidth)
			                	 {
			                		 x = panelWidth/2 - imgWidth/2;
			                		 y = panelHeight/2- imgHeight/2;
			                		 fi=0;
			                		 if(it.hasNext())
			 						{
			             				match(current, true);
			             				current=it.next();
			 							setImage(current.getImage());
			 						}
			                		 else
			                		 {
			                			 match(current, true);
			                			 bufferedImage=null;
			                		 }
			                		 cancel();
			 						repaint();
			                	 }
			                 }
			             });
					 }

					 }, 0,1);
			}
			
			// animacja lotu w lewo
			
			void goLeft()
			{
				slideTimer.schedule(new TimerTask() {
					 public void run() {
						 SwingUtilities.invokeLater(new Runnable() {  //InvokeLater
			                 @Override
			                 public void run() {
			                	 x-=1;
			                	 y-=0.5;
			                	 fi-=0.25;
			                	 repaint();
			                	 if(x<=-imgWidth)
			                	 {
			                		 x = panelWidth/2 - imgWidth/2;
			                		 y = panelHeight/2- imgHeight/2;
			                		 fi=0;
			                		 if(it.hasNext())
			 						{
			                			match(current, false);
				             			current=it.next();
				 						setImage(current.getImage());
			 						}
			                		 else
			                		 {
			                			 match(current, false);
			                			 bufferedImage=null;
			                		 }
			                		 cancel();
				 					repaint();
			                	 }
			                 }
			             });
					 }
					 }, 0,1);
			}

}
