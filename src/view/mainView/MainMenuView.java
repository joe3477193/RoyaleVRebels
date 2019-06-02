package view.mainView;

import controller.menuActionListeners.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class MainMenuView {

    private static final int MAIN_MENU_VIEW_WIDTH = 300;
    private static final int MAIN_MENU_VIEW_HEIGHT = 300;
    private static final int INFO_PANEL_ROWS = 6;
    private static final int INFO_PANEL_COLS = 1;
    private static final String FRAME_TITLE = "Welcome!";
    private static final String GAME_NAME = "ROYALE  VS  REBELS";
    private static final String NEW_GAME = "New Game";
    private static final String LOAD_GAME = "Load Game";
    private static final String HELP = "Help";
    private static final String ABOUT = "About";
    private static final String QUIT_GAME = "Quit Game";

    private JFrame frame;
    private JButton newGameBtn = new JButton(NEW_GAME);
    private JButton loadGameBtn = new JButton(LOAD_GAME);
    private JButton helpBtn = new JButton(HELP);
    private JButton aboutBtn = new JButton(ABOUT);
    private JButton quitBtn = new JButton(QUIT_GAME);

    public MainMenuView() {
        frame = new JFrame(FRAME_TITLE);
        frame.setSize(new Dimension(MAIN_MENU_VIEW_WIDTH, MAIN_MENU_VIEW_HEIGHT));
        frame.setLocationRelativeTo(null);
        JLabel title = new JLabel(GAME_NAME, SwingConstants.CENTER);
        JPanel infoPanel = new JPanel(new GridLayout(INFO_PANEL_ROWS, INFO_PANEL_COLS));
        infoPanel.add(title);
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
        ActionListener helpController = new HelpBtnActionListener(frame);
        helpBtn.addActionListener(helpController);
        ActionListener aboutController = new AboutBtnActionListener(frame);
        aboutBtn.addActionListener(aboutController);
        ActionListener quitController = new ExitBtnActionListener(frame);
        quitBtn.addActionListener(quitController);
    }
}
