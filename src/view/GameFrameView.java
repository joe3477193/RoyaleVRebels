package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.*;
import model.*;


public class GameFrameView extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private JPanel gridPanel;
    private JPanel selectPanel;
    private JLabel healthLabel;
    private JLabel combatPlabel;
    private JLabel playername, playertype;
    private static JButton[][] tileBtns;
    private static Board board;
    private JButton item1, item2, item3, item4, item5, item6;

    public static final String GRASS_IMAGE      = "../images/grass.png";
    public static final String WALL_IMAGE      = "../images/wall.jpg";
    public static final String ONE_IMAGE      = "../images/man.png";
    public static final String TWO_IMAGE      = "../images/megastrong.png";
    public static final String THREE_IMAGE      = "../images/strong.png";
    public static final String FOUR_IMAGE      = "../images/watchout.png";
    public static final String FIVE_IMAGE      = "../images/watchout.png";
    public static final String SIX_IMAGE      = "../images/watchout.png";
    
    public GameFrameView() {

        frame = new JFrame("Royals vs Rebels");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // show gui in the middle of screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    

    public void assembleBoard(Player playerOne, Board b) {

        JPanel statsPanel = new JPanel(new GridLayout(1, 6, 0, 0));
        selectPanel = new JPanel(new GridLayout(1, 5, 0, 0));
        gridPanel = new JPanel(new GridLayout(b.getRows(), b.getCols(), 0, 1));
        
        playername = new JLabel(playerOne.getName());
        statsPanel.add(new JLabel("Player Name: "));
        statsPanel.add(playername);
        
        playertype = new JLabel(playerOne.getFaction());
        statsPanel.add(new JLabel("Player Type: "));
        statsPanel.add(playertype);

        // We should show the hp of castle during the whole game, and it can be a large red bar on the top side
    	/*healthLabel = new JLabel(Integer.toString(((Royal) playerOne).getHp()));
    	statsPanel.add(new JLabel("HP: "));
        statsPanel.add(healthLabel);*/
        
        tileBtns = new JButton[Board.BOARD_ROWS][Board.BOARD_COLS];

        selectPanel.setMaximumSize(new Dimension(100, 100));

        frame.setSize(925, 600);
        paintRoyaleDeck();

        JPanel main = new JPanel(new BorderLayout());
        main.add(statsPanel, BorderLayout.NORTH);
        main.add(selectPanel, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();

        frame.add(main, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
    }

    private void paintRoyaleDeck() {
        item1 = new JButton(new ImageIcon(this.getClass().getResource(ONE_IMAGE)));
        item1.setName("spawn_General");
        item1.addActionListener(new SummonPieceActionListener(this.frame));
        selectPanel.add(item1);
         
        item2 = new JButton(new ImageIcon(this.getClass().getResource(TWO_IMAGE)));
        item2.setName("spawn_Lieutenant");
        item2.addActionListener(new SummonPieceActionListener(this.frame));
        selectPanel.add(item2);

        item3 = new JButton(new ImageIcon(this.getClass().getResource(THREE_IMAGE)));
        item3.setName("spawn_Spearman");
        item3.addActionListener(new SummonPieceActionListener(this.frame));
        selectPanel.add(item3);

        item4 = new JButton(new ImageIcon(this.getClass().getResource(FOUR_IMAGE)));
        item4.setName("spawn_Footman");
        item4.addActionListener(new SummonPieceActionListener(this.frame));
        selectPanel.add(item4);
        
        item5 = new JButton(new ImageIcon(this.getClass().getResource(ONE_IMAGE)));
        item5.setName("spawn_Archer");
        item5.addActionListener(new SummonPieceActionListener(this.frame));
        selectPanel.add(item5);

        item6 = new JButton(new ImageIcon(this.getClass().getResource(TWO_IMAGE)));
        item6.setName("spawn_Cannon");
        item6.addActionListener(new SummonPieceActionListener(this.frame));
        selectPanel.add(item6);

        genGrid();

    }

    public void genGrid() {
        for (int i = 0; i < Board.BOARD_ROWS; i++) {
            for (int j = 0; j < Board.BOARD_COLS; j++) {
                tileBtns[i][j] = new JButton();

                if ((i % 5 <= 2) && j % 4 == 3)
                    tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource(WALL_IMAGE)));
                else
                    tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource(GRASS_IMAGE)));

                tileBtns[i][j].putClientProperty("row", i);
                tileBtns[i][j].putClientProperty("column", j);
                // add ability to place pieces on tiles
                tileBtns[i][j].addActionListener(new PlacePieceActionListener(this));

                gridPanel.add(tileBtns[i][j]);
            }
        }
    }
    
    public JButton[][] getTileBtns() {
    	return tileBtns;
    }
    
    public Board getBoard() {
    	return board;
    }



    public JFrame getFrame() {
    	return frame;
    }
}