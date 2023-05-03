package mainApp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Transparency;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.mysql.jdbc.Connection;

import utilities.SqlConnection;
import utilities.User;

public class ImagePanel extends JPanel {

	private BufferedImage bufferedImage;
	double x,y,fi;
	int imgWidth=400;
	int imgHeight=400;
	int panelWidth=900;
	int panelHeight=800;
	Timer slideTimer = new Timer();
	SqlConnection sqlConn = new SqlConnection();
	private Connection conn;
	User current;
	List<User> users = new ArrayList<User>();
	Iterator<User> it = null;
	int id;
	SwipePanel panel;
	boolean running;
	
	public ImagePanel(int id, Iterator<User> it, SwipePanel panel) {
		
		this.id = id;
		this.it=it;
		if(it.hasNext())
			current=it.next();
		this.panel = panel;
		setSize(900,800);
		x = this.getWidth()/2 - imgWidth/2;
		y = this.getHeight()/2 - imgHeight/2-50;
		fi=0;
		setImage(current.getImage());
		repaint();
		
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
		
		
		
	}
	  public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			panel.repaint();
			panel.validate();
	    	if(bufferedImage!=null)
	    	{	
				g2d.rotate(Math.toRadians(fi), (int) x + imgWidth / 2, (int) y + imgHeight / 2);
				g2d.drawImage(bufferedImage, (int) x, (int) y, null);
				
				g2d.setPaint(new Color(255, 255, 255)); 
				g2d.fillRect((int) x, (int) y + imgHeight, imgWidth , 75);
				
				g2d.setPaint(new Color(0, 0, 0)); 
				g2d.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 10));
				g2d.drawString(current.getFirstname()+" "+current.getLastname(), (int) x, (int) y + imgHeight + 20);
				g2d.drawString(current.getGender()+" ", (int) x, (int) y + imgHeight + 40);
				g2d.drawString(Integer.toString(current.getAge())+" lat", (int) x + 100, (int) y + imgHeight + 40);
				g2d.drawString("Studiuje "+current.getUniversity(), (int) x, (int) y + imgHeight + 60);
	    	}
	    	else
	    		g2d.drawString("Nie ma więcej :(",(int) x+(imgWidth/4), (int) y+(imgHeight/4));
			}
	  
	
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
		running = true;
		slideTimer.schedule(new TimerTask() {
			 public void run() {
				 SwingUtilities.invokeLater(new Runnable() {  //InvokeLater
	                 @Override
	                 public void run() {
	                	 if(running)
	                	 {
		                	 x+=1;
		                	 y-=0.5;
		                	 fi+=0.25;
		                	 repaint();
		                	 if(x>=panelWidth)
		                	 {
		                		 x = panelWidth/2 - imgWidth/2;
		                		 y = panelHeight/2- imgHeight/2 - 50;
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
		                		running=false;
		                		cancel();
		 						repaint();
	                	 }

	                	 }
	                 }
	             });
			 }

			 }, 0,1);
	}
	
	// animacja lotu w lewo
	
	void goLeft()
	{
		running = true;
		slideTimer.schedule(new TimerTask() {
			 public void run() {
				 SwingUtilities.invokeLater(new Runnable() {  //InvokeLater
	                 @Override
	                 public void run() {
	                	 if(running)
	                	 {
		                	 x-=1;
		                	 y-=0.5;
		                	 fi-=0.25;
		                	 repaint();
		                	 if(x<=-imgWidth)
		                	 {
		                		 x = panelWidth/2 - imgWidth/2;
		                		 y = panelHeight/2- imgHeight/2 - 50;
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
		                		running=false;
		                		cancel();
			 					repaint();
		                	 }
	                	 }
	                 }
	             });
			 }
			 }, 0,1);
	}

	void setImage(Image image)
	{
		bufferedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
		bufferedImage.getGraphics().drawImage(image, 0, 0 , null);
	}
	
	BufferedImage getImage()
	{
		return bufferedImage;
	}
}

