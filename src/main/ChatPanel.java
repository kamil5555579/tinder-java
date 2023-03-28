package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;

import com.mysql.jdbc.Connection;

public class ChatPanel extends JPanel {
	
	private JButton buttonSwipe;
	SqlConnection sqlConn = new SqlConnection();
	private Connection conn;
	User current;
	List<User> users = new ArrayList<User>();
	Iterator<User> it = null;
	JLabel label;
	
	public ChatPanel(JPanel panel, int id) {
			
			//ustawienia panelu
				
			setBounds(100, 100, 1000, 1000);
			//setBackground(new Color(255, 153, 204));
			setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
			//setLayout(null);
			initializeOthers(id);
			
			// powrót do okna głównego
				
			buttonSwipe = new JButton("Return");
			buttonSwipe.setBackground(new Color(255, 240, 245));
			buttonSwipe.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
			buttonSwipe.setBounds(350, 865, 225, 65);
			buttonSwipe.addActionListener( new ActionListener()
		       {
		         public void actionPerformed(ActionEvent e)
		          {
		              CardLayout cardLayout = (CardLayout) panel.getLayout();
		              cardLayout.next(panel);
		          }
		       });
		    add(buttonSwipe);
		    buttonSwipe.setVisible(true);
		    
		    label = new JLabel();
			add(label);
			label.setVisible(true);
		            
	}
	  
	public void initializeOthers(int id)
	{
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
	       	 
            @Override
            protected Void doInBackground() throws Exception {
            	conn = sqlConn.connect();
    			PreparedStatement prep;

    			prep = conn.prepareStatement("SELECT * FROM userdata WHERE user_id IN "
    					+ "(SELECT user_id FROM matches WHERE user_id IN "
    					+ "(SELECT stranger_id FROM matches WHERE user_id = (?) AND interest =(?)) AND interest =(?) AND stranger_id = (?))");
    			prep.setInt(1, id);
    			prep.setBoolean(2, true);
    			prep.setBoolean(3, true);
    			prep.setInt(4, id);
    			ResultSet rs = prep.executeQuery();
    			while(rs.next())
    			{
    				byte[] imageData = rs.getBytes("image");
    				Image imageTemp = new ImageIcon(new ImageIcon(imageData).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)).getImage();
    				users.add(new User(rs.getInt("user_id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("university"), rs.getString("gender"), rs.getInt("age"), imageTemp));
    			}
				return null;
            }

            @Override
            protected void done() {
                try {
                	it = users.listIterator();
	    			while(it.hasNext())
					{
	    				//System.out.println(it.next().getFirstname());
	    				label.setText(label.getText()+it.next().getFirstname());
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
