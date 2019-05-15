package view.subView;

import controller.menuActionListeners.StartGameBtnActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class EnterNameView {

    private static final int ENTER_NAME_VIEW_WIDTH = 200;
    private static final int ENTER_NAME_VIEW_HEIGHT = 200;
    private static final int INFO_PANEL_ROWS = 5;
    private static final int INFO_PANEL_COLS = 1;
    private static final int NAME_MAX_LENGTH = 40;


    private JFrame frame;
    private JTextField player_one_name;
    private JTextField player_two_name;
    private JButton startBtn = new JButton("Begin Game");

    public EnterNameView() {
        frame = new JFrame();
        frame.setSize(new Dimension(ENTER_NAME_VIEW_WIDTH, ENTER_NAME_VIEW_HEIGHT));
        frame.setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel(new GridLayout(INFO_PANEL_ROWS, INFO_PANEL_COLS));

        JLabel nameLabelOne = new JLabel("Please enter player one name:");
        player_one_name = new JTextField(NAME_MAX_LENGTH);
        player_one_name.setText("Player One");

        JLabel nameLabelTwo = new JLabel("Please enter player two name:");
        player_two_name = new JTextField(NAME_MAX_LENGTH);
        player_two_name.setText("Player Two");

        infoPanel.add(nameLabelOne);
        infoPanel.add(player_one_name);
        infoPanel.add(nameLabelTwo);
        infoPanel.add(player_two_name);
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
        ActionListener startController = new StartGameBtnActionListener(frame, player_one_name, player_two_name);
        startBtn.addActionListener(startController);
    }
}
