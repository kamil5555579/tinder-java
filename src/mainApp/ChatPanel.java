package mainApp;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mysql.jdbc.Connection;

import utilities.SqlConnection;
import utilities.User;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

public class ChatPanel extends JPanel {
	
	private JButton buttonSwipe, buttonSettings;
	SqlConnection sqlConn = new SqlConnection();
	private Connection conn;
	User current;
	List<User> users = new ArrayList<User>();
	Iterator<User> it = null;
	int id;
	ConversationPanel conPanel;
	JComboBox<User> comboBox;
	JScrollPane listScrollPane;
	DefaultListModel listaElementy;
	JList lista;

	private JLabel lblChat,textChat;
	
	
	public ChatPanel(JPanel panel,  int id) {
		
		lblChat = new JLabel("Wiadomości");
		lblChat.setForeground(new Color(255, 100, 153));
		lblChat.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 30));
		lblChat.setBounds(350, 0, 200, 75);
		add(lblChat);
			
			this.id=id;
			
			//ustawienia panelu
				
			setBounds(0, 0, 900, 800);
			//setBackground(new Color(255, 153, 204));
			setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
			
			//setLayout(null);
			initializeOthers(id);
			
			
			// przycisk przejscia do ustawień
			/*
			buttonSettings = new JButton("Ustawienia");
			buttonSettings.setBorder(null);
			buttonSettings.setBackground(new Color(255, 240, 245));
			buttonSettings.setFont(new Font("Dialog", Font.BOLD, 16));
			buttonSettings.setBounds(550, 50, 300, 50);;
			
			buttonSettings.addActionListener( new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                CardLayout cardLayout = (CardLayout) panel.getLayout();
	                cardLayout.previous(panel);
	            }
	        });
	        
	        
	        add(buttonSettings);
	        */
			setLayout(null);
			
			buttonSwipe = new JButton();
			buttonSwipe.setBorder(null);
			buttonSwipe.setBackground(new Color(0, 0, 0,0));
		
			buttonSwipe.setBounds(800, 0, 100, 100);

			try {
			    Image img = ImageIO.read(getClass().getResource("back2.png"));
			    buttonSwipe.setIcon(new ImageIcon(img));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
			
			buttonSwipe.addActionListener( new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                CardLayout cardLayout = (CardLayout) panel.getLayout();
	                cardLayout.next(panel);
	            }
	        });
			add(buttonSwipe);
		    
			/*
			 textChat = new JLabel();
			 textChat.setHorizontalAlignment(SwingConstants.CENTER);
			 textChat.setText("Czat");
			 textChat.setBounds(300, 0, 300, 50);
			 textChat.setForeground(Color.WHITE);
			 textChat.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 50));
		     add(textChat);
		     */
			
		    // wybór osoby do czatowania
			listaElementy = new DefaultListModel();
			lista = new JList(listaElementy);
			lista.setFont(new Font("LM Sans 10", Font.ITALIC, 16));
		     
			 JScrollPane listScrollPane = new JScrollPane(lista);
			 listScrollPane.setBounds(30, 110, 175, 600);
			 listScrollPane.setBorder(null);
			 
			 add(listScrollPane);
			 lista.addListSelectionListener(new ListSelectionListener() {
				    @Override
				    public void valueChanged(ListSelectionEvent e)
				    {	
				    	if(conPanel == null) {
					        if(!e.getValueIsAdjusting()) {
					            conPanel = new ConversationPanel(id, (User) lista.getSelectedValue());
					            conPanel.setBounds(225, 100, 650, 625);
					            add(conPanel);
					        } }
					        else {
								conPanel.refresh();
								conPanel.setUser((User) lista.getSelectedValue());
					        }
					        
				    	}
				    

					
				});
			
		   /*
		    comboBox = new JComboBox<User>();

		    comboBox.setBackground(new Color(255, 240, 245));
		    comboBox.setFont(new Font("Dialog", Font.ITALIC, 12));
		    comboBox.setBounds(125, 65, 200, 25);

		    add(comboBox);
		    comboBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(conPanel == null)
					{
						conPanel = new ConversationPanel(id, (User) comboBox.getSelectedItem());

	    				conPanel.setBounds(125, 100, 650, 625);

	    				add(conPanel);
					}
					else
						conPanel.refresh();
						conPanel.setUser((User) comboBox.getSelectedItem());
					
				}
			});
		   */ 
		  
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
	    				listaElementy.addElement(it.next());
	    				//comboBox.addItem(it.next());
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

	      Rectangle2D r2=new Rectangle2D.Double(0,0,getWidth(),getHeight());

	      Color c0=new Color(245, 245, 245), c1= new Color(255, 240, 245);

	      GradientPaint  gp = new GradientPaint(150, 200, c1, 450, 200, c0, false);
	      g2.setPaint(gp);
	      g2.fill(r2);
	      
	      g2.setPaint(new Color(240, 240, 240)); //szary
	 
	      g2.fill(new RoundRectangle2D.Double(220, 95, 660, 635, 40, 40));
	      
	      g2.setPaint(Color.WHITE); 
	      
	      g2.fill(new RoundRectangle2D.Double(25, 95, 195, 635, 40, 40));
	      /*
	      g2.setPaint(new Color(255, 240, 245)); // jasnorozowy
	      g2.fill(new RoundRectangle2D.Double(100, 420, 230, 50, 40, 40));
	      g2.fill(new RoundRectangle2D.Double(355, 420, 230, 50, 40, 40));
	      */
	     }
}


