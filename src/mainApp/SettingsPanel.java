package mainApp;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;

public class SettingsPanel extends JPanel {
	
	private JButton buttonSwipe, buttonColor, buttonChat;
	private JLabel textSettings;
	Color newColor;
	
	public SettingsPanel(JPanel panel ,JFrame frame, int id) {
		
		// ustawienia panelu
		
		setBounds(0, 0, 900, 800);
		setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		setLayout(null); 
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
        
        textSettings = new JLabel();
        textSettings.setText("Ustawienia");
        textSettings.setBounds(400, 15, 300, 50);
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
	      
	      /*
	      g2.setPaint(new Color(255, 240, 245)); // jasnorozowy
	      g2.fill(new RoundRectangle2D.Double(100, 420, 230, 50, 40, 40));
	      g2.fill(new RoundRectangle2D.Double(355, 420, 230, 50, 40, 40));
	      */
	     }
	

	 }


