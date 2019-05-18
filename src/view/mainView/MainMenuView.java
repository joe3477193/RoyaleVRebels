package view.mainView;

import controller.menuActionListeners.AboutBtnActionListener;
import controller.menuActionListeners.LoadGameBtnActionListener;
import controller.menuActionListeners.NewGameBtnActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class MainMenuView {

    private static final int START_GAME_VIEW_WIDTH = 200;
    private static final int START_GAME_VIEW_HEIGHT = 200;
    private static final int INFO_PANEL_ROWS = 5;
    private static final int INFO_PANEL_COLS = 1;

    private JFrame frame;
    private JButton newGameBtn = new JButton("New Game");
    private JButton loadGameBtn = new JButton("Load Game");
    private JButton helpBtn = new JButton("Help");
    private JButton aboutBtn = new JButton("About");
    private JButton quitBtn = new JButton("Quit");

    public MainMenuView() {

        frame = new JFrame();
        frame.setSize(new Dimension(START_GAME_VIEW_WIDTH, START_GAME_VIEW_HEIGHT));
        frame.setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel(new GridLayout(INFO_PANEL_ROWS, INFO_PANEL_COLS));

        infoPanel.add(newGameBtn);
        infoPanel.add(loadGameBtn);
        infoPanel.add(helpBtn);
        infoPanel.add(aboutBtn);
        infoPanel.add(quitBtn);

        frame.add(infoPanel, BorderLayout.CENTER);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        addController();
    }

    private void addController() {
        ActionListener newGameController = new NewGameBtnActionListener(frame);
        newGameBtn.addActionListener(newGameController);
        ActionListener loadGameController = new LoadGameBtnActionListener(frame);
        loadGameBtn.addActionListener(loadGameController);
        ActionListener aboutController = new AboutBtnActionListener(frame);
        aboutBtn.addActionListener(aboutController);
    }
}
