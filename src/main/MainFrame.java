package main;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.mysql.jdbc.Connection;

public class MainFrame extends JFrame {


	SqlConnection sqlConn = new SqlConnection();
	User me;
	List<User> users = new ArrayList<User>();
	int i;
	JLabel label;

	public MainFrame(int id) throws HeadlessException {
		setSize(800,800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(6,1));
		initializeMe(id);
		initializeOthers(id);
		label = new JLabel();
		add(label);
		
		JButton next = new JButton("next");
		add(next);
		i=-1;
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(i<users.size()-1)
				{
					i++;
					label.setText(users.get(i).getFirstname());
				}
			}
		});
		JButton reject = new JButton("reject");
		add(reject);
		reject.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = sqlConn.connect();
				PreparedStatement prep;

				try {
				prep = conn.prepareStatement("INSERT INTO matches (user_id, stranger_id, interest) VALUES (?,?,?)");
				prep.setInt(1, me.getId());
				prep.setInt(2, users.get(i).getId());
				prep.setBoolean(3, false);
				prep.executeUpdate();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JButton match = new JButton("match");
		add(match);
		match.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = sqlConn.connect();
				PreparedStatement prep;

				try {
				prep = conn.prepareStatement("INSERT INTO matches (user_id, stranger_id, interest) VALUES (?,?,?)");
				prep.setInt(1, me.getId());
				prep.setInt(2, users.get(i).getId());
				prep.setBoolean(3, true);
				prep.executeUpdate();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton chat = new JButton("chat");
		add(chat);
		chat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				ChatFrame chatFrame = new ChatFrame(me.getId());
				chatFrame.setVisible(true);
			}
		});
		
	}

	public void initializeMe(int id)
	{
		Connection conn = sqlConn.connect();
		PreparedStatement prep;

		try {
		prep = conn.prepareStatement("SELECT * FROM userdata WHERE user_id =(?)");
		prep.setString(1, Integer.toString(id));
		ResultSet rs = prep.executeQuery();
		if (rs.next())
		{
			byte[] imageData = rs.getBytes("image");
			ImageIcon image = new ImageIcon((new ImageIcon(imageData)).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
			me = new User(id, rs.getString("firstname"), rs.getString("lastname"), rs.getString("university"), rs.getString("gender"), rs.getInt("age"), image);
		}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initializeOthers(int id)
	{
		Connection conn = sqlConn.connect();
		PreparedStatement prep;

		try {
		prep = conn.prepareStatement("SELECT * FROM userdata WHERE user_id NOT IN (SELECT stranger_id FROM matches WHERE user_id = (?)) AND user_id !=(?)");
		prep.setInt(1, id);
		prep.setInt(2, id);
		ResultSet rs = prep.executeQuery();
		while(rs.next())
		{
			byte[] imageData = rs.getBytes("image");
			ImageIcon image = new ImageIcon((new ImageIcon(imageData)).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
			users.add(new User(rs.getInt("user_id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("university"), rs.getString("gender"), rs.getInt("age"), image));
		}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
