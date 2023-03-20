package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;

import com.mysql.jdbc.Connection;

class DataPanel extends JPanel 
{

	private JPanel panel_1;
	private JButton btnRegister;
	private JComboBox comboBox;
	private JTextField txtAge;
	private JTextField txtDescription;
	private JTextField txtName;
	private JTextField txtSurname;
	private JButton imgButton;
	private JLabel imgLabel;
	private JComboBox comboBox_2;
	private int id;
	private SqlConnection sqlConn = new SqlConnection();
	private JFileChooser fileChooser = new JFileChooser();
	private File f=null;
	private InputStream is=null;

	public DataPanel(JPanel panel)  {
		
		//ustawienia panelu
		
		setBounds(100, 100, 600, 400);
		setBackground(new Color(255, 105, 180));
		setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(125, 93, 351, 174);
		add(panel_1);
		panel_1.setLayout(null);

		//opis
		
		txtDescription = new JTextField();
		txtDescription.setCaretColor(new Color(0, 0, 0));
		txtDescription.setBounds(12, 126, 110, 15);
		panel_1.add(txtDescription);
		txtDescription.setFont(new Font("Dialog", Font.ITALIC, 12));
		txtDescription.setText("Description...");
		txtDescription.setBorder(null);
		txtDescription.setColumns(10);
		
		//wybór płci
		
		String[] gender = {"Gender", "Male", "Famale",
  	    "Other" };
		comboBox = new JComboBox(gender);
		comboBox.setBounds(12, 55, 159, 24);
		panel_1.add(comboBox);
		comboBox.setBackground(new Color(255, 240, 245));
		comboBox.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		
		//wybór wydziału
		
		String[] faculty = {"Faculty", "Architecture", "Chemistry", "Mathematics and Infromation Science", "Physics", 
  	    "Other" };
		comboBox_2 = new JComboBox(faculty);
		comboBox_2.setBounds(12, 91, 159, 24);
		panel_1.add(comboBox_2);
		comboBox_2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		comboBox_2.setBackground(new Color(255, 240, 245));
		
		//wiek
		
		txtAge = new JTextField();
		txtAge.setBounds(183, 55, 156, 26);
		panel_1.add(txtAge);
		txtAge.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		txtAge.setText("Age");
		txtAge.setBackground(new Color(255, 240, 245));
		txtAge.setColumns(10);
		
		//imię
		
		txtName = new JTextField();
		txtName.setText("Name");
		txtName.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		txtName.setColumns(10);
		txtName.setBackground(new Color(255, 240, 245));
		txtName.setBounds(11, 17, 159, 26);
		panel_1.add(txtName);
		
		//nazwisko
		
		txtSurname = new JTextField();
		txtSurname.setText("Surname");
		txtSurname.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		txtSurname.setColumns(10);
		txtSurname.setBackground(new Color(255, 240, 245));
		txtSurname.setBounds(182, 17, 157, 26);
		panel_1.add(txtSurname);
		
		//zdjecie
		
		imgButton = new JButton("Choose image");
		imgButton.setBounds(154, 125, 85, 21);
		panel_1.add(imgButton);
		
		imgLabel = new JLabel("");
		imgLabel.setBounds(241, 64, 100, 100);
		panel_1.add(imgLabel);
		
		imgButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int retval = fileChooser.showOpenDialog(null);
			    if (retval == JFileChooser.APPROVE_OPTION) {
			      f = fileChooser.getSelectedFile();
			      String path = f.getAbsolutePath();
			      ImageIcon icon = new ImageIcon(path);
			      Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			      imgLabel.setIcon(new ImageIcon(img));
			      
			}
	
			}});
		
		
		//label Fill your data
		
		JLabel lblTinder = new JLabel("Fill your data");
		lblTinder.setForeground(Color.WHITE);
		lblTinder.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 30));
		lblTinder.setBounds(205, 25, 194, 43);
		add(lblTinder);
		
		/* logowanie
		JButton button = new JButton("Log in");
		button.setBackground(new Color(255, 240, 245));
		button.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		button.setBounds(124, 279, 170, 50);
		add(button);
		*/
		
		//zapisanie i powrót do loginu
		
		btnRegister = new JButton("Save");
		btnRegister.setAlignmentY(0.0f);
		btnRegister.setBackground(new Color(255, 240, 245));
		btnRegister.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		btnRegister.setBounds(306, 279, 170, 50);
		add(btnRegister);
		
		btnRegister.addActionListener( new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						String firstname = txtName.getText();
						String lastname = txtSurname.getText();
						String university = (String) comboBox_2.getSelectedItem();
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
		
        add(btnRegister);
		
        //wyłącznik programu
        
		JLabel lblX = new JLabel("X");
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to close this application?", "Confirmation", JOptionPane.YES_NO_OPTION)==0){
					System.exit(ABORT);
				}
			}
		});
		lblX.setForeground(new Color(255, 255, 255));
		lblX.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		lblX.setBounds(578, 3, 11, 17);
		add(lblX);
	
	}
	
	//przekazanie id użytkownika
	
	void setId(int id)
	{
		this.id = id;
	}
	
	//zapisanie danych i przejście do aplikacji
	
	public void save(String firstname, String lastname, String university, InputStream is) throws SQLException
	{
		 SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
	       	 
	            @Override
	            protected Void doInBackground() throws Exception {
	            	Connection conn = sqlConn.connect();
	    			PreparedStatement prep;

	    			prep = conn.prepareStatement("INSERT INTO userdata (user_id, firstname, lastname, university, image) VALUES (?,?,?,?,?)");
	    			prep.setLong(1, id);
	    			prep.setString(2, firstname);
	    			prep.setString(3, lastname);
	    			prep.setString(4, university);
	    			prep.setBinaryStream(5,is, (int) f.length());
	    			
	                return null;
	            }

	            @Override
	            protected void done() {
	                try {
	            		MainFrame frame = new MainFrame(id);
	            		frame.setVisible(true);
	                    
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                }
	            }

	       };
	       
	       worker.execute();

	}
}
