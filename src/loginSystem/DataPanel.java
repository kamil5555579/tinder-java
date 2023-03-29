package loginSystem;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;

import com.mysql.jdbc.Connection;

import mainApp.CardFrame2;
import utilities.PTextField;
import utilities.SqlConnection;

import javax.swing.SwingConstants;
import java.awt.ComponentOrientation;

class DataPanel extends JPanel 
{

	private JPanel panel_1;
	private JButton btnRegister;
	private JComboBox comboBox;
	private PTextField txtAge;
	private JTextPane txtDescription;
	private PTextField txtName;
	private PTextField txtSurname;
	private JButton imgButton;
	private JLabel imgLabel;
	private JComboBox comboBox_2;
	private int id;
	private SqlConnection sqlConn = new SqlConnection();
	private JFileChooser fileChooser = new JFileChooser();
	private File f=null;
	private InputStream is=null;
	private Connection conn;

	public DataPanel(JPanel panel, JFrame frame)  {
		
		//ustawienia panelu
		
		setBounds(100, 100, 700, 600);
		setBackground(new Color(255, 105, 180));
		setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(115, 90, 475, 375);
		add(panel_1);
		panel_1.setLayout(null);

		//opis
		
		txtDescription = new JTextPane();
		//txtDescription.setHorizontalAlignment(SwingConstants.LEFT);
		txtDescription.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		txtDescription.setCaretColor(new Color(0, 0, 0));
		txtDescription.setBounds(15, 145, 215, 210);
		panel_1.add(txtDescription);
		txtDescription.setFont(new Font("Dialog", Font.ITALIC, 12));
		txtDescription.setBorder(null);
		//txtDescription.setColumns(10);
		
		//wybór płci
		
		String[] gender = {"Gender", "Male", "Famale",
  	    "Other" };
		comboBox = new JComboBox(gender);
		comboBox.setBounds(15, 57, 215, 30);
		panel_1.add(comboBox);
		comboBox.setBackground(new Color(255, 240, 245));
		comboBox.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		
		//wybór wydziału
		
		String[] faculty = {"Faculty", "Architecture", "Chemistry", "Mathematics and Infromation Science", "Physics", 
  	    "Other" };
		comboBox_2 = new JComboBox(faculty);
		comboBox_2.setBounds(15, 100, 215, 30);
		panel_1.add(comboBox_2);
		comboBox_2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		comboBox_2.setBackground(new Color(255, 240, 245));
		
		//wiek
		
		txtAge = new PTextField("Age");
		txtAge.setBounds(245, 57, 215, 30);
		panel_1.add(txtAge);
		txtAge.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		txtAge.setBackground(new Color(255, 240, 245));
		txtAge.setColumns(10);
		
		//imię
		
		txtName = new PTextField("Name");
		txtName.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		txtName.setColumns(10);
		txtName.setBackground(new Color(255, 240, 245));
		txtName.setBounds(15, 15, 215, 30);
		panel_1.add(txtName);
		
		//nazwisko
		
		txtSurname = new PTextField("Surname");
		txtSurname.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		txtSurname.setColumns(10);
		txtSurname.setBackground(new Color(255, 240, 245));
		txtSurname.setBounds(245, 15, 215, 30);
		panel_1.add(txtSurname);
		
		//zdjecie
		
		imgButton = new JButton("Choose image");
		imgButton.setBackground(new Color(255, 240, 245));
		imgButton.setBounds(245, 100, 215, 30);
		panel_1.add(imgButton);
		
		imgLabel = new JLabel("");
		imgLabel.setBounds(245,145,215,215);
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
			      Image img = icon.getImage().getScaledInstance(215, 215, Image.SCALE_SMOOTH);
			      imgLabel.setIcon(new ImageIcon(img));
			      
			}
	
			}});
		
		
		//label Fill your data
		
		JLabel lblTinder = new JLabel("Fill your data");
		lblTinder.setForeground(Color.WHITE);
		lblTinder.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 42));
		lblTinder.setBounds(220, 20, 258, 61);
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
		btnRegister.setBackground(new Color(255, 240, 245));
		btnRegister.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		btnRegister.setBounds(115, 480, 475, 50);
		add(btnRegister);
		
		btnRegister.addActionListener( new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						String firstname = txtName.getText();
						String lastname = txtSurname.getText();
						String university = (String) comboBox_2.getSelectedItem();
						if (f==null) {
							JOptionPane.showMessageDialog(
		                            null,"Nie wybrano zdjęcia. Wybierz zdjęcie!",
		                            "Błąd uzupełniania danych",
		                            JOptionPane.ERROR_MESSAGE);
						}else {
							
						try {
							is = new FileInputStream(f);
						} catch (FileNotFoundException e1) {
							JOptionPane.showMessageDialog(
		                            null,"Błąd dostępu do pliku.",
		                            "Błąd dostępu do danych",
		                            JOptionPane.ERROR_MESSAGE);
						}
						save(firstname, lastname, university, is, frame);
						
						}
					}
			
				});
		
        add(btnRegister);
	
	}
	
	//przekazanie id użytkownika
	
	void setId(int id)
	{
		this.id = id;
	}
	
	//zapisanie danych i przejście do aplikacji
	
	public void save(String firstname, String lastname, String university, InputStream is, JFrame frame) 
	{
		 SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
	       	 
	            @Override
	            protected Void doInBackground() throws Exception {
	            	conn = sqlConn.connect();
	    			PreparedStatement prep;

	    			prep = conn.prepareStatement("INSERT INTO userdata (user_id, firstname, lastname, university, image) VALUES (?,?,?,?,?)");
	    			prep.setLong(1, id);
	    			prep.setString(2, firstname);
	    			prep.setString(3, lastname);
	    			prep.setString(4, university);
	    			prep.setBinaryStream(5,is, (int) f.length());
	    			prep.executeUpdate();
	    			
	                return null;
	            }

	            @Override
	            protected void done() {
	                try {
	            		CardFrame2 newFrame = new CardFrame2(id);
	            		newFrame.setVisible(true);
	            		if (conn!= null)
	    	    			conn.close();
	            		frame.dispose();
	            	
	                    
	                } catch (Exception ex) {
	                	JOptionPane.showMessageDialog(
	                            null,"Błąd zapisu danych",
	                            "Błąd zapisu danych",
	                            JOptionPane.ERROR_MESSAGE);
	                }
	            }

	       };
	       
	       worker.execute();

	}
	
	 public void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      
	      Graphics2D g2 = (Graphics2D)g;

	      Rectangle2D r2=new Rectangle2D.Double(0,0,getWidth(),getHeight());

	      Color c0=new Color(255,0,128), c1= new Color(255,128,0);

	      GradientPaint  gp = new GradientPaint(150, 200, c1, 450, 200, c0, false);
	      g2.setPaint(gp);
	      g2.fill(r2);
	      
	    
	     }
	 
}
