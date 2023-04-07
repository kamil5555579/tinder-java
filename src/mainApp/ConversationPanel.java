package mainApp;

import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;

import com.mysql.jdbc.Connection;

import utilities.SqlConnection;
import utilities.User;
import java.awt.Font;

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
		setBorder(null);
		
		this.id = id;
		this.current = current;
		setLayout(null);
		
		// czat z
		
		label2 = new JLabel();
		label2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
	    label2.setText("Czat z:<dynamic>");
	    label2.setBounds(310, 10, 195, 25);
		add(label2);
		
		// panel wewnętrzny z wiadomościami
		
		panel_1 = new JPanel();
		panel_1.setBounds(0, 50, 890, 550);
		add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane(vertical);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(600, 550));
		panel_1.add(scrollPane, BorderLayout.PAGE_START);
		vertical.add(Box.createVerticalStrut(15));

		
		// wiadomość
		
		JTextField msgField = new JTextField("");
		msgField.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		msgField.setBounds(200, 615, 232, 40);
		add(msgField);
	
		JButton sendBtn = new JButton("Wyślij");
		sendBtn.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		sendBtn.setBackground(new Color(255, 240, 245));
		sendBtn.setBounds(450, 615, 116, 40);
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
		    				//JLabel output = new JLabel(text);
		    				//JPanel textPanel = new JPanel();
		    				JTextArea area = new JTextArea(text);
		    				
		    				
		    				area.setWrapStyleWord(true);
		    				area.setEditable(false);
		    				area.setBorder(null);
		    				
		    				
		    				//textPanel.setBackground(Color.lightGray);
		    				
		    				//textPanel.add(area);
		    				add(area);
		    				if(rs.getInt("sender_id")==id)
		    				{
		    					JPanel right = new JPanel(new BorderLayout());
			    				vertical.add(right);
		    					right.add(area, BorderLayout.LINE_END);
		    				}
		    				else
		    				{
		    					JPanel left = new JPanel(new BorderLayout());
			    				vertical.add(left);
			    				left.add(area, BorderLayout.LINE_START);
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

}
