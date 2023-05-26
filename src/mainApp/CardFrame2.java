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
 
    
    
    public CardFrame2(int id) throws HeadlessException  {
		super();
		
		// ustawienia
		
		this.id = id;
		setBounds(0, 0, 900, 800);

		setResizable(false);
        setTitle("Tinder");
        
        
        // dodawanie paneli
        
        JPanel contentPane = new JPanel();
		setContentPane(contentPane);
        
        contentPane.setLayout(new CardLayout());
        
        panel6 = new ChatPanel(contentPane, id);
        panel4 = new SwipePanel(contentPane,  id);
        panel5 = new SettingsPanel(contentPane,this, id, panel4);
        contentPane.add(panel4, "Panel 4"); 
        contentPane.add(panel5, "Panel 5");
        contentPane.add(panel6, "Panel 6");
        setContentPane(contentPane); 
        setLocationRelativeTo(null);
        setVisible(true);
        
        // powiadomienie przy wyłączaniu
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e); 
                if(JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz wyjść z aplikacji?", "Potwierdzenie", JOptionPane.YES_NO_OPTION)!=0){
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                } else {
                setDefaultCloseOperation(EXIT_ON_CLOSE);
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
	            new CardFrame2(id);
	        }
	    });
		}
}


