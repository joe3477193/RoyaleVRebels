package view.subView;

import controller.menuActionListeners.ReturnBtnActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class AboutView {

    private static final int ABOUT_VIEW_WIDTH = 300;
    private static final int ABOUT_VIEW_HEIGHT = 300;
    private static final int INFO_PANEL_ROWS = 6;
    private static final int INFO_PANEL_COLS = 1;

    private static final String FRAME_TITLE = "OOSD 2019, RMIT";
    private static final String RETURN = "Return to Menu";
    private static final String ABOUT = "About";
    private static final String AUTHOR_NAME_ONE = "Author: Huirong Huang";
    private static final String AUTHOR_NAME_TWO = "Author: Edward Kahiro Kuo";
    private static final String AUTHOR_NAME_THREE = "Author: Joseph Anthony Verduci";
    private static final String AUTHOR_NAME_FOUR = "Author: Shamik Zahrawi Rahman";
    private JFrame frame;

    private JButton returnBtn = new JButton(RETURN);

    public AboutView() {
        frame = new JFrame(FRAME_TITLE);
        frame.setSize(new Dimension(ABOUT_VIEW_WIDTH, ABOUT_VIEW_HEIGHT));
        frame.setLocationRelativeTo(null);
        JPanel infoPanel = new JPanel(new GridLayout(INFO_PANEL_ROWS, INFO_PANEL_COLS));
        JLabel title = new JLabel(ABOUT, SwingConstants.CENTER);
        JLabel authorName1 = new JLabel(AUTHOR_NAME_ONE);
        JLabel authorName2 = new JLabel(AUTHOR_NAME_TWO);
        JLabel authorName3 = new JLabel(AUTHOR_NAME_THREE);
        JLabel authorName4 = new JLabel(AUTHOR_NAME_FOUR);
        infoPanel.add(title);
        infoPanel.add(authorName1);
        infoPanel.add(authorName2);
        infoPanel.add(authorName3);
        infoPanel.add(authorName4);
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
