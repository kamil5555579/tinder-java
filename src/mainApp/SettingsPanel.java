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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
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
import javax.swing.JRadioButton;
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
	SqlConnection sqlConn = new SqlConnection();
    Connection conn;
	int id;
	User me;
	private Image image;
	private boolean refresh = false;
	private BufferedImage imageSwipe;
	private Rectangle rectangleSwipe = new Rectangle(10 ,0 ,70 ,70);
	private SwipePanel swipePanel;
	private JPanel panelCheckBox;
	private JPanel panelCheckBox2;
	private List<String> genders;
	private List<String> faculties;
	private int length;
	
	private JRadioButton radioButton1;
	private JRadioButton radioButton2;
	private JRadioButton radioButton3;
	
	private JRadioButton radioButton10;
	private JRadioButton radioButton11;
	private JRadioButton radioButton12;
	private JRadioButton radioButton13;
	private JRadioButton radioButton14;
	private JRadioButton radioButton15;
	private JRadioButton radioButton16;
	private JRadioButton radioButton17;
	private JRadioButton radioButton18;
	private JRadioButton radioButton19;
	private JRadioButton radioButton20;
	private JRadioButton radioButton21;
	private JRadioButton radioButton22;
	private JRadioButton radioButton23;
	private JRadioButton radioButton24;
	
	private RangeSlider rangeSlider = new RangeSlider();
	private JLabel rangeSliderValue1 = new JLabel();
	private JLabel rangeSliderValue2 = new JLabel();

	public SettingsPanel(JPanel panel ,JFrame frame, int id,SwipePanel swipePanel) {
		
		this.id=id;
		this.swipePanel=swipePanel;
		
		setBounds(0, 0, 900, 800);
		setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		setLayout(null); 
		
		//strzałka powrotu do SwipePanelu
		try {
			imageSwipe = ImageIO.read(getClass().getResource("arrow_settings.PNG"));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		
		addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
				if (e.getX()>rectangleSwipe.getX() && e.getX()<(rectangleSwipe.getX()+rectangleSwipe.getWidth()) && e.getY()>rectangleSwipe.getY() && e.getY()<(rectangleSwipe.getHeight()+rectangleSwipe.getY()))
				{	
					if(refresh==true) {
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
					}else {
					CardLayout cardLayout = (CardLayout) panel.getLayout();
					cardLayout.previous(panel);
					}
					
				}
				repaint();
	        }
		});

		
		// label ustawienia
		
        labelSettings = new JLabel();
        labelSettings.setText("Ustawienia");
        labelSettings.setBounds(350, 15, 300, 50);
        labelSettings.setForeground(new Color(255, 100, 153));
		labelSettings.setFont(new Font("LM Sans", Font.ITALIC, 30));
        add(labelSettings);
       
		//opis
        
		textPaneDescription = new JTextPane();
		textPaneDescription.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		textPaneDescription.setCaretColor(new Color(0, 0, 0));
		textPaneDescription.setBounds(180, 310, 250, 140);
		textPaneDescription.setBackground(new Color(240, 240, 240));
		textPaneDescription.setFont(new Font("LM Sans", Font.ITALIC, 12));
		textPaneDescription.setBorder(null);
		add(textPaneDescription);
				
		//wybór płci
		
		String[] gender = {"Płeć", "Mężczyzna", "Kobieta","Inna" };
		comboBoxGender = new JComboBox(gender);
		comboBoxGender.setBackground(new Color(240, 240, 240));
		comboBoxGender.setBounds(180, 190, 215, 30);
		comboBoxGender.setFont(new Font("LM Sans", Font.ITALIC, 14));
		add(comboBoxGender);
				
		//wybór wydziału
				
		String[] faculty = {"Wydział", "Architektury","Administracji", "Budownictwa", "Chemii","EITI","Elektryczny", "Fizyki", "IBHIŚ","Matematyki","Mechatroniki", "MEL","SiMR","Transportu","Zarządzania"
		  	    ,"Inny" };
				
		comboBoxFaculty = new JComboBox(faculty);
		comboBoxFaculty.setBackground(new Color(255, 255, 255));
		comboBoxFaculty.setBorder(null);
		comboBoxFaculty.setBounds(180, 240, 215, 30);
		comboBoxFaculty.setFont(new Font("LM Sans", Font.ITALIC, 14));
		comboBoxFaculty.setBackground(new Color(240, 240, 240));
		add(comboBoxFaculty);
			
		//wiek
				
		textFieldAge = new PTextField("Wiek");
		textFieldAge.setBorder(null);
		textFieldAge.setBounds(480, 190, 215, 30);
		textFieldAge.setFont(new Font("LM Sans", Font.ITALIC, 14));
		textFieldAge.setBackground(new Color(240, 240, 240));
		textFieldAge.setColumns(10);
		add(textFieldAge);
		
		//imię
				
		textFieldName = new PTextField("Imię");
		textFieldName.setBorder(null);
		textFieldName.setFont(new Font("LM Sans", Font.ITALIC, 14));
		textFieldName.setColumns(10);
		textFieldName.setBackground(new Color(240, 240, 240));
		textFieldName.setBounds(180, 150, 215, 30);
		add(textFieldName);
				
		//nazwisko
				
		textFieldSurname = new PTextField("Nazwisko");
		textFieldSurname.setBorder(null);
		textFieldSurname.setFont(new Font("LM Sans", Font.ITALIC, 14));
		((JTextField) textFieldSurname).setColumns(10);
		textFieldSurname.setBackground(new Color(240, 240, 240));
		textFieldSurname.setBounds(480, 150, 215, 30);
		add(textFieldSurname);
		
		//zdjęcie
		labelPhoto = new JLabel("");
		labelPhoto.setBounds(480,250,200,200);
		add(labelPhoto);	
		
		//przycisk do zmiany zdjecia
				
		buttonChoosePhoto = new JButton("Wybierz zdjęcie");
		buttonChoosePhoto.setFont(new Font("LM Sans",  Font.ITALIC, 12));
		buttonChoosePhoto.setHorizontalAlignment(SwingConstants.LEFT);
		buttonChoosePhoto.setBackground(new Color(255, 255, 255));
		buttonChoosePhoto.setBorder(null);
		buttonChoosePhoto.setBackground(new Color(240, 240, 240));
		buttonChoosePhoto.setBounds(480, 220, 215, 30);
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
				
		buttonRegister = new JButton("Zapisz profil");
		buttonRegister.setBackground(new Color(255, 240, 245));
		buttonRegister.setFont(new Font("LM Sans", Font.ITALIC, 14));
		buttonRegister.setBounds(350, 455, 180, 20);
		buttonRegister.setBorder(null);
		add(buttonRegister);
				
		buttonRegister.addActionListener( new ActionListener()
				{
					String firstname;
					String lastname;
					String university;
					String gender;
					int age;
					String description;

					@Override
					public void actionPerformed(ActionEvent e) {
						
						try {
								firstname = textFieldName.getText();
								lastname = textFieldSurname.getText();
								university = (String) comboBoxFaculty.getSelectedItem();
								gender = (String) comboBoxGender.getSelectedItem();
								age = Integer.parseInt(textFieldAge.getText());
								description = textPaneDescription.getText();
								
								if (textFieldName.getText().equals("Imię")) {
									JOptionPane.showMessageDialog(
				                            null,"Nie podano imienia. Podaj imię!",
				                            "Błąd uzupełniania danych",
				                            JOptionPane.ERROR_MESSAGE);
								}else if (textFieldSurname.getText().equals("Nazwisko")) {
										JOptionPane.showMessageDialog(
					                            null,"Nie podano nazwiska. Podaj nazwisko!",
					                            "Błąd uzupełniania danych",
					                            JOptionPane.ERROR_MESSAGE);
								} else {
								
									
											try {
												ByteArrayOutputStream os = new ByteArrayOutputStream();
												BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
												bufferedImage.getGraphics().drawImage(image, 0, 0 , null);
												ImageIO.write( bufferedImage, "gif", os);
												is = new ByteArrayInputStream(os.toByteArray());
												length = is.available();
												os.close();
												save(firstname, lastname, university, is, frame, gender, age, description);
												System.out.println("Zmieniono");	
											} catch (FileNotFoundException e1) {
												JOptionPane.showMessageDialog(
							                            null,"Błąd dostępu do pliku.",
							                            "Błąd dostępu do danych",
							                            JOptionPane.ERROR_MESSAGE);
											} catch (IOException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
										}
									}
								
							 catch (NumberFormatException e2) {
								
								JOptionPane.showMessageDialog(
			                            null,"Nie możesz w ten sposób zmienić swoich danych!",
			                            "Błąd zmiany danych",
			                            JOptionPane.ERROR_MESSAGE);
								
							}			
					}
					
					
				});
		
		        add(buttonRegister);
		        
		        JLabel lblOpowiedzOSobie = new JLabel("Opowiedz o sobie");
		        lblOpowiedzOSobie.setFont(new Font("LM Sans", Font.ITALIC, 14));
		        lblOpowiedzOSobie.setBounds(180, 290, 180, 25);
		        add(lblOpowiedzOSobie);
		        
		        JLabel lblEdytujSwjProfil = new JLabel("Edytuj Swój profil");
		        lblEdytujSwjProfil.setHorizontalAlignment(SwingConstants.CENTER);
		        lblEdytujSwjProfil.setFont(new Font("LM Sans", Font.BOLD | Font.ITALIC, 18));
		        lblEdytujSwjProfil.setBounds(150, 100, 600, 40);
		        add(lblEdytujSwjProfil);
		        
		        JLabel labelPreferences = new JLabel("Edytuj Swoje preferencje");
		        labelPreferences.setHorizontalAlignment(SwingConstants.CENTER);
		        labelPreferences.setFont(new Font("LM Sans", Font.BOLD | Font.ITALIC, 18));
		        labelPreferences.setBounds(150, 470, 600, 40);
		        add(labelPreferences);
		        
		        
		        labelGender = new JLabel("Wybierz preferecje płci");
				labelGender.setBounds(180, 510, 300, 20);
				labelGender.setFont(new Font("LM Sans", Font.ITALIC, 14));
				add(labelGender);
		        
		        genders = new LinkedList<String>(); 
		        
		        radioButton1 = new JRadioButton("Kobiety");
				
				radioButton1.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton1.setSelected(true);
						genders.clear();
						genders.add("Kobieta");
						
					}
				}); 
		        
		        radioButton2 = new JRadioButton("Mężczyźni");
				
				radioButton2.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton2.setSelected(true);
						genders.clear();
						genders.add("Mężczyzna");
					}
				}); 
				
				
				radioButton3 = new JRadioButton("Inne");
				radioButton3.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton3.setSelected(true);
						genders.clear();
						genders.add("Inna");
					}
				}); 
				
				if(swipePanel.myPreferences.getGenders().isEmpty()==false) {
					if(swipePanel.myPreferences.getGenders().get(0).equals("Kobieta")) {
						radioButton1.setSelected(true);
						genders.clear();
						genders.add("Kobieta");
					} else if(swipePanel.myPreferences.getGenders().get(0).equals("Mężczyzna")) {
						radioButton2.setSelected(true);
						genders.clear();
						genders.add("Mężczyzna");
					} else if(swipePanel.myPreferences.getGenders().get(0).equals("Inna")) {
						radioButton3.setSelected(true);
						genders.clear();
						genders.add("Inna");
					}
				}
				
				panelCheckBox = new JPanel();
				panelCheckBox.setLayout(new FlowLayout(FlowLayout.LEFT));
				  
				 	    
				panelCheckBox.add(radioButton1);
				panelCheckBox.add(radioButton2);
				panelCheckBox.add(radioButton3);
				panelCheckBox.setBounds(180, 530, 300, 30);
				add( panelCheckBox);
				
				ButtonGroup group = new ButtonGroup();
				group.add(radioButton3);
				group.add(radioButton2);
				group.add(radioButton1);
		      
				
			  
				faculties = new LinkedList<String>(); 
				  
				radioButton10 = new JRadioButton("Architektury");
				radioButton10.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton10.setSelected(true);
						faculties.clear();
						faculties.add("Architektury");
					}
				}); 
				radioButton11 = new JRadioButton("Administracji");
				radioButton11.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton11.setSelected(true);
						faculties.clear();
						faculties.add("Administracji");
					}
				}); 
				radioButton12 = new JRadioButton("Budownictwa");
				radioButton12.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton12.setSelected(true);
						faculties.clear();
						faculties.add("Budownictwa");
					}
				}); 
				radioButton13 = new JRadioButton("Chemii");
				radioButton13.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton13.setSelected(true);
						faculties.clear();
						faculties.add("Chemii");
					}
				}); 
				radioButton14 = new JRadioButton("EITI");
				radioButton14.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton14.setSelected(true);
						faculties.clear();
						faculties.add("EITI");
					}
				}); 
				radioButton15 = new JRadioButton("Elektryczny");
				radioButton15.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton15.setSelected(true);
						faculties.clear();
						faculties.add("Elektryczny");
					}
				}); 
				radioButton16 = new JRadioButton("Fizyki");
				radioButton16.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton16.setSelected(true);
						faculties.clear();
						faculties.add("Fizyki");
					}
				}); 
				radioButton17 = new JRadioButton("IBHIŚ");
				radioButton17.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton17.setSelected(true);
						faculties.clear();
						faculties.add("IBHIŚ");
					}
				}); 
				radioButton18 = new JRadioButton("Matematyki");
				radioButton18.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton18.setSelected(true);
						faculties.clear();
						faculties.add("Matematyki");
					}
				}); 
				radioButton19 = new JRadioButton("MEL");
				radioButton19.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton19.setSelected(true);
						faculties.clear();
						faculties.add("MEL");
					}
				}); 
				radioButton20 = new JRadioButton("SiMR");
				radioButton20.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton20.setSelected(true);
						faculties.clear();
						faculties.add("SiMR");
					}
				}); 
				radioButton21 = new JRadioButton("Transportu");
				radioButton21.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton21.setSelected(true);
						faculties.clear();
						faculties.add("Transportu");
					}
				}); 
				radioButton22 = new JRadioButton("Zarządzania");
				radioButton22.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton22.setSelected(true);
						faculties.clear();
						faculties.add("Zarządzania");
					}
				}); 
				radioButton23 = new JRadioButton("Inny");
				radioButton23.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton23.setSelected(true);
						faculties.clear();
						faculties.add("Inny");
					}
				}); 
				radioButton24 = new JRadioButton("Mechatronik");
				radioButton24.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						radioButton24.setSelected(true);
						faculties.clear();
						faculties.add("Mechatroniki");
					}
				}); 
	
				ButtonGroup group2 = new ButtonGroup();
				group2.add(radioButton10);
				group2.add(radioButton11);
				group2.add(radioButton12);
				group2.add(radioButton13);
				group2.add(radioButton14);
				group2.add(radioButton15);
				group2.add(radioButton16);
				group2.add(radioButton17);
				group2.add(radioButton18);
				group2.add(radioButton19);
				group2.add(radioButton20);
				group2.add(radioButton21);
				group2.add(radioButton23);
				group2.add(radioButton22);
				group2.add(radioButton24);
				
				   
				labelFaculties = new JLabel("Wybierz preferecje wydziałów");
				labelFaculties.setBounds(180, 570, 400, 20);
				labelFaculties.setFont(new Font("LM Sans", Font.ITALIC, 14));
				add(labelFaculties);
				 
				if(swipePanel.myPreferences.getFaculties().isEmpty()==false) {
				  if(swipePanel.myPreferences.getFaculties().get(0).equals("Architektury")) {
					  radioButton10.setSelected(true);
					  faculties.clear();
					  faculties.add("Architektury");
				  } else if(swipePanel.myPreferences.getFaculties().get(0).equals("Administracji")) {
					  radioButton11.setSelected(true);
					  faculties.clear();
					  faculties.add("Administracji");
				  } else if(swipePanel.myPreferences.getFaculties().get(0).equals("Chemii")) {
					  radioButton13.setSelected(true);
					  faculties.clear();
					  faculties.add("Chemii");
				  }else if(swipePanel.myPreferences.getFaculties().get(0).equals("Fizyki")) {
					  radioButton16.setSelected(true);
					  faculties.clear();
					  faculties.add("Fizyki");
				  }else if(swipePanel.myPreferences.getFaculties().get(0).equals("EITI")) {
					  radioButton14.setSelected(true);
					  faculties.clear();
					  faculties.add("EITI");
				  }else if(swipePanel.myPreferences.getFaculties().get(0).equals("Elektryczny")) {
					  radioButton15.setSelected(true);
					  faculties.clear();
					  faculties.add("Elektryczny");
				  }else if(swipePanel.myPreferences.getFaculties().get(0).equals("IBHIŚ")) {
					  radioButton17.setSelected(true);
					  faculties.clear();
					  faculties.add("IBHIŚ");
				  }else if(swipePanel.myPreferences.getFaculties().get(0).equals("Matematyki")) {
					  radioButton18.setSelected(true);
					  faculties.clear();
					  faculties.add("Matematyki");
				  }else if(swipePanel.myPreferences.getFaculties().get(0).equals("MEL")) {
					  radioButton19.setSelected(true);
					  faculties.clear();
					  faculties.add("MEL");
				  }else if(swipePanel.myPreferences.getFaculties().get(0).equals("SiMR")) {
					  radioButton20.setSelected(true);
					  faculties.clear();
					  faculties.add("SiMR");
				  }else if(swipePanel.myPreferences.getFaculties().get(0).equals("Transportu")) {
					  radioButton21.setSelected(true);
					  faculties.clear();
					  faculties.add("Transportu");
				  }else if(swipePanel.myPreferences.getFaculties().get(0).equals("Zarządzania")) {
					  radioButton22.setSelected(true);
					  faculties.clear();
					  faculties.add("Zarządzania");
				  }else if(swipePanel.myPreferences.getFaculties().get(0).equals("Inny")) {
					  radioButton23.setSelected(true);
					  faculties.clear();
					  faculties.add("Inny");
				  }else if(swipePanel.myPreferences.getFaculties().get(0).equals("Mechatroniki")) {
					  radioButton24.setSelected(true);
					  faculties.clear();
					  faculties.add("Mechatroniki");
				  }
				}
				  panelCheckBox2 = new JPanel();
				  panelCheckBox2.setLayout(new FlowLayout(FlowLayout.LEADING));
				  	    
				  panelCheckBox2.add(radioButton10);
				  panelCheckBox2.add(radioButton11);
				  panelCheckBox2.add(radioButton12);
				  panelCheckBox2.add(radioButton13);
				  panelCheckBox2.add(radioButton14);
				  panelCheckBox2.add(radioButton15);
				  panelCheckBox2.add(radioButton16);
				  panelCheckBox2.add(radioButton17);
				  panelCheckBox2.add(radioButton18);
				  panelCheckBox2.add(radioButton19);
				  panelCheckBox2.add(radioButton20);
				  panelCheckBox2.add(radioButton21);
				  panelCheckBox2.add(radioButton22);
				  panelCheckBox2.add(radioButton23);
				  
				  panelCheckBox2.setBounds(180, 590, 500, 100);
				  add( panelCheckBox2);
				  
				  labelAge = new JLabel("Wybierz preferecje wieku");
				  labelAge.setBounds(480, 510, 300, 20);
				  labelAge.setFont(new Font("LM Sans", Font.ITALIC, 14));
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
			       
			       rangeSlider.setValue(swipePanel.myPreferences.getAgeMin());
			       rangeSlider.setUpperValue(swipePanel.myPreferences.getAgeMax());
			       
			       rangeSliderValue1.setText("Min wiek: "+String.valueOf(rangeSlider.getValue()));
			       rangeSliderValue2.setText("Max wiek: "+String.valueOf(rangeSlider.getUpperValue()));
			       rangeSlider.setBounds(480, 540, 200, 20);
			       add(rangeSlider);
			       
			       rangeSliderValue1.setFont(new Font("LM Sans", Font.ITALIC, 11));
			       rangeSliderValue1.setBounds(480, 560, 100, 20);
			       add(rangeSliderValue1);
			       
			       rangeSliderValue2.setFont(new Font("LM Sans", Font.ITALIC, 11));
			       rangeSliderValue2.setBounds(580, 560, 100, 20);
			       add(rangeSliderValue2);
			       
			        
			        JButton btnNewButton = new JButton("Zapisz preferencje");
			        btnNewButton.setBounds(350, 690, 180, 20);
			        btnNewButton.setBackground(new Color(255, 240, 245));
			        btnNewButton.setFont(new Font("LM Sans 10", Font.ITALIC, 14));
			        btnNewButton.setBorder(null);
			        add(btnNewButton);
			        btnNewButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							int ageMin = rangeSlider.getValue();
							int ageMax = rangeSlider.getUpperValue();
							savePreferences(genders, faculties, ageMin, ageMax);
							refresh=true;
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
	    			prep.setBinaryStream(6,is, length);
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
    				ImageIcon icon = new ImageIcon(me.getImage());
				    Image imgTemp = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
				    image = new ImageIcon(icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH)).getImage();
				    labelPhoto.setIcon(new ImageIcon(imgTemp));
				    
    				//labelPhoto.setIcon(new ImageIcon(me.getImage()));
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


