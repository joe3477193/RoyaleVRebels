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

    private static final String FRAME_TITLE = "How to play???";
    private static final String RETURN = "Return to Menu";
    private static final String GAME_RULES = "Game Rules";
    private static final String GAME_RULE_ONE = "Rule 1: Before the start, players can enter names, create different sized board, and specify the undo level (up to 3 rounds).";
    private static final String GAME_RULE_TWO = "Rule 2: Players are randomly assigned to Royale and Rebel, Rebel moves first.";
    private static final String GAME_RULE_THREE = "Rule 3: Rebel needs to destroy Royale's CASTLE within a limit to win the game, while Royale need to defence.";
    private static final String GAME_RULE_FOUR = "Rule 4: In each move, you can either summon a new piece, move an on-board moveable piece or attack an opponent piece.";
    private static final String GAME_RULE_FIVE = "Rule 5: Each move has a time limit of 60 seconds, before performing a move you can select any on-board pieces of your side to be OFFENSIVE or DEFENSIVE, excepts Obstacles.";
    private static final String GAME_RULE_SIX = "Rule 6: Player can save and quit game at any time, and will be able to continue the game after loading the game.";

    private JFrame frame;

    private JButton returnBtn = new JButton(RETURN);

    public HelpView() {
        frame = new JFrame(FRAME_TITLE);
        frame.setSize(new Dimension(ABOUT_VIEW_WIDTH, ABOUT_VIEW_HEIGHT));
        frame.setLocationRelativeTo(null);
        JPanel infoPanel = new JPanel(new GridLayout(INFO_PANEL_ROWS, INFO_PANEL_COLS));
        JLabel title = new JLabel(GAME_RULES, SwingConstants.CENTER);
        JLabel gameRule1 = new JLabel(GAME_RULE_ONE);
        JLabel gameRule2 = new JLabel(GAME_RULE_TWO);
        JLabel gameRule3 = new JLabel(GAME_RULE_THREE);
        JLabel gameRule4 = new JLabel(GAME_RULE_FOUR);
        JLabel gameRule5 = new JLabel(GAME_RULE_FIVE);
        JLabel gameRule6 = new JLabel(GAME_RULE_SIX);
        infoPanel.add(title);
        infoPanel.add(gameRule1);
        infoPanel.add(gameRule2);
        infoPanel.add(gameRule3);
        infoPanel.add(gameRule4);
        infoPanel.add(gameRule5);
        infoPanel.add(gameRule6);
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
