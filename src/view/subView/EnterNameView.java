package view.subView;

import controller.menuActionListeners.ReturnBtnActionListener;
import controller.menuActionListeners.StartGameBtnActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class EnterNameView {

    private static final int ENTER_NAME_VIEW_WIDTH = 300;
    private static final int ENTER_NAME_VIEW_HEIGHT = 300;
    private static final int INFO_PANEL_ROWS = 6;
    private static final int INFO_PANEL_COLS = 1;
    private static final int NAME_MAX_LENGTH = 40;
    private static final int DIMENSIONS_MAX_LENGTH = 2;

    private static final String FRAME_TITLE = "Register...";
    private static final String START_GAME = "Start Game";
    private static final String RETURN = "Return to Menu";
    private static final String ENTER_PLAYER_ONE_NAME = "Player one name: ";
    private static final String ENTER_PLAYER_TWO_NAME = "Player two name: ";
    private static final String DEFAULT_PLAYER_ONE_NAME = "Player One";
    private static final String DEFAULT_PLAYER_TWO_NAME = "Player Two";
    private static final String ENTER_BOARD_ROWS = "Board rows: ";
    private static final String ENTER_BOARD_COLS = "Board columns: ";
    private static final String DEFAULT_BOARD_ROWS = "13";
    private static final String DEFAULT_BOARD_COLS = "15";
    private static final String UNDO_LEVEL_CONFIG = "Undo is allowed for: ";

    private JComboBox<String> undoMoves;
    private JFrame frame;
    private JTextField player_one_name;
    private JTextField player_two_name;
    private JTextField board_rows;
    private JTextField board_cols;
    private JButton startBtn = new JButton(START_GAME);
    private JButton returnBtn = new JButton(RETURN);
    private String[] undoLevels = new String[]{"0", "1", "2", "3"};

    public EnterNameView() {
        frame = new JFrame(FRAME_TITLE);
        frame.setSize(new Dimension(ENTER_NAME_VIEW_WIDTH, ENTER_NAME_VIEW_HEIGHT));
        frame.setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel(new GridLayout(INFO_PANEL_ROWS, INFO_PANEL_COLS));

        JLabel playerOneNameLabel = new JLabel(ENTER_PLAYER_ONE_NAME);
        player_one_name = new JTextField(NAME_MAX_LENGTH);
        player_one_name.setText(DEFAULT_PLAYER_ONE_NAME);

        JLabel playerTwoNameLabel = new JLabel(ENTER_PLAYER_TWO_NAME);
        player_two_name = new JTextField(NAME_MAX_LENGTH);
        player_two_name.setText(DEFAULT_PLAYER_TWO_NAME);

        JLabel rowsLabel = new JLabel(ENTER_BOARD_ROWS);
        board_rows = new JTextField(DIMENSIONS_MAX_LENGTH);
        board_rows.setText(DEFAULT_BOARD_ROWS);

        JLabel colsLabel = new JLabel(ENTER_BOARD_COLS);
        board_cols = new JTextField(DIMENSIONS_MAX_LENGTH);
        board_cols.setText(DEFAULT_BOARD_COLS);

        JLabel undoLabel = new JLabel(UNDO_LEVEL_CONFIG);
        undoMoves = new JComboBox<>(undoLevels);

        infoPanel.add(playerOneNameLabel);
        infoPanel.add(player_one_name);
        infoPanel.add(playerTwoNameLabel);
        infoPanel.add(player_two_name);
        infoPanel.add(rowsLabel);
        infoPanel.add(board_rows);
        infoPanel.add(colsLabel);
        infoPanel.add(board_cols);
        infoPanel.add(undoLabel);
        infoPanel.add(undoMoves);
        infoPanel.add(startBtn);
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
        ActionListener startController = new StartGameBtnActionListener(frame, player_one_name, player_two_name, undoMoves, board_rows, board_cols);
        startBtn.addActionListener(startController);
        ActionListener returnController = new ReturnBtnActionListener(frame);
        returnBtn.addActionListener(returnController);
    }
}
