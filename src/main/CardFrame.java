package main;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import main.DataPanel;
import main.LoginPanel;
import main.RegisterPanel;


public class CardFrame {

	private JPanel contentPane;
    private LoginPanel panel1;
    private RegisterPanel panel2;
    private DataPanel panel3;

    private void displayGUI()
    {
        JFrame frame = new JFrame("Login & Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 600, 400);
		frame.setUndecorated(true);

        JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 105, 180));
		contentPane.setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		frame.setContentPane(contentPane);
		//frame.setLocationRelativeTo(null);
        
        contentPane.setLayout(new CardLayout());
        panel1 = new LoginPanel(contentPane);
        panel2 = new RegisterPanel(contentPane);
        panel3 = new DataPanel(contentPane);
        contentPane.add(panel1, "Panel 1"); 
        contentPane.add(panel2, "Panel 2");
        contentPane.add(panel3, "Panel 3");
        frame.setContentPane(contentPane); //to nie do konca rozumiem co robi  
        frame.setLocationRelativeTo(null);
        //frame.setLocationByPlatform(true); //nie otwiera sie w rogu
        frame.setVisible(true);

    }


public static void main(String[] args)
{
    SwingUtilities.invokeLater(new Runnable()
    {
        public void run()
        {
            new CardFrame().displayGUI();
        }
    });
}
}
