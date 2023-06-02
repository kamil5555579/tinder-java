package mainApp;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.mysql.jdbc.Connection;

import utilities.PTextField;
import utilities.SqlConnection;
import utilities.User;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.JSlider;

public class SettingsPanel extends JPanel {
	
	private JLabel labelSettings;
	private JTextPane textPaneDescription;
	private JComboBox comboBoxGender;
	private JComboBox comboBoxFaculty;
	private PTextField textFieldAge;
	private PTextField textFieldName;
	private PTextField textFieldSurname;
	private JButton buttonChoosePhoto;
	private JLabel labelPhoto;
	private JButton buttonRegister;
	private JFileChooser fileChooser = new JFileChooser();
	private File f = null;
	private ByteArrayInputStream is = null;
	private SqlConnection sqlConn = new SqlConnection();
    private Connection conn;
	private int id;
	private User me;
	private Image image;
	private BufferedImage imageSwipe;
	private Rectangle rectangleSwipe = new Rectangle(10 ,0 ,70 ,70);
	private JTextField txtJo;
	private JTextField textField_1;
	SwipePanel swipePanel;
	
	public SettingsPanel(JPanel panel ,JFrame frame, int id,SwipePanel swipePanel) {
		
		this.id=id;
		this.swipePanel=swipePanel;
		//initializeMe(id);
		
		// ustawienia panelu
		
		setBounds(0, 0, 900, 800);
		setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		setLayout(null); 
		
		
		try {
			imageSwipe = ImageIO.read(getClass().getResource("arrow_settings.PNG"));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		
		addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
				if (e.getX()>rectangleSwipe.getX() && e.getX()<(rectangleSwipe.getX()+rectangleSwipe.getWidth()) && e.getY()>rectangleSwipe.getY() && e.getY()<(rectangleSwipe.getHeight()+rectangleSwipe.getY()))
				{
					CardLayout cardLayout = (CardLayout) panel.getLayout();
	                cardLayout.previous(panel);
				}
				repaint();
	        }
		});

		
		// label ustawienia
		
        labelSettings = new JLabel();
        labelSettings.setText("Ustawienia");
        labelSettings.setBounds(350, 15, 300, 50);
        labelSettings.setForeground(new Color(255, 100, 153));
		labelSettings.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 40));
        add(labelSettings);
       
		//opis
        
		textPaneDescription = new JTextPane();
		textPaneDescription.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		textPaneDescription.setCaretColor(new Color(0, 0, 0));
		textPaneDescription.setBounds(180, 320, 250, 128);
		textPaneDescription.setBackground(new Color(240, 240, 240));
		textPaneDescription.setFont(new Font("Dialog", Font.ITALIC, 12));
		textPaneDescription.setBorder(null);
		add(textPaneDescription);
				
		//wybór płci
		
		String[] gender = {"Płeć", "Mężczyzna", "Kobieta","Inna" };
		comboBoxGender = new JComboBox(gender);
		comboBoxGender.setBackground(new Color(240, 240, 240));
		comboBoxGender.setBounds(180, 200, 215, 30);
		comboBoxGender.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		add(comboBoxGender);
				
		//wybór wydziału
				
		String[] faculty = {"Wydział", "Architektury", "Chemii", "Matematyki", "Fizyki", "Inny" };
				
		comboBoxFaculty = new JComboBox(faculty);
		comboBoxFaculty.setBackground(new Color(255, 255, 255));
		comboBoxFaculty.setBorder(null);
		comboBoxFaculty.setBounds(180, 250, 215, 30);
		comboBoxFaculty.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		comboBoxFaculty.setBackground(new Color(240, 240, 240));
		add(comboBoxFaculty);
			
		//wiek
				
		textFieldAge = new PTextField("Wiek");
		textFieldAge.setBorder(null);
		textFieldAge.setBounds(480, 200, 215, 30);
		textFieldAge.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		textFieldAge.setBackground(new Color(240, 240, 240));
		textFieldAge.setColumns(10);
		add(textFieldAge);
		
		//imię
				
		textFieldName = new PTextField("Imię");
		textFieldName.setBorder(null);
		textFieldName.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		textFieldName.setColumns(10);
		textFieldName.setBackground(new Color(240, 240, 240));
		textFieldName.setBounds(180, 150, 215, 30);
		add(textFieldName);
				
		//nazwisko
				
		textFieldSurname = new PTextField("Nazwisko");
		textFieldSurname.setBorder(null);
		textFieldSurname.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		((JTextField) textFieldSurname).setColumns(10);
		textFieldSurname.setBackground(new Color(240, 240, 240));
		textFieldSurname.setBounds(480, 150, 215, 30);
		add(textFieldSurname);
		
		//zdjęcie
		labelPhoto = new JLabel("");
		labelPhoto.setBounds(480,275,250,250);
		add(labelPhoto);	
		
		//przycisk do zmiany zdjecia
				
		buttonChoosePhoto = new JButton("Wybierz zdjęcie");
		buttonChoosePhoto.setHorizontalAlignment(SwingConstants.LEFT);
		buttonChoosePhoto.setBackground(new Color(255, 255, 255));
		buttonChoosePhoto.setBorder(null);
		buttonChoosePhoto.setBackground(new Color(240, 240, 240));
		buttonChoosePhoto.setBounds(480, 250, 215, 30);
		add(buttonChoosePhoto);
				
		buttonChoosePhoto.addActionListener(new ActionListener()
				{


					@Override
					public void actionPerformed(ActionEvent e) {
						
						int retval = fileChooser.showOpenDialog(null);
					    if (retval == JFileChooser.APPROVE_OPTION) {
					      f = fileChooser.getSelectedFile();
					      String path = f.getAbsolutePath();
					      ImageIcon icon = new ImageIcon(path);
					      Image imgTemp = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
					      image = new ImageIcon(icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH)).getImage();
					      labelPhoto.setIcon(new ImageIcon(imgTemp));
					}
			
					}});
				
				//zapisanie zmian
				
		buttonRegister = new JButton("Zapisz");
		buttonRegister.setBackground(new Color(255, 240, 245));
		buttonRegister.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 18));
		buttonRegister.setBounds(200, 600, 475, 40);
		buttonRegister.setBorder(null);
		add(buttonRegister);
				
		buttonRegister.addActionListener( new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						String firstname = textFieldName.getText();
						String lastname = textFieldSurname.getText();
						String university = (String) comboBoxFaculty.getSelectedItem();
						String gender = (String) comboBoxGender.getSelectedItem();
						int age = Integer.parseInt(textFieldAge.getText());
						String description = textPaneDescription.getText();
						if (f==null) {
							JOptionPane.showMessageDialog(
		                            null,"Nie wybrano zdjęcia. Wybierz zdjęcie!",
		                            "Błąd uzupełniania danych",
		                            JOptionPane.ERROR_MESSAGE);
						}else {
							
						try {
							ByteArrayOutputStream os = new ByteArrayOutputStream();
							BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
							bufferedImage.getGraphics().drawImage(image, 0, 0 , null);
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
					
				});
		
		        add(buttonRegister);
		        
		        JLabel lblOpowiedzOSobie = new JLabel("Opowiedz o sobie");
		        lblOpowiedzOSobie.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		        lblOpowiedzOSobie.setBounds(180, 300, 180, 25);
		        add(lblOpowiedzOSobie);
		        
		        JLabel lblEdytujSwjProfil = new JLabel("Edytuj Swój profil");
		        lblEdytujSwjProfil.setHorizontalAlignment(SwingConstants.CENTER);
		        lblEdytujSwjProfil.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 18));
		        lblEdytujSwjProfil.setBounds(150, 100, 600, 50);
		        add(lblEdytujSwjProfil);
		        
		        txtJo = new JTextField();
		        txtJo.setBackground(Color.WHITE);
		        txtJo.setBounds(180, 460, 96, 19);
		        add(txtJo);
		        txtJo.setColumns(10);
		        
		        textField_1 = new JTextField();
		        textField_1.setColumns(10);
		        textField_1.setBackground(new Color(0, 128, 64));
		        textField_1.setBounds(180, 480, 96, 19);
		        add(textField_1);
		        
		        JSlider slider = new JSlider();
		        slider.setBounds(180, 510, 200, 6);
		        add(slider);
		        
		        JButton btnNewButton = new JButton("zapisz preferencje");
		        btnNewButton.setBounds(180, 530, 116, 21);
		        add(btnNewButton);
		        btnNewButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						List<String> genders = Arrays.asList(txtJo.getText().split(" "));
						List<String> faculties = Arrays.asList(textField_1.getText().split(" "));
						int age = slider.getValue();
						savePreferences(genders, faculties, 0, age);
						//System.out.println("wiek"+age);
						//System.out.println("wydział"+faculties);
						//System.out.println("płeć"+genders);
					}
				});
		        initializeMe(id);
			}		


	protected void savePreferences(List<String> genders, List<String> faculties, int ageMin, int ageMax) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
	       	 
			@Override
            protected Void doInBackground() throws Exception {
            	conn = sqlConn.connect();
            	String selectSql = "SELECT gender_id FROM genders WHERE gender_name IN " +
                        "(" + String.join(",", Collections.nCopies(genders.size(), "?")) + ")";
            	PreparedStatement selectStatement = conn.prepareStatement(selectSql);
			     for (int i = 1; i <= genders.size(); i++) {
			         selectStatement.setString(i, genders.get(i - 1));
			     }
			     ResultSet resultSet = selectStatement.executeQuery();
			     
			     String deleteSql = "DELETE FROM user_preferred_genders WHERE user_id = ?";
		            PreparedStatement deleteStatement = conn.prepareStatement(deleteSql);
		            deleteStatement.setInt(1, id);
		            deleteStatement.executeUpdate();

			
			     // Insert the preferred genders for the user
			     String insertSql = "INSERT INTO user_preferred_genders (user_id, gender_id) VALUES (?, ?)";
			     PreparedStatement insertStatement = conn.prepareStatement(insertSql);
			     while (resultSet.next()) {
			         int genderId = resultSet.getInt("gender_id");
			         insertStatement.setInt(1, id);
			         insertStatement.setInt(2, genderId);
			         insertStatement.executeUpdate();
			     }

			     selectSql = "SELECT faculty_id FROM faculties WHERE faculty_name IN " +
                         "(" + String.join(",", Collections.nCopies(faculties.size(), "?")) + ")";
			      selectStatement = conn.prepareStatement(selectSql);
			      for (int i = 1; i <= faculties.size(); i++) {
			          selectStatement.setString(i, faculties.get(i - 1));
			      }
			      resultSet = selectStatement.executeQuery();
			      
			      deleteSql = "DELETE FROM user_preferred_faculties WHERE user_id = ?";
		            deleteStatement = conn.prepareStatement(deleteSql);
		            deleteStatement.setInt(1, id);
		            deleteStatement.executeUpdate();
			
			      // Insert the preferred faculties for the user
			      insertSql = "INSERT INTO user_preferred_faculties (user_id, faculty_id) VALUES (?, ?)";
			      insertStatement = conn.prepareStatement(insertSql);
			      while (resultSet.next()) {
			          int facultyId = resultSet.getInt("faculty_id");
			          insertStatement.setInt(1, id);
			          insertStatement.setInt(2, facultyId);
			          insertStatement.executeUpdate();
			      }

			      PreparedStatement prepUp = conn.prepareStatement("UPDATE userdata SET age_min = (?), age_max = (?) WHERE user_id = (?)");
			      prepUp.setInt(1, ageMin);
			      prepUp.setInt(2, ageMax);
			      prepUp.setInt(3, id);
			      prepUp.executeUpdate();
			      //System.out.println("Preferred faculties inserted successfully!");
    			
                return null;
            }

            @Override
            protected void done() {
                try {
            		if (conn!= null)
    	    			conn.close();
            		swipePanel.initializeMyPreferences(id);
                } catch (Exception ex) {
                	JOptionPane.showMessageDialog(
                            null,"Błąd zapisu danych",
                            "Błąd zapisu danych",
                            JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(
                        null,"Zapisano",
                        "Zapisano dane",
                        JOptionPane.INFORMATION_MESSAGE);
            }

       };
       
       worker.execute();

		
	}


	public void save(String firstname, String lastname, String university, InputStream is, JFrame frame, String gender, int age, String description) 
	{
		 SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
	       	 
				@Override
	            protected Void doInBackground() throws Exception {
	            	conn = sqlConn.connect();
	    			PreparedStatement prep;

	    			prep = conn.prepareStatement("UPDATE userdata SET firstname = (?), lastname = (?), university = (?) , gender = (?), age = (?), image = (?), description = (?) WHERE user_id = (?)");
	    			prep.setString(1, firstname);
	    			prep.setString(2, lastname);
	    			prep.setString(3, university);
	    			prep.setString(4, gender);
	    			prep.setInt(5, age);
	    			prep.setBinaryStream(6,is, (int) f.length());
	    			prep.setString(7, description);
	    			prep.setLong(8, id);
	    			prep.executeUpdate();
	    			
	                return null;
	            }

	            @Override
	            protected void done() {
	                try {
	            		if (conn!= null)
	    	    			conn.close();
	                } catch (Exception ex) {
	                	JOptionPane.showMessageDialog(
	                            null,"Błąd zapisu danych",
	                            "Błąd zapisu danych",
	                            JOptionPane.ERROR_MESSAGE);
	                }
	                JOptionPane.showMessageDialog(
                            null,"Zapisano",
                            "Zapisano dane",
                            JOptionPane.INFORMATION_MESSAGE);
	            }

	       };
	       
	       worker.execute();

	}
        
	public void initializeMe(int id)
	{
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
	       	 
            @Override
            protected Void doInBackground() throws Exception {
            	conn = sqlConn.connect();
    			PreparedStatement prep;

    			prep = conn.prepareStatement("SELECT * FROM userdata WHERE user_id =(?)");
    			prep.setInt(1, id);
    			ResultSet rs = prep.executeQuery();
    			if (rs.next())
    			{
    				byte[] imageData = rs.getBytes("image");
    				Image imageTemp = new ImageIcon(new ImageIcon(imageData).getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH)).getImage();
    				me = new User(id, rs.getString("firstname"), rs.getString("lastname"), rs.getString("university"), rs.getString("gender"), rs.getInt("age"), imageTemp, rs.getString("description"));
    				textFieldSurname.setText(me.getLastname());
    				textFieldName.setText(me.getFirstname());
    				textFieldAge.setText(Integer.toString(me.getAge()));
    				comboBoxGender.setSelectedItem(me.getGender());
    				comboBoxFaculty.setSelectedItem(me.getUniversity());
    				labelPhoto.setIcon(new ImageIcon(me.getImage()));
    				textPaneDescription.setText(me.getDescription());
    			}
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
	
	
	public void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      
	      Graphics2D g2 = (Graphics2D)g;

	      Rectangle2D r2=new Rectangle2D.Double(0,0,getWidth(),getHeight());

	      Color c0=new Color(255, 255, 255), c1= new Color(255, 215, 230);

	      GradientPaint  gp = new GradientPaint(150, 200, c1, 450, 200, c0, false);
	      g2.setPaint(gp);
	      g2.fill(r2);
	      
	      g2.setPaint(new Color(240, 240, 240)); //szary
	 
	      g2.fill(new RoundRectangle2D.Double(120, 95, 660, 635, 40, 40));
	   
	      g2.drawImage(imageSwipe ,(int) rectangleSwipe.getX() ,(int) rectangleSwipe.getY(), (int) rectangleSwipe.getWidth(), (int) rectangleSwipe.getHeight(), null);
	     }
	 }


