package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import main.DataPanel;
import main.LoginPanel;
import main.RegisterPanel;


public class CardFrame extends JFrame {

	private JPanel contentPane;
    private LoginPanel panel1;
    private RegisterPanel panel2;
    private DataPanel panel3;
       



public CardFrame() throws HeadlessException {
		super();

		setTitle("Login & Register");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
		setBounds(100, 100, 700, 600);
		setResizable(false);

		//frame.setUndecorated(true);

        JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 105, 180));
		contentPane.setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		setContentPane(contentPane);
		//frame.setLocationRelativeTo(null);
        
        contentPane.setLayout(new CardLayout());
        panel1 = new LoginPanel(contentPane, this);
        panel3 = new DataPanel(contentPane, this);
        panel2 = new RegisterPanel(contentPane, panel3);
        contentPane.add(panel1, "Panel 1"); ;
        contentPane.add(panel2, "Panel 2");
        contentPane.add(panel3, "Panel 3");
        setContentPane(contentPane); //to nie do konca rozumiem co robi  
        setLocationRelativeTo(null);
        //frame.setLocationByPlatform(true); //nie otwiera sie w rogu
        setVisible(true);
        
        this.addWindowListener(new WindowAdapter() {
            

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e); 
                if(JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz wyjść z aplikacji?", "Potwierdzenie", JOptionPane.YES_NO_OPTION)==0){
                	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
            }
        });
    }  
	


public static void main(String[] args)
{
    SwingUtilities.invokeLater(new Runnable()
    {
        public void run()
        {
            new CardFrame();
        }
    });
}



}
