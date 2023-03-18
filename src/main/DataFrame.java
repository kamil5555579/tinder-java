package main;

import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.jdbc.Connection;

public class DataFrame extends JFrame {

	SqlConnection sqlConn = new SqlConnection();
	int id;
	JFileChooser fileChooser = new JFileChooser();
	File f=null;
	InputStream is=null;
	
	public DataFrame(int id) throws HeadlessException {
		
		setSize(800,800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.id = id;
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(6,1));
		add(mainPanel);
		
		JLabel fnameLabel = new JLabel("Imie");
		mainPanel.add(fnameLabel);
		
		JTextField fnameText = new JTextField();
		mainPanel.add(fnameText);
		
		JLabel lnameLabel = new JLabel("Nazwisko");
		mainPanel.add(lnameLabel);
		
		JTextField lnameText = new JTextField();
		mainPanel.add(lnameText);
		
		JLabel universityLabel = new JLabel("Uczelnia");
		mainPanel.add(universityLabel);
		
		JTextField universityText = new JTextField();
		mainPanel.add(universityText);
		
		JLabel imageLabel = new JLabel("Zdjęcie");
		mainPanel.add(imageLabel);
		
		JLabel thumb = new JLabel();
		mainPanel.add(thumb);
		
		JButton open = new JButton("Wybierz");
		open.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						
						int retval = fileChooser.showOpenDialog(null);
					    if (retval == JFileChooser.APPROVE_OPTION) {
					      f = fileChooser.getSelectedFile();
					      String path = f.getAbsolutePath();
					      ImageIcon icon = new ImageIcon(path);
					      Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
					      thumb.setIcon(new ImageIcon(img));
					      
					}
			
					}});
		mainPanel.add(open); 
		
		JButton registerButton = new JButton("Zapisz");
		mainPanel.add(registerButton);
		registerButton.addActionListener( new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						String firstname = fnameText.getText();
						String lastname = lnameText.getText();
						String university = universityText.getText();
						try {
							is = new FileInputStream(f);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							save(firstname, lastname, university, is);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
			
				});
		
	}
	
	public void save(String firstname, String lastname, String university, InputStream is) throws SQLException
	{
		Connection conn = sqlConn.connect();
		PreparedStatement prep;

		prep = conn.prepareStatement("INSERT INTO userdata (user_id, firstname, lastname, university,image) VALUES (?,?,?,?,?)");
		prep.setLong(1, id);
		prep.setString(2, firstname);
		prep.setString(3, lastname);
		prep.setString(4, university);
		prep.setBinaryStream(5,is, (int) f.length());
		
		prep.executeUpdate();
		dispose();
		MainFrame frame = new MainFrame(id);
		frame.setVisible(true);
		

		
	}



}
