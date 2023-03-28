package mainApp;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class CardFrame2 extends JFrame{

	private JPanel contentPane;
    private SwipePanel panel4;
    private SettingsPanel panel5;
    private ChatPanel panel6;
    private static int id;
    
    
    public CardFrame2(int id) throws HeadlessException {
		super();
		this.id = id;
		this.setBackground(Color.white);
		setBounds(100, 100, 1000, 1000);
		setResizable(false);


        setTitle("Tinder");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e); 
                if(JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz wyjść z aplikacji?", "Potwierdzenie", JOptionPane.YES_NO_OPTION)==0){
                	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
            }
        });

		//frame.setUndecorated(true);

        JPanel contentPane = new JPanel();
		//contentPane.setBackground(new Color(200, 105, 180));
		//contentPane.setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		setContentPane(contentPane);
		//frame.setLocationRelativeTo(null);
        
        contentPane.setLayout(new CardLayout());
        panel4 = new SwipePanel(contentPane, this, id);
        panel6 = new ChatPanel(contentPane, id);
        panel5 = new SettingsPanel(contentPane,id);
        contentPane.add(panel4, "Panel 4"); 
        contentPane.add(panel5, "Panel 5");
        contentPane.add(panel6, "Panel 6");
        setContentPane(contentPane); //to nie do konca rozumiem co robi  
        setLocationRelativeTo(null);
        //frame.setLocationByPlatform(true); //nie otwiera sie w rogu
        setVisible(true);
	}


	

public static void main(String[] args)
{
    SwingUtilities.invokeLater(new Runnable()
    {
        public void run()
        {
            new CardFrame2(id);
        }
    });
	}
}


