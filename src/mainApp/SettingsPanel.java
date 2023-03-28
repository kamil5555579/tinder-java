package mainApp;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class SettingsPanel extends JPanel {
	
	private JButton buttonSwipe;
	
	public SettingsPanel(JPanel panel, int id) {
		//ustawienia panelu
		setBounds(100, 100, 1000, 1000);
		setBackground(new Color(200, 105, 180));
		setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		setLayout(null); 
		
		setBounds(100, 100, 800, 1000);
		setBackground(new Color(200, 105, 180));
		setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		setLayout(null);

		buttonSwipe = new JButton("Return");
		buttonSwipe.setBackground(new Color(255, 240, 245));
		buttonSwipe.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		buttonSwipe.setBounds(277, 865, 225, 65);
		buttonSwipe.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.previous(panel);
            }
        });
		
        add(buttonSwipe);
	}

}
