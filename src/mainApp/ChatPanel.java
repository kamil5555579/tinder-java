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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.Box;
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
import java.awt.BorderLayout;

public class ChatPanel extends JPanel {
	
	private JButton buttonSwipe;
	SqlConnection sqlConn = new SqlConnection();
	private Connection conn;
	Connection conn2;
	User current;
	List<User> users = new ArrayList<User>();
	Iterator<User> it = null;
	JLabel label2;
	Box vertical = Box.createVerticalBox();
	int id;
	final ScheduledExecutorService scheduler = 
		       Executors.newScheduledThreadPool(2);
	private JPanel panel_1;
	
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
			buttonSwipe.setBounds(368, 8, 83, 29);
			buttonSwipe.addActionListener( new ActionListener()
		       {
		         public void actionPerformed(ActionEvent e)
		          {
		              CardLayout cardLayout = (CardLayout) panel.getLayout();
		              cardLayout.next(panel);
		          }
		       });
		    setLayout(null);
		    add(buttonSwipe);
		    buttonSwipe.setVisible(true);
		    
		    
		    label2 = new JLabel();
		    label2.setText("czat z:");
		    label2.setBounds(378, 47, 83, 16);
			add(label2);
			
			panel_1 = new JPanel();
			panel_1.setBounds(372, 73, 259, 362);
			add(panel_1);
			panel_1.setLayout(new BorderLayout(0, 0));
			panel_1.add(vertical, BorderLayout.PAGE_START);
			vertical.add(Box.createVerticalStrut(15));

			
			// wiadomość
			JTextField msgField = new JTextField("");
			msgField.setBounds(466, 16, 96, 19);
			add(msgField);
		
			JButton sendBtn = new JButton("Wyślij");
			sendBtn.setBounds(572, 15, 59, 21);
			add(sendBtn);
			sendBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					sendMsg(msgField.getText());
					msgField.setText("");
					
				}
			});
			
			scheduler.scheduleAtFixedRate((new Runnable() {

				Timestamp last = new Timestamp(0);
				
				@Override
				public void run() {
	    				if(current!=null)
	    				{
	    					conn2 = sqlConn.connect();
			    			try {
		    				PreparedStatement prep;
							prep = conn2.prepareStatement("SELECT * FROM messages WHERE ((sender_id=(?) AND receiver_id=(?)) OR (sender_id=(?) AND receiver_id=(?))) AND date > (?)");
							prep.setInt(1, id);
			    			prep.setInt(2, current.getId());
			    			prep.setInt(3, current.getId());
			    			prep.setInt(4, id);
			    			prep.setTimestamp(5, last);
			    			ResultSet rs = prep.executeQuery();
			    			while(rs.next())
			    			{
			    				String text = rs.getString("text");
			    				JLabel output = new JLabel(text);
			    				JPanel textPanel = new JPanel();
			    				textPanel.add(output);
			    				if(rs.getInt("sender_id")==id)
			    				{
			    					JPanel right = new JPanel(new BorderLayout());
				    				vertical.add(right);
			    					right.add(textPanel, BorderLayout.LINE_END);
			    				}
			    				else
			    				{
			    					JPanel left = new JPanel(new BorderLayout());
				    				vertical.add(left);
				    				left.add(textPanel, BorderLayout.LINE_START);
			    				}
			    				repaint();
			    				invalidate();
			    				validate();
				    			last = rs.getTimestamp("date");
			    			}
			    			
			    			if(conn2!=null)
		    					conn2.close();
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
	    				label2.setText(label2.getText() + current.getFirstname());
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
    			prep = conn.prepareStatement("INSERT INTO messages (sender_id, receiver_id, text) VALUES (?,?,?)");
    			prep.setInt(1, id);
    			prep.setInt(2, current.getId());
    			prep.setString(3, text);
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

}
