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
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class ChatPanel extends JPanel {
	
	private JButton buttonSwipe;
	SqlConnection sqlConn = new SqlConnection();
	private Connection conn;
	User current;
	List<User> users = new ArrayList<User>();
	Iterator<User> it = null;
	int id;
	ConversationPanel conPanel;
	JComboBox<User> comboBox;
	
	public ChatPanel(JPanel panel,  int id) {
			
			this.id=id;
			//ustawienia panelu
				
			setBounds(100, 100, 900, 900);
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
		    
		    // wybór osoby do czatowania
		    
		    comboBox = new JComboBox<User>();
		    comboBox.setBounds(103, 94, 124, 21);
		    add(comboBox);
		    comboBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(conPanel == null)
					{
						conPanel = new ConversationPanel(id, (User) comboBox.getSelectedItem());
	    				conPanel.setBounds(350, 100, 500, 500);
	    				add(conPanel);
					}
					else
						conPanel.refresh();
						conPanel.setUser((User) comboBox.getSelectedItem());
					
				}
			});
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
	    			while(it.hasNext())
					{
	    				comboBox.addItem(it.next());
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
