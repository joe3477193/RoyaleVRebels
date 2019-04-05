package xvy;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartPlayerInfo implements ActionListener {

	private StartPlayerInfoView spiv;
	private List<String> strings;
	
    public StartPlayerInfo(StartPlayerInfoView spiv) {
        this.spiv = spiv;
        strings = new ArrayList<String>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton btn = (JButton) e.getSource();
            String pressed = btn.getText();

            if(!spiv.player_one_name.getText().equals("") && !spiv.player_two_name.getText().equals("")) {
            	strings.add(spiv.player_one_name.getText());
            	strings.add(spiv.player_two_name.getText());
            	for(String s : strings) {
            		spiv.initPlayers(s);
            	}
            	
            	
            } else {
                spiv.warnNameNotSet();
            }
        }
    }
	
	
	

}
