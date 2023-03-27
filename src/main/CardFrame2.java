package main;

import java.awt.CardLayout;
import java.awt.Color;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class CardFrame2 {

	private JPanel contentPane;
    private SwipePanel panel4;
    private SettingsPanel panel5;
    private ChatPanel panel6;

    
    private void displayGUI()
    {
        JFrame frame = new JFrame("Tinder");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e); 
                if(JOptionPane.showConfirmDialog(null, "Are you sure you want to close this application?", "Confirmation", JOptionPane.YES_NO_OPTION)==0){
					frame.dispose();
				}
            }
        });
        
       
		frame.setBounds(100, 100, 1000, 1000);
		frame.setResizable(false);

		//frame.setUndecorated(true);

        JPanel contentPane = new JPanel();
		//contentPane.setBackground(new Color(200, 105, 180));
		contentPane.setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		frame.setContentPane(contentPane);
		//frame.setLocationRelativeTo(null);
        
        contentPane.setLayout(new CardLayout());
        panel4 = new SwipePanel(contentPane, frame);
        panel6 = new ChatPanel(contentPane);
        panel5 = new SettingsPanel(contentPane);
        contentPane.add(panel4, "Panel 4"); 
        contentPane.add(panel5, "Panel 5");
        contentPane.add(panel6, "Panel 6");
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
            new CardFrame2().displayGUI();
        }
    });
}


}
