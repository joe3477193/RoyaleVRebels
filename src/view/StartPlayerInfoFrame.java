package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.StartGameActionListener;

public class StartPlayerInfoFrame {
    private JFrame frame;
    private JTextField player_one_name;
    private JTextField player_two_name;

    public StartPlayerInfoFrame() {
    	
Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame();
        
        frame.setResizable(true);
		frame.setSize(dimension.width/7,
				dimension.height/4);
        frame.setLocationRelativeTo(null);
        //frame.setSize(new Dimension(200, 200));

        JPanel infoPanel = new JPanel(new GridLayout(5, 1, 0, 0));

        JLabel nameLabelone = new JLabel("Please enter player one name:");
        player_one_name = new JTextField(40);
        player_one_name.setText("Player One");

        JLabel nameLabeltwo = new JLabel("Please enter player two name:");
        player_two_name = new JTextField(40);
        player_two_name.setText("Player Two");

        JButton start = new JButton("Begin Game");

        infoPanel.add(nameLabelone);
        infoPanel.add(player_one_name);
        infoPanel.add(nameLabeltwo);
        infoPanel.add(player_two_name);
        infoPanel.add(start);

        frame.add(infoPanel, BorderLayout.CENTER);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

       
        start.addActionListener(new StartGameActionListener(frame, player_one_name, player_two_name));
    }
}
