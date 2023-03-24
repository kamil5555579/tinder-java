package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class SwipePanel extends JPanel {

	
	private BufferedImage bufferedImage;
	private Image image;
	double x,y,fi;
	int imgWidth=400;
	int imgHeight=500;
	int panelWidth;
	int panelHeight;
	Timer slideTimer = new Timer();

	private JButton buttonChat, buttonSettings;
	private JLabel lblTinder;

		    public SwipePanel(JPanel panel, JFrame frame) 
		    {
		    	super();
		    
		    	setBounds(100, 100, 1000, 1000);
				setBackground(new Color(225, 85, 160));
				setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
				setLayout(null);
				
				panelWidth=getWidth();
				panelHeight=getHeight();
				
				lblTinder = new JLabel("Tinder");
				lblTinder.setForeground(new Color(255, 100, 153));
				lblTinder.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 50));
				lblTinder.setBounds(400, 20, 151, 72);
				add(lblTinder);
	
		        
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

				setSize(panelWidth, panelHeight);
				x = this.getWidth()/2 - imgWidth/2;
				y = this.getHeight()/2 - imgHeight/2;
				fi=0;
				System.out.println(x);
				System.out.println(y);
				URL resource = getClass().getResource("zdjecie.jpg");
				try {
				bufferedImage = ImageIO.read(resource);
				image = bufferedImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_DEFAULT);
				bufferedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
				bufferedImage.getGraphics().drawImage(image, 0, 0 , null);
				} catch (IOException e) {
				System.err.println("Blad odczytu obrazka");
				e.printStackTrace();
				}
				addMouseMotionListener(new MouseMotionListener() {
					
					@Override
					public void mouseMoved(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseDragged(MouseEvent e) {
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
					}
				});
				
				addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						if(x >= panelWidth-imgWidth)
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
						                		 y=panelHeight/2- imgHeight/2;
						                		 fi=0;
						 						repaint();
						                		 cancel();
						                	 }
						                 }
						             });
								 }

								 }, 0,1);
							
						}
						else if(x <=0)
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
						                		 y=panelHeight/2- imgHeight/2;
						                		 fi=0;
						 						repaint();
						                		 cancel();
						                	 }
						                 }
						             });
								 }
								 }, 0,1);
						else
						{
							 x = panelWidth/2 - imgWidth/2;
		           		 y=panelHeight/2- imgHeight/2;
		           		 fi=0;
							repaint();
						}
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
		       
		        
		    }  
		    
		    public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.clearRect(0, 0, panelWidth, panelHeight);
				//bufferedImage = rotate(bufferedImage, 45.0);
				//bufferedImage = rotateCw(bufferedImage);
				double rotationRequired = Math.toRadians (fi);
				double locationX = image.getWidth(null) / 2;
				double locationY = image.getHeight(null) / 2;
				AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
				
				// Drawing the rotated image at the required drawing locations
				g2d.drawImage(op.filter(bufferedImage, null), (int) x, (int) y, null);
				//g2d.drawImage(bufferedImage, x, y, this);
				
				}
			
			public static BufferedImage rotate(BufferedImage bimg, Double angle) {
			    double sin = Math.abs(Math.sin(Math.toRadians(angle))),
			           cos = Math.abs(Math.cos(Math.toRadians(angle)));
			    int w = bimg.getWidth();
			    int h = bimg.getHeight();
			    int neww = (int) Math.floor(w*cos + h*sin),
			        newh = (int) Math.floor(h*cos + w*sin);
			    BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
			    Graphics2D graphic = rotated.createGraphics();
			    graphic.translate((neww-w)/2, (newh-h)/2);
			    graphic.rotate(Math.toRadians(angle), w/2, h/2);
			    graphic.drawRenderedImage(bimg, null);
			    graphic.dispose();
			   
			    return rotated;
			}
			
			public static BufferedImage rotateCw( BufferedImage img )
			{
			    int         width  = img.getWidth();
			    int         height = img.getHeight();
			    BufferedImage   newImage = new BufferedImage( height, width, img.getType() );

			    for( int i=0 ; i < width ; i++ )
			        for( int j=0 ; j < height ; j++ )
			            newImage.setRGB( height-1-j, i, img.getRGB(i,j) );

			    return newImage;
			}	    

}
