package mainApp;

import static java.util.concurrent.TimeUnit.SECONDS;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mysql.jdbc.Connection;

import utilities.SqlConnection;
import utilities.User;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.SystemColor;


public class ConversationPanel extends JPanel {

	int id;
	User current;
	SqlConnection sqlConn = new SqlConnection();
	private Connection conn;
	Connection conn2;
	Connection conn3;
	JLabel label2;
	Box vertical = Box.createVerticalBox();
	final ScheduledExecutorService scheduler = 
		       Executors.newScheduledThreadPool(2);
	private JPanel panel_1,panel_2;
	Timestamp last = new Timestamp(0);
	JScrollBar verticalScrollBar;
	boolean scrollDown = true;
	int maxMsg = 6;
	Rectangle rectangleLoad;
	BufferedImage imageLoad;
	
	public ConversationPanel(int id, User current) {
		setBorder(null);
		
		
		this.id = id;
		this.current = current;
		setLayout(null);
		
		// czat z
		
		label2 = new JLabel();

		label2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
	    label2.setText("Czat z:" + current.getFirstname());
	    label2.setBounds(250, 0, 250, 50);

		add(label2);
		
		// załaduj więcej
		
		try {
		    imageLoad = ImageIO.read(getClass().getResource("load_black.PNG"));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		rectangleLoad = new Rectangle(600,10, 35, 35);
		addMouseListener(new MouseAdapter() {
			
			@Override
			 public void mouseClicked(MouseEvent e) {
				if (e.getX()>rectangleLoad.getX() && e.getX()<(rectangleLoad.getX()+rectangleLoad.getWidth()) && e.getY()>rectangleLoad.getY() && e.getY()<(rectangleLoad.getHeight()+rectangleLoad.getY()))
				{
					loadMore();
				}
				repaint();
	        }
		});
		
		
		
		// panel wewnętrzny z wiadomościami
		
		panel_1 = new JPanel();

		panel_1.setBounds(0, 50, 650, 500);
		panel_1.setBackground(new Color(240, 240, 240));
		panel_1.setLayout(new BorderLayout());
		add(panel_1);
		
		// panel zewnętrzny
		
		panel_2 = new JPanel();

		panel_2.setBounds(0, 50, 650, 500);
		panel_2.setBackground(new Color(240, 240, 240));
		panel_2.setLayout(new BorderLayout());
		add(panel_2);
		
		
		vertical.add(Box.createVerticalStrut(15));

		
		JScrollPane scrollPane = new JScrollPane(panel_1);
		scrollPane.setBackground(new Color(240, 240, 240));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		scrollPane.setPreferredSize(new Dimension(650, 500));
		verticalScrollBar = scrollPane.getVerticalScrollBar();
		/* scroll listener
		 verticalScrollBar.addAdjustmentListener(new AdjustmentListener() {
	            @Override
	            public void adjustmentValueChanged(AdjustmentEvent e) {
	                if (e.getValue() == verticalScrollBar.getMinimum() && verticalScrollBar.getMaximum()!=0) {
	                    // Perform the action when scroll bar reaches the top
	                    System.out.println("Scroll bar reached the top!");
	                }
	            }
	        });
		*/
		panel_2.add(scrollPane, BorderLayout.PAGE_START);
		
		
		// wiadomość
		
		JTextField msgField = new JTextField("");

		msgField.setBorder(null);
		msgField.setFont(new Font("Dialog", Font.ITALIC, 14));
		msgField.setBounds(25, 570, 400, 40);
		add(msgField);
	
		JButton sendBtn = new JButton("Wyślij");
		sendBtn.setBorder(null);
		sendBtn.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		sendBtn.setBackground(new Color(255, 240, 245));
		sendBtn.setBounds(460, 570, 175, 40);

		add(sendBtn);
		sendBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMsg(msgField.getText());
				msgField.setText("");
				
			}
		});
		
		startRunning();
		
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
	
	void setUser(User current)
	{
		this.current = current;
		label2.setText("czat z:" + current.getFirstname());
		maxMsg = 6;
		
	}
	
	void startRunning()
	{
		scheduler.scheduleAtFixedRate((new Runnable() {

			@Override
			public void run() {
    				if(current!=null)
    				{
    					conn2 = sqlConn.connect();
		    			try {
	    				PreparedStatement prep;
						prep = conn2.prepareStatement("SELECT * FROM "
								+ "(SELECT * FROM messages "
								+ "WHERE ((sender_id=(?) AND receiver_id=(?)) OR (sender_id=(?) AND receiver_id=(?))) AND date > (?)"
								+ " ORDER BY date DESC "
								+ "LIMIT ?) AS subquery "
								+ "ORDER BY date ASC");
						prep.setInt(1, id);
		    			prep.setInt(2, current.getId());
		    			prep.setInt(3, current.getId());
		    			prep.setInt(4, id);
		    			prep.setTimestamp(5, last);
		    			prep.setInt(6, maxMsg);
		    			ResultSet rs = prep.executeQuery();
		    			while(rs.next())
		    			{
		    				String text = rs.getString("text");

		    				JLabel output = new JLabel("<html><p style=\"width: 150px\">"+text+"</p></html>");
		    				JPanel textPanel = new JPanel();
		    				textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		    				output.setBackground(new Color (65,105,225));
		    				output.setFont(new Font("Dialog", Font.PLAIN| Font.ITALIC, 14));
		    				output.setBorder(new EmptyBorder(10,10,15,50));
		    				output.setOpaque(true);
		    				textPanel.add(output);
		    				
		    				   
		    				SimpleDateFormat sdf = new SimpleDateFormat("d.MM.yyyy HH:mm:ss");
		    				
		    				JLabel time = new JLabel();
		    				time.setFont(new Font("Dialog", Font.PLAIN| Font.ITALIC, 9));
		    				time.setForeground(Color.GRAY);
		    				time.setText("	"+sdf.format(rs.getTimestamp("date")));
		    				textPanel.add(time);
		    				

		    				if(rs.getInt("sender_id")==id)
		    				{
		    					JPanel right = new JPanel(new BorderLayout());
		    					right.setBackground(new Color(240, 240, 240));
			    				vertical.add(right);
			    				vertical.add(Box.createVerticalStrut(15));
			    				output.setBackground(new Color (65,105,225));
			    				output.setForeground(Color.WHITE);
			    				
		    					right.add(textPanel, BorderLayout.LINE_END);
		    					panel_1.add(vertical, BorderLayout.PAGE_START);

		    				}
		    				else
		    				{
		    					JPanel left = new JPanel(new BorderLayout());
		    					left.setBackground(new Color(240, 240, 240));
			    				vertical.add(left);
			    				vertical.add(Box.createVerticalStrut(15));
			    				output.setBackground(Color.WHITE);
			    				output.setForeground(Color.GRAY);
			    				
			    				left.add(textPanel, BorderLayout.LINE_START);
			    				panel_1.add(vertical, BorderLayout.PAGE_START);

		    				}
		    				vertical.add(Box.createVerticalStrut(15));
		    				repaint();
		    				invalidate();
		    				validate();
			    			last = rs.getTimestamp("date");
			    			if(scrollDown)
			    			{
				    			int extent = verticalScrollBar.getModel().getExtent();
				                int maximum = verticalScrollBar.getMaximum();
				                verticalScrollBar.setValue(maximum - extent);
			    			}
		    			}
		    			
		    			if(conn2!=null)
	    					conn2.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				}
			}}), 0, 100, MILLISECONDS);
	            
	}
	
	void loadMore()
	{
		scrollDown = false;
		maxMsg +=2;
		refresh();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scrollDown = true;
	}

	void refresh()
	{
		vertical.removeAll();
		vertical.revalidate();
		vertical.repaint();
		vertical.add(Box.createVerticalStrut(15));
		last = new Timestamp(0);
	}
	
	public void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      
	      Graphics2D g2 = (Graphics2D)g;

	      Rectangle2D r2=new Rectangle2D.Double(0,0,getWidth(),getHeight());

	      g2.setPaint(new Color(255, 255, 255)); //szary
	 
	      g2.fill(new RoundRectangle2D.Double(15, 565, 425, 50, 40, 40));
	      
	      g2.setPaint(new Color(240, 240, 240)); 
	 	 
	      g2.fill(new RoundRectangle2D.Double(450, 565, 190, 50, 40, 40));
	      
	      g2.drawImage(imageLoad ,(int) rectangleLoad.getX() ,(int) rectangleLoad.getY(), (int) rectangleLoad.getWidth(), (int) rectangleLoad.getHeight(), null);
	     }
}
