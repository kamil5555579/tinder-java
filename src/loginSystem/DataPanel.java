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
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;

import com.mysql.jdbc.Connection;

import mainApp.CardFrame2;
import utilities.PTextField;
import utilities.SqlConnection;

import javax.swing.SwingConstants;
import java.awt.ComponentOrientation;
import javax.swing.border.EtchedBorder;

class DataPanel extends JPanel 
{
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
	Image img;

	public DataPanel(JPanel panel, JFrame frame)  {
		
		//ustawienia panelu
		
		setBounds(100, 100, 700, 600);
		setBackground(new Color(255, 105, 180));
		setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		setLayout(null);

		//opis
		
		txtDescription = new JTextPane();
		//txtDescription.setHorizontalAlignment(SwingConstants.LEFT);
		txtDescription.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		txtDescription.setCaretColor(new Color(0, 0, 0));
		txtDescription.setBounds(135, 240, 215, 210);
		txtDescription.setBackground(new Color(240, 240, 240));
		add(txtDescription);
		txtDescription.setFont(new Font("Dialog", Font.ITALIC, 12));
		txtDescription.setBorder(null);
		//txtDescription.setColumns(10);
		
		//wybór płci
		
		String[] gender = {"Płeć", "Mężczyzna", "Kobieta",
  	    "Inna" };
		comboBox = new JComboBox(gender);
		comboBox.setBackground(new Color(240, 240, 240));
		comboBox.setBounds(135, 137, 215, 30);
		add(comboBox);
		//comboBox.setBackground(new Color(255, 240, 245));
		comboBox.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		
		//wybór wydziału
		
		String[] faculty = {"Wydział", "Architektury", "Chemii", "Matematyki", "Fizyki", 
  	    "Inny" };
		
		comboBox_2 = new JComboBox(faculty);
		comboBox_2.setBackground(new Color(255, 255, 255));
		//comboBox_2.setOpaque(true);
		comboBox_2.setBorder(null);
		comboBox_2.setBounds(135, 185, 215, 30);
		comboBox_2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		comboBox_2.setBackground(new Color(240, 240, 240));
		add(comboBox_2);
	

		
		
		//wiek
		
		txtAge = new PTextField("Wiek");
		txtAge.setBorder(null);
		txtAge.setBounds(362, 137, 215, 30);
		add(txtAge);
		txtAge.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		txtAge.setBackground(new Color(240, 240, 240));
		//txtAge.setBackground(new Color(255, 240, 245));
		txtAge.setColumns(10);
		
		//imię
		
		txtName = new PTextField("Imię");
		txtName.setBorder(null);
		txtName.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		txtName.setColumns(10);
		txtName.setBackground(new Color(240, 240, 240));
		txtName.setBounds(135, 94, 215, 30);
		add(txtName);
		
		//nazwisko
		
		txtSurname = new PTextField("Nazwisko");
		txtSurname.setBorder(null);
		txtSurname.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		txtSurname.setColumns(10);
		//txtSurname.setBackground(new Color(255, 240, 245));
		txtSurname.setBackground(new Color(240, 240, 240));
		txtSurname.setBounds(362, 95, 215, 30);
		add(txtSurname);
		
		//zdjecie
		
		imgButton = new JButton("Wybierz zdjęcie");
		imgButton.setHorizontalAlignment(SwingConstants.LEFT);
		imgButton.setBackground(new Color(255, 255, 255));
		imgButton.setBorder(null);
		//imgButton.setBackground(new Color(255, 240, 245));
		imgButton.setBackground(new Color(240, 240, 240));
		imgButton.setBounds(360, 185, 215, 30);
		add(imgButton);
		
		imgLabel = new JLabel("");
		imgLabel.setBounds(360,240,215,215);
		add(imgLabel);
		
		imgButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int retval = fileChooser.showOpenDialog(null);
			    if (retval == JFileChooser.APPROVE_OPTION) {
			      f = fileChooser.getSelectedFile();
			      String path = f.getAbsolutePath();
			      ImageIcon icon = new ImageIcon(path);
			      img = new ImageIcon(icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH)).getImage();
			      Image imgTemp = icon.getImage().getScaledInstance(215, 215, Image.SCALE_SMOOTH);
			      imgLabel.setIcon(new ImageIcon(imgTemp));
			      
			}
	
			}});
		
		
		//label Fill your data
		
		JLabel lblTinder = new JLabel("Stwórz swój profil!");
		lblTinder.setForeground(Color.WHITE);
		lblTinder.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 42));
		lblTinder.setBounds(155, 12, 410, 65);
		add(lblTinder);
		
		/* logowanie
		JButton button = new JButton("Log in");
		button.setBackground(new Color(255, 240, 245));
		button.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		button.setBounds(124, 279, 170, 50);
		add(button);
		*/
		
		//zapisanie i przejscie do aplikacji
		
		btnRegister = new JButton("Zapisz");
		btnRegister.setBackground(new Color(255, 240, 245));
		btnRegister.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 18));
		btnRegister.setBounds(120, 490, 475, 40);
		btnRegister.setBorder(null);
		add(btnRegister);
		
		btnRegister.addActionListener( new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						
						
						if(txtName.getText().equals("Imię")||txtSurname.getText().equals("Nazwisko")||f==null) {
							if (txtName.getText().equals("Imię")) {
							JOptionPane.showMessageDialog(
		                            null,"Nie podano imienia. Podaj imię!",
		                            "Błąd uzupełniania danych",
		                            JOptionPane.ERROR_MESSAGE);
							}
							if (txtSurname.getText().equals("Nazwisko")) {
								JOptionPane.showMessageDialog(
			                            null,"Nie podano nazwiska. Podaj nazwisko!",
			                            "Błąd uzupełniania danych",
			                            JOptionPane.ERROR_MESSAGE);
								}
							if (f==null) {
								JOptionPane.showMessageDialog(
			                            null,"Nie wybrano zdjęcia. Wybierz zdjęcie!",
			                            "Błąd uzupełniania danych",
			                            JOptionPane.ERROR_MESSAGE);
							}
							
							
						} else {
						String firstname = txtName.getText();
						String lastname = txtSurname.getText();
						String university = (String) comboBox_2.getSelectedItem();
						String gender = (String) comboBox.getSelectedItem();
						int age = Integer.parseInt(txtAge.getText());
						String description = txtDescription.getText();
						if (f==null) {
							JOptionPane.showMessageDialog(
		                            null,"Nie wybrano zdjęcia. Wybierz zdjęcie!",
		                            "Błąd uzupełniania danych",
		                            JOptionPane.ERROR_MESSAGE);
						}else {
							

						try {
							ByteArrayOutputStream os = new ByteArrayOutputStream();
							BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
							bufferedImage.getGraphics().drawImage(img, 0, 0 , null);
							ImageIO.write( bufferedImage, "gif", os);
							is = new ByteArrayInputStream(os.toByteArray());
							os.close();
						} catch (FileNotFoundException e1) {
							JOptionPane.showMessageDialog(
		                            null,"Błąd dostępu do pliku.",
		                            "Błąd dostępu do danych",
		                            JOptionPane.ERROR_MESSAGE);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						save(firstname, lastname, university, is, frame, gender, age, description);
						
						}
						
					}
			
				}});
		
        add(btnRegister);
        
        JLabel lblOpowiedzOSobie = new JLabel("Opowiedz o sobie");
        lblOpowiedzOSobie.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
        lblOpowiedzOSobie.setBounds(165, 220, 180, 25);
        add(lblOpowiedzOSobie);
	
	}
	
	//przekazanie id użytkownika
	
	void setId(int id)
	{
		this.id = id;
	}
	
	//zapisanie danych i przejście do aplikacji
	
	public void save(String firstname, String lastname, String university, InputStream is, JFrame frame, String gender, int age, String description) 
	{
		 SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
	       	 
	            @Override
	            protected Void doInBackground() throws Exception {
	            	conn = sqlConn.connect();
	    			PreparedStatement prep;

	    			prep = conn.prepareStatement("INSERT INTO userdata (user_id, firstname, lastname, university, gender, age, image, description) VALUES (?,?,?,?,?,?,?,?)");
	    			prep.setLong(1, id);
	    			prep.setString(2, firstname);
	    			prep.setString(3, lastname);
	    			prep.setString(4, university);
	    			prep.setString(5, gender);
	    			prep.setInt(6, age);
	    			prep.setBinaryStream(7,is, (int) f.length());
	    			prep.setString(8, description);
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
	            		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	            	
	                    
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
	      
	      g2.setPaint(new Color(240, 240, 240)); //szary
	      g2.fill(new RoundRectangle2D.Double(115, 80, 485, 400, 40, 40));
	   
	      //g2.setPaint(new Color(0, 0, 0)); //czarny
	      //g2.draw(new RoundRectangle2D.Double(125, 90, 225, 40, 40, 40));
	     // g2.draw(new RoundRectangle2D.Double(125, 180, 225, 40, 40, 40));
	      
	      g2.setPaint(new Color(255, 240, 245)); // jasnorozowy
	      g2.fill(new RoundRectangle2D.Double(110, 485, 495, 50, 40, 40));
	    
	    
	     }
}
