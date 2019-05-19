package view.subView;

import controller.menuActionListeners.ReturnBtnActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class HelpView {

    private static final int ABOUT_VIEW_WIDTH = 300;
    private static final int ABOUT_VIEW_HEIGHT = 300;
    private static final int INFO_PANEL_ROWS = 6;
    private static final int INFO_PANEL_COLS = 1;

    private JFrame frame;

    private JButton returnBtn = new JButton("Return");

    public HelpView() {
        frame = new JFrame("How to play?");
        frame.setSize(new Dimension(ABOUT_VIEW_WIDTH, ABOUT_VIEW_HEIGHT));
        frame.setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel(new GridLayout(INFO_PANEL_ROWS, INFO_PANEL_COLS));

        // TODO: put key game rules here later
        JLabel title = new JLabel("Game Rules", SwingConstants.CENTER);
        JLabel author1 = new JLabel("Rule 1: xxxxxxxxxxxxxxxxx");
        JLabel author2 = new JLabel("Rule 2: xxxxxxxxxxxxxxxxx");
        JLabel author3 = new JLabel("Rule 3: xxxxxxxxxxxxxxxxx");
        JLabel author4 = new JLabel("Rule 4: xxxxxxxxxxxxxxxxx");

        infoPanel.add(title);
        infoPanel.add(author1);
        infoPanel.add(author2);
        infoPanel.add(author3);
        infoPanel.add(author4);
        infoPanel.add(returnBtn);

        frame.add(infoPanel, BorderLayout.CENTER);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        addController();
    }

    private void addController() {
        ActionListener returnController = new ReturnBtnActionListener(frame);
        returnBtn.addActionListener(returnController);
    }
}
