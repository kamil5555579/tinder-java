package mainApp;

import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

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
	JLabel label2;
	Box vertical = Box.createVerticalBox();
	final ScheduledExecutorService scheduler = 
		       Executors.newScheduledThreadPool(2);
	private JPanel panel_1;
	Timestamp last = new Timestamp(0);
	
	public ConversationPanel(int id, User current) {
		
		
		this.id = id;
		this.current = current;
		setLayout(null);
		
		// czat z
		
		label2 = new JLabel();
		label2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
	    label2.setText("Czat z:" + current.getFirstname());
	    label2.setBounds(250, 0, 250, 50);
		add(label2);
		
		// panel wewnętrzny z wiadomościami
		
		panel_1 = new JPanel();
		panel_1.setBounds(0, 50, 650, 500);
		panel_1.setBackground(new Color(240, 240, 240));
		add(panel_1);
		panel_1.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(vertical);
		scrollPane.setBackground(SystemColor.text);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(650, 500));
		panel_1.add(scrollPane, BorderLayout.PAGE_START);
		vertical.add(Box.createVerticalStrut(15));

		
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
		    				JLabel output = new JLabel("<html><p style=\"width: 150px\">"+text+"</p></html>");
		    				JPanel textPanel = new JPanel();
		    				textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		    				output.setBackground(new Color (65,105,225));
		    				output.setFont(new Font("Dialog", Font.PLAIN| Font.ITALIC, 14));
		    				output.setBorder(new EmptyBorder(10,10,15,50));
		    				output.setOpaque(true);
		    				textPanel.add(output);
		    				
		    				Calendar cal = Calendar.getInstance();   
		    				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		    				
		    				JLabel time = new JLabel();
		    				time.setFont(new Font("Dialog", Font.PLAIN| Font.ITALIC, 8));
		    				time.setForeground(Color.LIGHT_GRAY);
		    				time.setText("	"+sdf.format(cal.getTime()));
		    				textPanel.add(time);
		    				
		    				if(rs.getInt("sender_id")==id)
		    				{
		    					JPanel right = new JPanel(new BorderLayout());
			    				vertical.add(right);
			    				
			    				output.setBackground(new Color (65,105,225));
			    				output.setForeground(Color.WHITE);
			    				
		    					right.add(textPanel, BorderLayout.LINE_END);
		    				}
		    				else
		    				{
		    					JPanel left = new JPanel(new BorderLayout());
			    				vertical.add(left);
			    				
			    				output.setBackground(Color.WHITE);
			    				output.setForeground(Color.GRAY);
			    				
			    				left.add(textPanel, BorderLayout.LINE_START);
		    				}
		    				vertical.add(Box.createVerticalStrut(15));
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
			}}), 0, 500, MILLISECONDS);
	            
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

	      //Color c0=new Color(240, 240, 240), c1= new Color(255, 240, 245);

	      //GradientPaint  gp = new GradientPaint(150, 200, c1, 450, 200, c0, false);
	      //g2.setPaint(gp);
	      //g2.fill(r2);
	      
	      g2.setPaint(new Color(255, 255, 255)); //szary
	 
	      g2.fill(new RoundRectangle2D.Double(15, 565, 425, 50, 40, 40));
	      
	      g2.setPaint(new Color(255, 240, 245)); //szary
	 	 
	      g2.fill(new RoundRectangle2D.Double(450, 565, 190, 50, 40, 40));
	      
	     }

}
