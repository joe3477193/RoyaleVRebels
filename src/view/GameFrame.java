package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.*;

public class GameFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private JPanel gridPanel;
    private JPanel selectPanel;
    private JLabel healthLabel;
    private JLabel combatPlabel;
    private JLabel playername;
    private static JButton[][] btn;
    private JButton item1, item2, item3, item4, item5, item6;

    protected static final String GRASS_IMAGE = "../images/grass.png";
    protected static final String WALL_IMAGE = "../images/wall.jpg";

    public GameFrame() {

        frame = new JFrame("Royals vs Rebels");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // show gui in the middle of screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void assembleBoard(Player playerOne) {

        JPanel statsPanel = new JPanel(new GridLayout(1, 6, 0, 0));
        selectPanel = new JPanel(new GridLayout(1, 5, 0, 0));
        gridPanel = new JPanel(new GridLayout(Board.BOARD_ROWS, Board.BOARD_COLS, 0, 1));

        healthLabel = new JLabel(Integer.toString(playerOne.getStartHP()));
        combatPlabel = new JLabel(Integer.toString(playerOne.getStartCP()));
        playername = new JLabel(playerOne.getName());

        statsPanel.add(new JLabel("Player Name: "));

        statsPanel.add(playername);
        statsPanel.add(new JLabel("HP: "));
        statsPanel.add(healthLabel);
        statsPanel.add(new JLabel("CP: "));
        statsPanel.add(combatPlabel);

        btn = new JButton[Board.BOARD_ROWS][Board.BOARD_COLS];

        selectPanel.setMaximumSize(new Dimension(100, 100));

        frame.setSize(925, 600);
        paintGridforRoyal();

        JPanel main = new JPanel(new BorderLayout());
        main.add(statsPanel, BorderLayout.NORTH);
        main.add(selectPanel, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();

        frame.add(main, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
    }

    private void paintGridforRoyal() {
        item1 = new JButton();
        item1.setName("spawn_General");
        // item1.addActionListener(new AddPlayerActionListener(this));
        selectPanel.add(item1);

        item2 = new JButton();
        item2.setName("spawn_Lieutenant");
        // item2.addActionListener(new AddPlayerActionListener(this));
        selectPanel.add(item2);

        item3 = new JButton();
        item3.setName("spawn_Spearman");
        // item3.addActionListener(new AddPlayerActionListener(this));
        selectPanel.add(item3);

        item4 = new JButton();
        item4.setName("spawn_Footman");
        // item4.addActionListener(new AddPlayerActionListener(this));
        selectPanel.add(item4);

        item5 = new JButton();
        item5.setName("spawn_Archer");
        // item5.addActionListener(new AddPlayerActionListener(this));
        selectPanel.add(item5);

        item6 = new JButton();
        item6.setName("spawn_Cannon");
        // item6.addActionListener(new AddPlayerActionListener(this));
        selectPanel.add(item6);

        genGrid();

    }

    public void genGrid() {
        for (int i = 0; i < Board.BOARD_ROWS; i++) {
            for (int j = 0; j < Board.BOARD_COLS; j++) {
                btn[i][j] = new JButton();

                if ((i % 5 <= 2) && j % 4 == 3)
                    btn[i][j].setIcon(new ImageIcon(this.getClass().getResource(WALL_IMAGE)));
                else
                    btn[i][j].setIcon(new ImageIcon(this.getClass().getResource(GRASS_IMAGE)));

                btn[i][j].putClientProperty("row", i);
                btn[i][j].putClientProperty("column", j);
                // btn[i][j].addActionListener(new AddPlayerActionListener(this));

                gridPanel.add(btn[i][j]);
            }
        }
    }

    public void updateHP(Player player) {
        healthLabel.setText(Integer.toString(player.getHP()));
    }

    public void updateCP(Player player) {
        healthLabel.setText(Integer.toString(player.getCP()));
    }

}
