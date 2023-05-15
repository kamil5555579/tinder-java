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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class SettingsPanel extends JPanel {
	
	private JButton buttonSwipe, buttonColor, buttonChat;
	private JLabel textSettings;
	Color newColor;
	private JTextPane txtDescription;
	private JComboBox comboBox;
	private JComboBox comboBox_2;
	private PTextField txtAge;
	private PTextField txtName;
	private PTextField txtSurname;
	private JButton imgButton;
	private JLabel imgLabel;
	private JButton btnRegister;
	private JFileChooser fileChooser = new JFileChooser();
	private File f = null;
	private ByteArrayInputStream is = null;
	private Image img;
	private SqlConnection sqlConn = new SqlConnection();
    private Connection conn;
	private int id;
	private User me;


	
	public SettingsPanel(JPanel panel ,JFrame frame, int id) {
		
		this.id=id;
		initializeMe(id);
		// ustawienia panelu
		
		setBounds(0, 0, 900, 800);
		setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		setLayout(null); 

		//przycisk przejścia do wiadomości
		

		/*
		buttonChat = new JButton("Wiadomości");
		buttonChat.setBorder(null);
		buttonChat.setBackground(new Color(255, 240, 245));
		buttonChat.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		buttonChat.setBounds(50, 50, 300, 50);
		buttonChat.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.next(panel);
            }
        });
		add(buttonChat);
		*/

		// przycisk przejścia do swipowania
		
		buttonSwipe = new JButton();
		buttonSwipe.setBorder(null);
		buttonSwipe.setBackground(new Color(0,0,0,0));
		
		buttonSwipe.setBounds(25, 25, 100, 100);
		try {
		    Image img = ImageIO.read(getClass().getResource("back3.png"));
		    buttonSwipe.setIcon(new ImageIcon(img));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		buttonSwipe.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.previous(panel);
            }
        });
		add(buttonSwipe);
        
		
		// label ustawienia
		
        textSettings = new JLabel();
        textSettings.setText("Ustawienia");
        textSettings.setBounds(350, 15, 300, 50);
        textSettings.setForeground(new Color(255, 100, 153));
		textSettings.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 40));
        add(textSettings);
        /*
        buttonColor=new JButton ("Wybierz kolor tła");
        buttonColor.setLocation(350, 600);
        buttonColor.setBackground(Color.white);
        buttonColor.setSize(250, 50);
        buttonColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			newColor = JColorChooser.showDialog(null,  "Wybierz kolor tła swojej aplikacji", getForeground());
			setBackground(newColor);
			}
		}); // fancy z wykładu
		
		add(buttonColor);
		*/
		//opis
		
				txtDescription = new JTextPane();
				//txtDescription.setHorizontalAlignment(SwingConstants.LEFT);
				txtDescription.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
				txtDescription.setCaretColor(new Color(0, 0, 0));
				txtDescription.setBounds(180, 320, 250, 230);
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
				comboBox.setBounds(180, 200, 215, 30);
				add(comboBox);
				//comboBox.setBackground(new Color(255, 240, 245));
				comboBox.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
				
				//wybór wydziału
				
				String[] faculty = {"Wydział", "Architektury", "Chemii", "Matematyki", "Fizyki", 
		  	    "Inny" };
				
				comboBox_2 = new JComboBox(faculty);
				comboBox_2.setBackground(new Color(255, 255, 255));
				//comboBox_2.setOpaque(true);
				comboBox_2.setBorder(null);
				comboBox_2.setBounds(180, 250, 215, 30);
				comboBox_2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
				comboBox_2.setBackground(new Color(240, 240, 240));
				add(comboBox_2);
			

				
				
				//wiek
				
				txtAge = new PTextField("Wiek");
				txtAge.setBorder(null);
				txtAge.setBounds(480, 200, 215, 30);
				add(txtAge);
				txtAge.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
				txtAge.setBackground(new Color(240, 240, 240));
				//txtAge.setBackground(new Color(255, 240, 245));
				txtAge.setColumns(10);
				
				//imię
				
				txtName = new PTextField("Imię");
				txtName.setBorder(null);
				txtName.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
				txtName.setColumns(10);
				txtName.setBackground(new Color(240, 240, 240));
				txtName.setBounds(180, 150, 215, 30);
				add(txtName);
				
				//nazwisko
				
				txtSurname = new PTextField("Nazwisko");
				txtSurname.setBorder(null);
				txtSurname.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
				((JTextField) txtSurname).setColumns(10);
				//txtSurname.setBackground(new Color(255, 240, 245));
				txtSurname.setBackground(new Color(240, 240, 240));
				txtSurname.setBounds(480, 150, 215, 30);
				add(txtSurname);
				
				//zdjecie
				
				imgButton = new JButton("Wybierz zdjęcie");
				imgButton.setHorizontalAlignment(SwingConstants.LEFT);
				imgButton.setBackground(new Color(255, 255, 255));
				imgButton.setBorder(null);
				//imgButton.setBackground(new Color(255, 240, 245));
				imgButton.setBackground(new Color(240, 240, 240));
				imgButton.setBounds(480, 250, 215, 30);
				add(imgButton);
				
				imgLabel = new JLabel("");
				imgLabel.setBounds(480,275,250,250);
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
					      Image imgTemp = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
					      img = new ImageIcon(icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH)).getImage();
					      imgLabel.setIcon(new ImageIcon(imgTemp));
					}
			
					}});
				
				//zapisanie zmian
				
				btnRegister = new JButton("Zapisz");
				btnRegister.setBackground(new Color(255, 240, 245));
				btnRegister.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 18));
				btnRegister.setBounds(200, 600, 475, 40);
				btnRegister.setBorder(null);
				add(btnRegister);
				
				btnRegister.addActionListener( new ActionListener()
						{

							@Override
							public void actionPerformed(ActionEvent e) {
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
					
						});
				
		        add(btnRegister);
		        
		        JLabel lblOpowiedzOSobie = new JLabel("Opowiedz o sobie");
		        lblOpowiedzOSobie.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		        lblOpowiedzOSobie.setBounds(165, 300, 180, 25);
		        add(lblOpowiedzOSobie);
		        
		        JLabel lblEdytujSwjProfil = new JLabel("Edytuj Swój profil!");
		        lblEdytujSwjProfil.setHorizontalAlignment(SwingConstants.CENTER);
		        lblEdytujSwjProfil.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 18));
		        lblEdytujSwjProfil.setBounds(150, 100, 600, 50);
		        add(lblEdytujSwjProfil);
			
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
    				me = new User(id, rs.getString("firstname"), rs.getString("lastname"), rs.getString("university"), rs.getString("gender"), rs.getInt("age"), imageTemp);
    				txtSurname.setText(me.getLastname());
    				txtName.setText(me.getFirstname());
    				txtAge.setText(Integer.toString(me.getAge()));
    				comboBox.setSelectedItem(me.getGender());
    				comboBox_2.setSelectedItem(me.getUniversity());
    
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

	      Color c0=new Color(245, 245, 245), c1= new Color(255, 240, 245);

	      GradientPaint  gp = new GradientPaint(150, 200, c1, 450, 200, c0, false);
	      g2.setPaint(gp);
	      g2.fill(r2);
	      
	      g2.setPaint(new Color(240, 240, 240)); //szary
	 
	      g2.fill(new RoundRectangle2D.Double(120, 95, 660, 635, 40, 40));
	   
	     }
	 }


