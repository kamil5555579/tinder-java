package mainApp;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;

public class SettingsPanel extends JPanel {
	
	private JButton buttonSwipe, buttonColor;
	private JLabel textSettings;
	Color newColor;
	
	public SettingsPanel(JPanel panel ,JFrame frame, int id) {
		
		// ustawienia panelu
		
		setBounds(100, 100, 900, 900);
		setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		setLayout(null); 

		buttonSwipe = new JButton("Zpisz zmiany");
		buttonSwipe.setBackground(new Color(255, 240, 245));
		buttonSwipe.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		buttonSwipe.setBounds(350, 700, 225, 65);
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
        textSettings.setBounds(300, 0, 300, 100);
        textSettings.setForeground(Color.WHITE);
		textSettings.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 50));
        add(textSettings);
        
        buttonColor=new JButton ("Wybierz kolor tła");
        buttonColor.setLocation(350, 600);
        buttonColor.setSize(250, 50);
        buttonColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
			newColor = JColorChooser.showDialog(null,  "Wybierz kolor tła swojej aplikacji", getForeground());
			setBackground(newColor);
			}
		}); // fancy z wykładu
		
		add(buttonColor);
		
        
	}
	
	 public void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      
	      Graphics2D g2 = (Graphics2D)g;
	    
	      g2.setPaint(new Color(240, 240, 240)); //szary
	 
	      g2.fill(new RoundRectangle2D.Double(50, 100, 800, 725, 40, 40));
	      
	      
	      g2.setPaint(new Color(255, 240, 245)); // jasnorozowy
	      g2.fill(new RoundRectangle2D.Double(300, 600, 230, 50, 40, 40));
	      
	      
	     }
	 }


