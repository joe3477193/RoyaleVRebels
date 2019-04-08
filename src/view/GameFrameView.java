package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.*;
import model.*;
import model.Piece.Type;


public class GameFrameView extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private JPanel bottomPanel;
    private JPanel gridPanel;
    private JPanel statsPanel;
    private JPanel selectPanel;
    private JLabel healthLabel;
    private JLabel combatPlabel;
    private JLabel playername, playertype;
    private static JButton[][] tileBtns;
    private static Board board;
    public JButton spawn_General;
    public JButton spawn_Lieutenant;
    public JButton spawn_Spearman;
    public JButton spawn_Footman;
    public JButton spawn_Archer;
    public JButton spawn_Cannon;

    public static final String GRASS_IMAGE      = "../images/grass.png";
    public static final String WALL_IMAGE      = "../images/wall.jpg";
    
    public static final String RO_ONE_IMAGE      = "../images/man.png";
    public static final String RO_TWO_IMAGE      = "../images/megastrong.png";
    public static final String RO_THREE_IMAGE      = "../images/strong.png";
    public static final String RO_FOUR_IMAGE      = "../images/watchout.png";
    public static final String RO_FIVE_IMAGE      = "../images/watchout.png";
    public static final String RO_SIX_IMAGE      = "../images/watchout.png";
    
    public static final String RE_ONE_IMAGE      = "../images/watchout.png";
    public static final String RE_TWO_IMAGE      = "../images/watchout.png";
    public static final String RE_THREE_IMAGE      = "../images/watchout.png";
    public static final String RE_FOUR_IMAGE      = "../images/watchout.png";
    public static final String RE_FIVE_IMAGE      = "../images/watchout.png";
    public static final String RE_SIX_IMAGE      = "../images/watchout.png";
    
    /*LEADER("Rebel", "LD", Type.TROOP, 5, 50, 30, 1, 1),
	SCOUNDREL("Rebel", "SC", Type.TROOP, 3, 100, 10, 1, 1),
	MOBSTER("Rebel", "MB", Type.TROOP, 1, 20, 20, 3, 1),
	ANGRYMAN("Rebel", "AG", Type.TROOP, 3, 20, 50, 4, 3),
	SPEARER("Rebel", "SP", Type.TROOP, 3, 40, 30, 1, 6),
	CATAPULT("Rebel", "CA", Type.ARTILLERY, 5, 50, 20, 1, 8),
	GENERAL("Royale", "GN", Type.TROOP, 5, 50, 50, 1, 1),
	LIUTENANT("Royale", "LT", Type.TROOP, 3, 30, 30, 2, 1),
	INFANTRY("Royale", "IF", Type.TROOP, 5, 20, 20, 3, 1),
	BALISTA("Royale", "BA", Type.ARTILLERY, 8, 40, 30, 1, 8),
	CANNON("Royale", "CA", Type.ARTILLERY, 10, 50, 50, 1, 10),
	ARCHER("Royale", "AC", Type.TROOP, 5, 50, 30, 2, 6);*/
    
    private JButton[] rebelSpawnButton;
    private String[] rebelSpawnName;
    private String[] rebelSpawnImage;
    
    public GameFrameView() {

        frame = new JFrame("Royals vs Rebels");
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // show gui in the middle of screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        board = new Board();
        
        rebelSpawnButton= new JButton[6]; 

    }
    
    private void createSpawn() {
    	spawn_General = new JButton(new ImageIcon(this.getClass().getResource(ONE_IMAGE)));
    	spawn_General.setName("spawn_General");
    	spawn_General.addActionListener(new SummonPieceActionListener(this));
        selectPanel.add(spawn_General);
    }
    

    public void assembleBoard(Player playerOne, Board b) {

        statsPanel = new JPanel(new GridLayout(1, 6, 0, 0));
        selectPanel = new JPanel(new GridLayout(1, 5, 0, 0));
        gridPanel = new JPanel(new GridLayout(b.getRows(), b.getCols(), 0, 1));
        bottomPanel = new JPanel(new GridLayout(1, 2));
        
        playername = new JLabel(playerOne.getName());
        statsPanel.add(new JLabel("Player Name: "));
        statsPanel.add(playername);
        
        playertype = new JLabel(playerOne.getFaction());
        statsPanel.add(new JLabel("Player Type: "));
        statsPanel.add(playertype);

    	/*healthLabel = new JLabel(Integer.toString(playerOne.()));
    	statsPanel.add(new JLabel("HP: "));
        statsPanel.add(healthLabel);*/
        
        tileBtns = new JButton[Board.BOARD_ROWS][Board.BOARD_COLS];

        selectPanel.setMaximumSize(new Dimension(100, 100));

        frame.setSize(925, 600);
        paintRoyaleDeck();
        drawActionBtns();

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(statsPanel, BorderLayout.NORTH);
        topPanel.add(selectPanel, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void paintRoyaleDeck() {
    	spawn_General = new JButton(new ImageIcon(this.getClass().getResource(RO_ONE_IMAGE)));
    	spawn_General.setName("spawn_General");
    	spawn_General.addActionListener(new SummonPieceActionListener(this, "royal"));
        selectPanel.add(spawn_General);
         
        spawn_Lieutenant = new JButton(new ImageIcon(this.getClass().getResource(RO_TWO_IMAGE)));
        spawn_Lieutenant.setName("spawn_Lieutenant");
        spawn_Lieutenant.addActionListener(new SummonPieceActionListener(this, "royal"));
        selectPanel.add(spawn_Lieutenant);

        spawn_Spearman = new JButton(new ImageIcon(this.getClass().getResource(RO_THREE_IMAGE)));
        spawn_Spearman.setName("spawn_Spearman");
        spawn_Spearman.addActionListener(new SummonPieceActionListener(this, "royal"));
        selectPanel.add(spawn_Spearman);

        spawn_Footman = new JButton(new ImageIcon(this.getClass().getResource(RO_FOUR_IMAGE)));
        spawn_Footman.setName("spawn_Footman");
        spawn_Footman.addActionListener(new SummonPieceActionListener(this, "royal"));
        selectPanel.add(spawn_Footman);
        
        spawn_Archer = new JButton(new ImageIcon(this.getClass().getResource(RO_ONE_IMAGE)));
        spawn_Archer.setName("spawn_Archer");
        spawn_Archer.addActionListener(new SummonPieceActionListener(this, "royal"));
        selectPanel.add(spawn_Archer);

        spawn_Cannon = new JButton(new ImageIcon(this.getClass().getResource(RO_TWO_IMAGE)));
        spawn_Cannon.setName("spawn_Cannon");
        spawn_Cannon.addActionListener(new SummonPieceActionListener(this, "royal"));
        selectPanel.add(spawn_Cannon);

        genGrid();

    }
    
    private void paintRebelDeck() {
    	spawn_General = new JButton(new ImageIcon(this.getClass().getResource(RE_ONE_IMAGE)));
    	spawn_General.setName("spawn_General");
    	spawn_General.addActionListener(new SummonPieceActionListener(this, "rebel"));
        selectPanel.add(spawn_General);
         
        spawn_Lieutenant = new JButton(new ImageIcon(this.getClass().getResource(RE_TWO_IMAGE)));
        spawn_Lieutenant.setName("spawn_Lieutenant");
        spawn_Lieutenant.addActionListener(new SummonPieceActionListener(this, "rebel"));
        selectPanel.add(spawn_Lieutenant);

        spawn_Spearman = new JButton(new ImageIcon(this.getClass().getResource(RE_THREE_IMAGE)));
        spawn_Spearman.setName("spawn_Spearman");
        spawn_Spearman.addActionListener(new SummonPieceActionListener(this, "rebel"));
        selectPanel.add(spawn_Spearman);

        spawn_Footman = new JButton(new ImageIcon(this.getClass().getResource(RE_FOUR_IMAGE)));
        spawn_Footman.setName("spawn_Footman");
        spawn_Footman.addActionListener(new SummonPieceActionListener(this,"rebel"));
        selectPanel.add(spawn_Footman);
        
        spawn_Archer = new JButton(new ImageIcon(this.getClass().getResource(RE_ONE_IMAGE)));
        spawn_Archer.setName("spawn_Archer");
        spawn_Archer.addActionListener(new SummonPieceActionListener(this, "rebel"));
        selectPanel.add(spawn_Archer);

        spawn_Cannon = new JButton(new ImageIcon(this.getClass().getResource(RE_TWO_IMAGE)));
        spawn_Cannon.setName("spawn_Cannon");
        spawn_Cannon.addActionListener(new SummonPieceActionListener(this, "rebel"));
        selectPanel.add(spawn_Cannon);

        genGrid();

    }
    
    public void drawActionBtns() {
    	JButton moveBtn = new JButton("Move");
    	bottomPanel.add(moveBtn);
    	JButton attackBtn = new JButton("Attack");
    	bottomPanel.add(attackBtn);
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

