package view.subView;

import controller.menuActionListeners.ReturnBtnActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class AboutView {

    private static final int ABOUT_VIEW_WIDTH = 200;
    private static final int ABOUT_VIEW_HEIGHT = 200;
    private static final int INFO_PANEL_ROWS = 5;
    private static final int INFO_PANEL_COLS = 1;

    private JFrame frame;

    private JButton returnBtn = new JButton("Return");

    public AboutView() {
        frame = new JFrame();
        frame.setSize(new Dimension(ABOUT_VIEW_WIDTH, ABOUT_VIEW_HEIGHT));
        frame.setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel(new GridLayout(INFO_PANEL_ROWS, INFO_PANEL_COLS));

        JLabel title = new JLabel("ABOUT");
        JLabel author1 = new JLabel("Author: Huirong Huang");
        JLabel author2 = new JLabel("Author: Edward Kahiro Kuo");
        JLabel author3 = new JLabel("Author: Joseph Anthony Verduci");
        JLabel author4 = new JLabel("Author: Shamik Zahrawi Rahman");

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
