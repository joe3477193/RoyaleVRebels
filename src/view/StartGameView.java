package view;

import controller.viewActionListeners.LoadGameBtnActionListener;
import controller.viewActionListeners.NewGameBtnActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class StartGameView {

    private static final int START_GAME_VIEW_WIDTH = 200;
    private static final int START_GAME_VIEW_HEIGHT = 200;
    private static final int INFO_PANEL_ROWS = 2;
    private static final int INFO_PANEL_COLS = 1;

    private JFrame frame;
    private JButton newGameBtn = new JButton("New Game");
    private JButton loadGameBtn = new JButton("Load Game");

    public StartGameView() {

        frame = new JFrame();
        frame.setSize(new Dimension(START_GAME_VIEW_WIDTH, START_GAME_VIEW_HEIGHT));
        frame.setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel(new GridLayout(INFO_PANEL_ROWS, INFO_PANEL_COLS));

        infoPanel.add(newGameBtn);
        infoPanel.add(loadGameBtn);

        frame.add(infoPanel, BorderLayout.CENTER);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        addController();
    }

    private void addController() {
        ActionListener newGame = new NewGameBtnActionListener(frame);
        newGameBtn.addActionListener(newGame);
        ActionListener loadGame = new LoadGameBtnActionListener(frame);
        loadGameBtn.addActionListener(loadGame);
    }
}
