package mainApp;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
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
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mysql.jdbc.Connection;

import utilities.RangeSlider;
import utilities.RangeSliderUI;
import utilities.PTextField;
import utilities.SqlConnection;
import utilities.User;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.JSlider;

public class SettingsPanel extends JPanel {
	
	private JLabel labelSettings, labelGender, labelFaculties, labelAge;
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
	JPanel panelCheckBox;
	JPanel panelCheckBox2;
	List<String> genders;
	List<String> faculties;
	JCheckBox
	cb1 = new JCheckBox("Kobiety"),
	cb2 = new JCheckBox("Mężczyźni"),
	cb3 = new JCheckBox("Inne");	
	JCheckBox
	f1 = new JCheckBox("Architektury"),
	f2 = new JCheckBox("Administracji"),
	f3 = new JCheckBox("Budownictwa"),
	f4 = new JCheckBox("Chemii"),
	f5 = new JCheckBox("EITI"),
	f6 = new JCheckBox("Elektryczny"),
	f7 = new JCheckBox("Fizyki"),
	f8 = new JCheckBox("IBHIŚ"),
	f9 = new JCheckBox("Matematyki"),
	f10 = new JCheckBox("MEL"),
	f11 = new JCheckBox("SiMR"),
	f12 = new JCheckBox("Transportu"),
	f13 = new JCheckBox("Zarządzania"),
	f14 = new JCheckBox("Inny");
	private RangeSlider rangeSlider = new RangeSlider();
	private JLabel rangeSliderValue1 = new JLabel();
	private JLabel rangeSliderValue2 = new JLabel();
	
	
	
	
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
				
		String[] faculty = {"Wydział", "Architektury","Administracji", "Budownictwa", "Chemii","EITI","Elektryczny", "Fizyki", "IBHIŚ","Matematyki","Mechatroniki", "MEL","SiMR","Transportu","Zarządzania"
		  	    ,"Inny" };
				
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
		labelPhoto.setBounds(480,260,200,200);
		add(labelPhoto);	
		
		//przycisk do zmiany zdjecia
				
		buttonChoosePhoto = new JButton("Wybierz zdjęcie");
		buttonChoosePhoto.setHorizontalAlignment(SwingConstants.LEFT);
		buttonChoosePhoto.setBackground(new Color(255, 255, 255));
		buttonChoosePhoto.setBorder(null);
		buttonChoosePhoto.setBackground(new Color(240, 240, 240));
		buttonChoosePhoto.setBounds(480, 230, 215, 30);
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
					      Image imgTemp = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
					      image = new ImageIcon(icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH)).getImage();
					      labelPhoto.setIcon(new ImageIcon(imgTemp));
					}
			
					}});
				
				//zapisanie zmian
				
		buttonRegister = new JButton("Zapisz");
		buttonRegister.setBackground(new Color(255, 240, 245));
		buttonRegister.setFont(new Font("Dialog", Font.ITALIC, 14));
		buttonRegister.setBounds(250, 375, 180, 20);
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
		        lblEdytujSwjProfil.setBounds(150, 100, 600, 40);
		        add(lblEdytujSwjProfil);
		        
		        JLabel labelPreferences = new JLabel("Edytuj Swoje preferencje");
		        labelPreferences.setHorizontalAlignment(SwingConstants.CENTER);
		        labelPreferences.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 18));
		        labelPreferences.setBounds(150, 470, 600, 40);
		        add(labelPreferences);
		        
		        
			  ActionListener cbListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) 
				{
				      Object obj = e.getSource();
				      genders = new LinkedList<String>(); 
				        if (cb1.isSelected()) { 
				        	genders.add("Kobieta");
				        	}
				      
				        if (cb2.isSelected()) {
				        	genders.add("Mężczyzna");
				        	} 
				        if (cb3.isSelected()) { 
				        	genders.add("Inna");}
				          
				      for (int i = 0; i<genders.size(); i++) {
				    	  System.out.println("płeć"+genders.get(i));
		  	  			}
				      
				}
			  };
			  
			  cb1.addActionListener(cbListener);
			  cb2.addActionListener(cbListener);
			  cb3.addActionListener(cbListener);
			   
			  labelGender = new JLabel("Wybierz preferecje płci");
			  labelGender.setBounds(180, 510, 300, 20);
			  labelGender.setFont(new Font("Dialog", Font.ITALIC, 14));
			  add(labelGender);
			  
			
			  
			  panelCheckBox = new JPanel();
			  panelCheckBox.setLayout(new FlowLayout(FlowLayout.LEFT));
			  	    
			  panelCheckBox.add(cb1);
			  panelCheckBox.add(cb2);
			  panelCheckBox.add(cb3);
			  panelCheckBox.setBounds(180, 530, 300, 30);
			  add( panelCheckBox);
			
			  
			  
		        
		        ActionListener cbListener2 = new ActionListener() {
					public void actionPerformed(ActionEvent e) 
					
					{
					      Object obj = e.getSource();
					      faculties = new LinkedList<String>(); 
					        if (f1.isSelected()) faculties.add("Architektury");
					        
					        if (f2.isSelected()) faculties.add("Administracji");
					        
					        if (f3.isSelected()) faculties.add("Budownictwa");
					        
					        if (f4.isSelected()) faculties.add("Chemii");
					        
					        if (f5.isSelected()) faculties.add("EITI");
					        
					        if (f6.isSelected()) faculties.add("Elektryczny");
					        
					        if (f7.isSelected()) faculties.add("Fizyki");
					        
					        if (f8.isSelected()) faculties.add("IBHIŚ");
					        
					        if (f9.isSelected()) faculties.add("Matematyki");
					        
					        if (f10.isSelected()) faculties.add("Mechatroniki");
					        
					        if (f11.isSelected()) faculties.add("MEL");
					        
					        if (f12.isSelected()) faculties.add("SiMR");
					        
					        if (f13.isSelected()) faculties.add("Transportu");
					        
					        if (f14.isSelected()) faculties.add("Zarządzania");
					       
					        
					       
					        
					          
					      for (int i = 0; i<faculties.size(); i++) {
					    	  System.out.println("wydzial "+faculties.get(i));
			  	  			}
					      
					}
				  };
				  
				  f1.addActionListener(cbListener2);
				  f2.addActionListener(cbListener2);
				  f3.addActionListener(cbListener2);
				  f4.addActionListener(cbListener2);
				  f5.addActionListener(cbListener2);
				  f6.addActionListener(cbListener2);
				  f7.addActionListener(cbListener2);
				  f8.addActionListener(cbListener2);
				  f9.addActionListener(cbListener2);
				  f10.addActionListener(cbListener2);
				  f11.addActionListener(cbListener2);
				  f12.addActionListener(cbListener2);
				  f13.addActionListener(cbListener2);
				  f14.addActionListener(cbListener2);
				   
				  labelFaculties = new JLabel("Wybierz preferecje wydziałów");
				  labelFaculties.setBounds(180, 570, 400, 20);
				  labelFaculties.setFont(new Font("Dialog", Font.ITALIC, 14));
				  add(labelFaculties);
				  
				  panelCheckBox2 = new JPanel();
				  panelCheckBox2.setLayout(new FlowLayout(FlowLayout.LEADING));
				  	    
				  panelCheckBox2.add(f1);
				  panelCheckBox2.add(f2);
				  panelCheckBox2.add(f3);
				  panelCheckBox2.add(f4);
				  panelCheckBox2.add(f5);
				  panelCheckBox2.add(f6);
				  panelCheckBox2.add(f7);
				  panelCheckBox2.add(f8);
				  panelCheckBox2.add(f9);
				  panelCheckBox2.add(f10);
				  panelCheckBox2.add(f11);
				  panelCheckBox2.add(f12);
				  panelCheckBox2.add(f13);
				  panelCheckBox2.add(f14);
				  panelCheckBox2.setBounds(180, 590, 500, 100);
				  add( panelCheckBox2);

				  labelAge = new JLabel("Wybierz preferecje wieku");
				  labelAge.setBounds(480, 510, 300, 20);
				  labelAge.setFont(new Font("Dialog", Font.ITALIC, 14));
				  add(labelAge);
				  
				// slider z dwoma suwakami
		        rangeSliderValue1.setHorizontalAlignment(JLabel.LEFT);
		        rangeSliderValue2.setHorizontalAlignment(JLabel.LEFT);
		        
		        rangeSlider.setPreferredSize(new Dimension(240, rangeSlider.getPreferredSize().height));
		        rangeSlider.setMinimum(0);
		        rangeSlider.setMaximum(100);
		        
		        // Add listener to update display.
		        rangeSlider.addChangeListener(new ChangeListener() {
		            public void stateChanged(ChangeEvent e) {
		                RangeSlider slider = (RangeSlider) e.getSource();
		                rangeSliderValue1.setText("Min wiek: "+String.valueOf(slider.getValue()));
		                rangeSliderValue2.setText("Max wiek: "+String.valueOf(slider.getUpperValue()));
		            }
		        });
		    
		       rangeSlider.setValue(20);
		       rangeSlider.setUpperValue(80);
		       rangeSliderValue1.setText("Min wiek: "+String.valueOf(rangeSlider.getValue()));
		       rangeSliderValue2.setText("Max wiek: "+String.valueOf(rangeSlider.getUpperValue()));
		       rangeSlider.setBounds(480, 540, 200, 20);
		       add(rangeSlider);
		       
		       rangeSliderValue1.setFont(new Font("Dialog", Font.ITALIC, 11));
		       rangeSliderValue1.setBounds(480, 560, 100, 20);
		       add(rangeSliderValue1);
		       
		       rangeSliderValue2.setFont(new Font("Dialog", Font.ITALIC, 11));
		       rangeSliderValue2.setBounds(580, 560, 100, 20);
		       add(rangeSliderValue2);
		       
		        
		        JButton btnNewButton = new JButton("Zapisz preferencje");
		        btnNewButton.setBounds(350, 690, 180, 20);
		        btnNewButton.setBackground(new Color(255, 240, 245));
		        btnNewButton.setFont(new Font("Dialog", Font.ITALIC, 14));
		        btnNewButton.setBorder(null);
		        add(btnNewButton);
		        btnNewButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int ageMin = rangeSlider.getValue();
						int ageMax = rangeSlider.getUpperValue();
						savePreferences(genders, faculties, ageMin, ageMax);
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
			      System.out.println("Preferred faculties inserted successfully!");
			      System.out.println("save"+faculties);
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


