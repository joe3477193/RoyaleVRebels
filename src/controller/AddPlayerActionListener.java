package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.*;
import view.*;

public class AddPlayerActionListener implements ActionListener {

    private JFrame frame;
    private JTextField name1, name2;

    public AddPlayerActionListener(JFrame frame, JTextField name1, JTextField name2) {
        this.frame = frame;
        this.name1 = name1;
        this.name2 = name2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (name1.getText().length() > 0 && name2.getText().length() > 0) {
        	
        	ArrayList<String> players = new ArrayList<String>();
        	players.add(name1.getText());
        	players.add(name2.getText());
        	System.out.println(name1.getText() +" "+name2.getText());
        	
        	GameFrameView gameFrame = new GameFrameView();
    		SwingUtilities.invokeLater(new Runnable(){			
    			public void run() {			
    				new Game(gameFrame, players);
    							}
    		});
	       
            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "Please make sure both names are entered.", "Input error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
}
