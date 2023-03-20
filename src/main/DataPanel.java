package main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

class DataPanel extends JPanel 
{

	private JPanel panel_1;
	private JButton btnRegister;
	private JComboBox comboBox;
	private JTextField txtAge;
	private JTextField txtUploadPhoto;
	private JTextField txtDescription;
	private JTextField txtName;
	private JTextField txtSurname;
	

	public DataPanel(JPanel panel)  {
		
		setBounds(100, 100, 600, 400);
		setBackground(new Color(255, 105, 180));
		setBorder(new LineBorder(new Color(255, 20, 147), 3, true));
		setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(125, 93, 351, 174);
		add(panel_1);
		panel_1.setLayout(null);
		
		txtDescription = new JTextField();
		txtDescription.setCaretColor(new Color(0, 0, 0));
		txtDescription.setBounds(12, 126, 110, 15);
		panel_1.add(txtDescription);
		txtDescription.setFont(new Font("Dialog", Font.ITALIC, 12));
		txtDescription.setText("Description...");
		txtDescription.setBorder(null);
		txtDescription.setColumns(10);
		
		String[] gender = {"Gender", "Male", "Famale",
  	    "Other" };
		comboBox = new JComboBox(gender);
		comboBox.setBounds(12, 55, 159, 24);
		panel_1.add(comboBox);
		comboBox.setBackground(new Color(255, 240, 245));
		comboBox.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		
		txtUploadPhoto = new JTextField();
		txtUploadPhoto.setBounds(183, 88, 156, 26);
		panel_1.add(txtUploadPhoto);
		txtUploadPhoto.setText("Upload photo");
		txtUploadPhoto.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		txtUploadPhoto.setColumns(10);
		txtUploadPhoto.setBackground(new Color(255, 240, 245));
		String[] faculty = {"Faculty", "Architecture", "Chemistry", "Mathematics and Infromation Science", "Physics", 
  	    "Other" };
		JComboBox comboBox_2 = new JComboBox(faculty);
		comboBox_2.setBounds(12, 91, 159, 24);
		panel_1.add(comboBox_2);
		comboBox_2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		comboBox_2.setBackground(new Color(255, 240, 245));
		
		txtAge = new JTextField();
		txtAge.setBounds(183, 55, 156, 26);
		panel_1.add(txtAge);
		txtAge.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		txtAge.setText("Age");
		txtAge.setBackground(new Color(255, 240, 245));
		txtAge.setColumns(10);
		
		txtName = new JTextField();
		txtName.setText("Name");
		txtName.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		txtName.setColumns(10);
		txtName.setBackground(new Color(255, 240, 245));
		txtName.setBounds(11, 17, 159, 26);
		panel_1.add(txtName);
		
		txtSurname = new JTextField();
		txtSurname.setText("Surname");
		txtSurname.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		txtSurname.setColumns(10);
		txtSurname.setBackground(new Color(255, 240, 245));
		txtSurname.setBounds(182, 17, 157, 26);
		panel_1.add(txtSurname);
		
		
		JLabel lblTinder = new JLabel("Fill your data");
		lblTinder.setForeground(Color.WHITE);
		lblTinder.setFont(new Font("LM Sans 10", Font.BOLD | Font.ITALIC, 30));
		lblTinder.setBounds(214, 25, 185, 43);
		add(lblTinder);
		
		JButton button = new JButton("Log in");
		button.setBackground(new Color(255, 240, 245));
		button.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		button.setBounds(124, 279, 170, 50);
		add(button);
		
		btnRegister = new JButton("Save");
		btnRegister.setAlignmentY(0.0f);
		btnRegister.setBackground(new Color(255, 240, 245));
		btnRegister.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		btnRegister.setBounds(306, 279, 170, 50);
		add(btnRegister);
		
		btnRegister.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                CardLayout cardLayout = (CardLayout) panel.getLayout();
                cardLayout.next(panel);
            }
        });
        add(btnRegister);
		
		JLabel lblX = new JLabel("X");
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to close this application?", "Confirmation", JOptionPane.YES_NO_OPTION)==0){
					System.exit(ABORT);
				}
			}
		});
		lblX.setForeground(new Color(255, 255, 255));
		lblX.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		lblX.setBounds(578, 3, 11, 17);
		add(lblX);
	
	}
}
