package mainApp;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
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
import javax.swing.DefaultListCellRenderer;
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
	
	private SqlConnection sqlConn = new SqlConnection();
	private Connection conn;
	private User current;
	private List<User> users = new ArrayList<User>();
	private Iterator<User> it = null;
	int id;
	private ConversationPanel conPanel;
	private DefaultListModel listaElementy;
	private BufferedImage imageChat;
	private JList<User> lista;
	private Rectangle rectangleChat = new Rectangle(810, 0, 70, 70);
	private JLabel labelChat;
	
	
	public ChatPanel(JPanel panel,  int id) {
			
			labelChat = new JLabel("Wiadomości");
			labelChat.setForeground(new Color(255, 100, 153));
			labelChat.setFont(new Font("LM Sans", Font.ITALIC, 30));
			labelChat.setBounds(350, 0, 200, 75);
			add(labelChat);
			
			this.id=id;
			
			//ustawienia panelu
				
			setBounds(0, 0, 900, 800);
			setBorder(new LineBorder(new Color(255, 20, 147), 3, true));

			initializeOthers(id);
			setLayout(null);

			try {
			    imageChat = ImageIO.read(getClass().getResource("arrow_chat.PNG"));
			  } catch (Exception ex) {
			    System.out.println(ex);
			  }
			
			addMouseListener(new MouseAdapter() {
				 public void mouseClicked(MouseEvent e) {
					if (e.getX()>rectangleChat.getX() && e.getX()<(rectangleChat.getX()+rectangleChat.getWidth()) && e.getY()>rectangleChat.getY() && e.getY()<(rectangleChat.getHeight()+rectangleChat.getY()))
					{
						CardLayout cardLayout = (CardLayout) panel.getLayout();
		                cardLayout.next(panel);
					}
					repaint();
		        }
			});
		    

		    // wybór osoby do czatowania
			listaElementy = new DefaultListModel<User>();
			lista = new JList<User>(listaElementy);
			lista.setFont(new Font("LM Sans", Font.ITALIC, 14));
			lista.setCellRenderer(new DefaultListCellRenderer() {
	            @Override
	            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
	                                                          boolean isSelected, boolean cellHasFocus) {
	                String name = ((User) value).getFirstname(); // Wyodrębnienie samej nazwy
	                return super.getListCellRendererComponent(list, name, index, isSelected, cellHasFocus);
	            }
	        });
		     
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
    				users.add(new User(rs.getInt("user_id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("university"), rs.getString("gender"), rs.getInt("age"), imageTemp, rs.getString("description")));
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

	      Color c0=new Color(255, 255, 255), c1= new Color(255, 215, 230);

	      GradientPaint  gp = new GradientPaint(150, 200, c1, 450, 200, c0, false);
	      g2.setPaint(gp);
	      g2.fill(r2);
	      
	      g2.setPaint(new Color(240, 240, 240)); //szary
	 
	      g2.fill(new RoundRectangle2D.Double(220, 95, 660, 635, 40, 40));
	      
	      g2.setPaint(Color.WHITE); 
	      
	      g2.fill(new RoundRectangle2D.Double(25, 95, 195, 635, 40, 40));
	      
	      g2.drawImage(imageChat ,(int) rectangleChat.getX() ,(int) rectangleChat.getY(), (int) rectangleChat.getWidth(), (int) rectangleChat.getHeight(), null);
	     }
}


