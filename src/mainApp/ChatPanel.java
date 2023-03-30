package mainApp;

import static java.util.concurrent.TimeUnit.SECONDS;

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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;

import com.mysql.jdbc.Connection;

import utilities.SqlConnection;
import utilities.User;

public class ChatPanel extends JPanel {
	
	private JButton buttonSwipe;
	SqlConnection sqlConn = new SqlConnection();
	private Connection conn;
	Connection conn2 = sqlConn.connect();
	User current;
	List<User> users = new ArrayList<User>();
	Iterator<User> it = null;
	JLabel label,label2;
	int id;
	final ScheduledExecutorService scheduler = 
		       Executors.newScheduledThreadPool(2);
	
	public ChatPanel(JPanel panel, int id) {
			
			this.id=id;
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
		    
		    
		    label2 = new JLabel();
			add(label2);
			label2.setVisible(true);
			
			// wiadomość
			JTextField msgField = new JTextField("wiad");
			add(msgField);
		
			JButton sendBtn = new JButton("Wyślij");
			add(sendBtn);
			sendBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					sendMsg(msgField.getText());
					msgField.setText("");
					
				}
			});
			
			label = new JLabel("wiadomości \n");
			add(label);
			label.setVisible(true);
			
			scheduler.scheduleAtFixedRate((new Runnable() {

				@Override
				public void run() {
	    				System.out.println("biegne");
	    				if(current!=null)
	    				{
			    			try {
		    				PreparedStatement prep;
							prep = conn2.prepareStatement("SELECT * FROM messages WHERE (sender_id=(?) AND receiver_id=(?)) OR (sender_id=(?) AND receiver_id=(?))");
							prep.setInt(1, id);
			    			prep.setInt(2, current.getId());
			    			prep.setInt(3, current.getId());
			    			prep.setInt(4, id);
			    			ResultSet rs = prep.executeQuery();
			    			String conversation="";
			    			while(rs.next())
			    			{
			    				conversation+=rs.getString("text")+"\t";
			    			} 
			    			label.setText(conversation);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    				}
				}}), 0, 1, SECONDS);
		            
	}
	  
	// funkcja ładująca sparowanie osoby
	
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
	    			if(it.hasNext())
					{
	    				current = it.next();
	    				label2.setText(current.getFirstname());
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
	
	void sendMsg(String text)
	{
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
	       	 
            @Override
            protected Void doInBackground() throws Exception {
            	conn = sqlConn.connect();
    			PreparedStatement prep;
    			System.out.println("wyslano1");
    			prep = conn.prepareStatement("INSERT INTO messages (sender_id, receiver_id, text) VALUES (?,?,?)");
    			System.out.println("wyslano2");
    			prep.setInt(1, id);
    			prep.setInt(2, current.getId());
    			prep.setString(3, text);
    			prep.executeUpdate();
    			System.out.println("wyslano");
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

}
