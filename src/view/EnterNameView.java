package view;

import controller.StartBtnActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class EnterNameView {
    private JFrame frame;
    private JTextField player_one_name;
    private JTextField player_two_name;
    private JTextField rows;
    private JTextField cols;
    private JButton startBtn = new JButton("Begin Game");

    public EnterNameView() {
        frame = new JFrame();
        //frame.setSize(new Dimension(200, 200));
        frame.setSize(new Dimension(300, 400));
        frame.setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel(new GridLayout(5, 1, 0, 0));

        JLabel nameLabelOne = new JLabel("Please enter player one name:");
        player_one_name = new JTextField(40);
        player_one_name.setText("Player One");

        JLabel nameLabelTwo = new JLabel("Please enter player two name:");
        player_two_name = new JTextField(40);
        player_two_name.setText("Player Two");

        JLabel browsLabel = new JLabel("Please enter player two name:");
        rows = new JTextField(40);
        rows.setText("13");

        JLabel bcolsLabel = new JLabel("Please enter player two name:");
        cols = new JTextField(40);
        cols.setText("15");

        infoPanel.add(nameLabelOne);
        infoPanel.add(player_one_name);
        infoPanel.add(nameLabelTwo);
        infoPanel.add(player_two_name);
        infoPanel.add(browsLabel);
        infoPanel.add(rows);
        infoPanel.add(bcolsLabel);
        infoPanel.add(cols);
        infoPanel.add(startBtn);

        frame.add(infoPanel, BorderLayout.CENTER);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        addController();
    }

    private void addController() {
        ActionListener startController = new StartBtnActionListener(frame, player_one_name, player_two_name, rows, cols);
        startBtn.addActionListener(startController);
    }
}
