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


    private JFrame frame;
    private JTextField player_one_name;
    private JTextField player_two_name;
    private JTextField board_rows;
    private JTextField board_cols;
    private JButton startBtn = new JButton("Begin Game");
    private JButton returnBtn = new JButton("Return");
    private String[] undoOptions = new String[] {"0","1","2","3"};
    JComboBox<String> undoMoves;

    public EnterNameView() {
        frame = new JFrame("Register...");
        frame.setSize(new Dimension(ENTER_NAME_VIEW_WIDTH, ENTER_NAME_VIEW_HEIGHT));
        frame.setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel(new GridLayout(INFO_PANEL_ROWS, INFO_PANEL_COLS));

        JLabel nameLabelOne = new JLabel("Please enter player one name:");
        player_one_name = new JTextField(NAME_MAX_LENGTH);
        player_one_name.setText("Player One");
        
        undoMoves = new JComboBox<>(undoOptions);

        JLabel nameLabelTwo = new JLabel("Please enter player two name:");
        player_two_name = new JTextField(NAME_MAX_LENGTH);
        player_two_name.setText("Player Two");

        JLabel rowsLabel = new JLabel("Please enter number of board rows:");
        board_rows = new JTextField(DIMENSIONS_MAX_LENGTH);
        board_rows.setText("13");

        JLabel columnLabel = new JLabel("Please enter number of board columns:");
        board_cols = new JTextField(DIMENSIONS_MAX_LENGTH);
        board_cols.setText("15");

        infoPanel.add(nameLabelOne);
        infoPanel.add(player_one_name);
        infoPanel.add(nameLabelTwo);
        infoPanel.add(player_two_name);
        infoPanel.add(rowsLabel);
        infoPanel.add(board_rows);
        infoPanel.add(columnLabel);
        infoPanel.add(board_cols);
        infoPanel.add(new JLabel("Undo's allowed:"));
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
