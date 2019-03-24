package xvy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartPlayerInfoView {
	private JFrame frame;
	public JTextField player_one_name;
	public JTextField player_two_name;
	Game g;
	
	StartPlayerInfoView(Game g){
		this.g = g;
		frame = new JFrame();
		frame.setSize(new Dimension(200,200));
		frame.setLocationRelativeTo(null);
		
        JPanel infoPanel = new JPanel(new GridLayout(5,1,0,0));

        JLabel nameLabelone = new JLabel("Please enter player one name:");
        player_one_name = new JTextField(40);
        
        JLabel nameLabeltwo = new JLabel("Please enter player two name:");
        player_two_name = new JTextField(40);
        
        JButton start = new JButton("Begin Game");
        start.addActionListener(new StartPlayerInfo(this));
        
        infoPanel.add(nameLabelone);
        infoPanel.add(player_one_name);
        infoPanel.add(nameLabeltwo);
        infoPanel.add(player_two_name);
        infoPanel.add(start);
   
        frame.add(infoPanel, BorderLayout.CENTER);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);      
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public void initPlayers(List<String> players) {
		
		for(String player : players) {
			g.addPlayer(player);
		}
        g.showBoard();
        this.frame.dispose();
	}
	
    public void warnNameNotSet() {
        JOptionPane.showMessageDialog(frame,
                "Please ensure both names are entered",
                "Input error",
                JOptionPane.ERROR_MESSAGE);
    }
}
