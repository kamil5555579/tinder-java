package main;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.mysql.jdbc.Connection;

public class MainFrame extends JFrame {

	JLabel welcome;
	SqlConnection sqlConn = new SqlConnection();
	public MainFrame(int id) throws HeadlessException {
		setSize(800,800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		welcome = new JLabel();
		add(welcome);
		
		Connection conn = sqlConn.connect();
		PreparedStatement prep;

		try {
		prep = conn.prepareStatement("SELECT * FROM userdata WHERE user_id =(?)");
		prep.setString(1, Integer.toString(id));
		ResultSet rs = prep.executeQuery();
		if (rs.next())
		{
			welcome.setText(( String) rs.getObject("university") + rs.getObject("gender"));
		}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
