package view;

import controller.LoadGameActionListener;
import controller.NewGameActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class StartGameView {
    private JFrame frame;
    private JButton newGameButton;
    private JButton loadGameButton;

    public StartGameView() {
        newGameButton = new JButton("New Game");
        loadGameButton = new JButton("Load Game");
        frame = new JFrame();
        frame.setSize(new Dimension(200, 200));
        frame.setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 0));

        infoPanel.add(newGameButton);
        infoPanel.add(loadGameButton);

        frame.add(infoPanel, BorderLayout.CENTER);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        addController();
    }

    private void addController() {
        ActionListener newGame = new NewGameActionListener(frame);
        newGameButton.addActionListener(newGame);
        ActionListener loadGame = new LoadGameActionListener(frame);
        loadGameButton.addActionListener(loadGame);
    }
}
