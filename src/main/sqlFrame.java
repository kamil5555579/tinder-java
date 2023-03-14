package main;

import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.sql.*;  

public class sqlFrame extends JFrame {

	JTextField field = new JTextField(10);
	JButton button = new JButton("Szukaj");
	JTextArea area = new JTextArea();
	
	public sqlFrame() throws HeadlessException {
		// TODO Auto-generated constructor stub
		setSize(400,400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setLayout( new GridLayout(3,1));
		this.add(area);
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Wpisz zapytanie");
		panel.add(label);
		this.add(panel);
		panel.add(field);
		this.add(button);
		
		button.addActionListener(new ActionListener()
				{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Connection conn = null;
				try {
					Class.forName("com.mysql.jdbc.Driver");  
					conn = DriverManager.getConnection(
							"jdbc:mysql://s176.cyber-folks.pl/gacteavduo_projektjava", "gacteavduo_gacteavduo",
							"ProjektJava2023!");


					PreparedStatement prep = conn.prepareStatement("SELECT * FROM waluty where usd <(?)");
					prep.setString(1, field.getText());
					ResultSet rs = prep.executeQuery();
					//Statement stmt = conn.createStatement();
					//ResultSet rs = stmt.executeQuery( field.getText());

					ResultSetMetaData md  = rs.getMetaData();
							

					for (int ii = 1; ii <= md.getColumnCount(); ii++){
						area.append(md.getColumnName(ii)+ " | ");						
						
					}
					area.append("\n");
					
					while (rs.next()) {
						for (int ii = 1; ii <= md.getColumnCount(); ii++){
							area.append( rs.getObject(ii)+ " | ");							
						}
					area.append("\n");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				finally {
					if (conn!= null){
						try {
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}	
				});
		

	}

	public sqlFrame(GraphicsConfiguration gc) {
		super(gc);
		// TODO Auto-generated constructor stub
	}

	public sqlFrame(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public sqlFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sqlFrame frame = new sqlFrame();
		frame.setVisible(true);

	}

}
