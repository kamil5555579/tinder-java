package main;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PPasswordField extends JPasswordField {

    public PPasswordField(final String proptText) {
        super(proptText);
        char passwordChar = getEchoChar();
		setEchoChar ((char) 0);
        addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                if(String.valueOf(getPassword()).isEmpty()) {
                	setEchoChar ((char) 0);
                    setText(proptText);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if(String.valueOf(getPassword()).equals(proptText)) {
                    setText("");
                    setEchoChar(passwordChar);
                }
            }
        });

    }

}