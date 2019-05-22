package view.subView;

import controller.menuActionListeners.ReturnBtnActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class HelpView {

    private static final int ABOUT_VIEW_WIDTH = 1050;
    private static final int ABOUT_VIEW_HEIGHT = 500;
    private static final int INFO_PANEL_ROWS = 8;
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
        JLabel author1 = new JLabel("Rule 1: Before the start, players can enter names, create different sized board, and specify the undo level (up to 3 rounds).");
        JLabel author2 = new JLabel("Rule 2: Players are randomly assigned to Royale and Rebel, Rebel moves first.");
        JLabel author3 = new JLabel("Rule 3: Rebel needs to destroy Royale's CASTLE within a limit to win the game, while Royale need to defence.");
        JLabel author4 = new JLabel("Rule 4: In each move, you can either summon a new piece, move an on-board moveable piece or attack an opponent piece.");
        JLabel author5 = new JLabel("Rule 5: Each move has a time limit of 60 seconds, before performing a move you can select any on-board pieces of your side to be OFFENSIVE or DEFENSIVE, excepts Obstacles.");
        JLabel author6 = new JLabel("Rule 6: Player can save and quit game at any time, and will be able to continue the game after loading the game.");


        infoPanel.add(title);
        infoPanel.add(author1);
        infoPanel.add(author2);
        infoPanel.add(author3);
        infoPanel.add(author4);
        infoPanel.add(author5);
        infoPanel.add(author6);
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
