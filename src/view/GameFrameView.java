package view;

import model.board.Board;
import model.players.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameFrameView extends JFrame{
	private Board b;

	private JFrame frame;
	private JPanel actionPanel;
	private JPanel gridPanel;
	private JPanel playerPanel;
	private JPanel deckPanel;

	private JLabel healthLabel;
	private JLabel combatPlabel;
	private JLabel playerName, playerType;
	private String nameOne, typeOne, nameTwo, typeTwo;
	private static JButton[][] tileBtns;

	public JButton getMoveBtn() {
		return moveBtn;
	}

	private JButton moveBtn;

	public JButton getAttackBtn() {
		return attackBtn;
	}

	private JButton attackBtn;

	public JButton getEndTurnBtn() {
		return endTurnBtn;
	}

	private JButton endTurnBtn;

	private static final int BUTTON_LENGTH = 6;

	private static final String GRASS_IMAGE      = "../images/grass.png";
	private static final String WALL_IMAGE      = "../images/wall.jpg";

	private static final String RO_ONE_IMAGE      = "../images/man.png";
	private static final String RO_TWO_IMAGE      = "../images/megastrong.png";
	private static final String RO_THREE_IMAGE      = "../images/strong.png";
	private static final String RO_FOUR_IMAGE      = "../images/watchout.png";
	private static final String RO_FIVE_IMAGE      = "../images/watchout.png";
	private static final String RO_SIX_IMAGE      = "../images/watchout.png";

	private static final String RE_ONE_IMAGE      = "../images/archer.png";
	private static final String RE_TWO_IMAGE      = "../images/berserker.png";
	private static final String RE_THREE_IMAGE      = "../images/gilgamesh.png";
	private static final String RE_FOUR_IMAGE      = "../images/rem.png";
	private static final String RE_FIVE_IMAGE      = "../images/rin.png";
	private static final String RE_SIX_IMAGE      = "../images/saber.png";

	public static final String STATUS = "Game status:  ";
	private JButton[] rebelButton;
	private String[] rebelName;
	private String[] rebelImage;
	private JButton[] royaleButton;
	private String[] royaleName;
	private String[] royaleImage;

	private String currentImage;

	private JButton lastTile;
	private ArrayList<JButton> summonBtns;

	JLabel statusLabel;

	public JLabel getStatusLabel() {
		return statusLabel;
	}

	public GameFrameView() {

		frame = new JFrame("Royals vs Rebels");
		frame.setResizable(false);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		rebelButton= new JButton[6]; 
		rebelImage= new String[]{RE_ONE_IMAGE, RE_TWO_IMAGE, RE_THREE_IMAGE, RE_FOUR_IMAGE, RE_FIVE_IMAGE,
				RE_SIX_IMAGE
		};
		rebelName= new String[] {"Leader", "Scoundrel", "Mobster", "Angryman", "Rascal", "Catapult"
		};

		royaleButton = new JButton[6];
		royaleName = new String[]{"General", "Liutenant", "Infantry", "Balista", "Cannon", "Archer"
		};
		royaleImage = new String[] {RO_ONE_IMAGE, RO_TWO_IMAGE, RO_THREE_IMAGE, RO_FOUR_IMAGE, RO_FIVE_IMAGE,
				RO_SIX_IMAGE
		};
		summonBtns = new ArrayList<>();
		statusLabel = new JLabel(STATUS);
	}

	private JButton[] createSpawn(String[] name, String[] image) {
		JButton[] button = new JButton[6];
		for(int i=0;i<BUTTON_LENGTH;i++) {
			button[i]= new JButton(new ImageIcon(this.getClass().getResource(image[i])));
			button[i].setName(name[i]);
			summonBtns.add(button[i]);
		}
		return button;
	}

	private void loadSpawn(JButton[] button) {
		for(JButton icon:button) {
			deckPanel.add(icon);
		}
	}

	private void removeSpawn(JButton[] button) {
		for(JButton icon:button) {
			deckPanel.remove(icon);
		}
	}


	public void assembleBoard(Player playerOne, Player playerTwo, Board b) {

		this.b= b;
		nameOne= playerOne.getName();
		typeOne= playerOne.getFaction();
		nameTwo= playerTwo.getName();
		typeTwo= playerTwo.getFaction();
		
		playerPanel = new JPanel(new GridLayout(1, 6, 0, 0));
		deckPanel = new JPanel(new GridLayout(1, 5, 0, 0));
		gridPanel = new JPanel(new GridLayout(b.getRows(), b.getCols(), 0, 1));
		actionPanel = new JPanel(new GridLayout(1, 2));
		JPanel statusPanel = new JPanel(new GridLayout(1, 1));
		statusPanel.add(statusLabel);

		playerName = new JLabel(playerOne.getName());
		playerPanel.add(new JLabel("Player Name: "));
		playerPanel.add(playerName);

		playerType = new JLabel(playerOne.getFaction());
		playerPanel.add(new JLabel("Player Type: "));
		playerPanel.add(playerType);

		/*healthLabel = new JLabel(Integer.toString(playerOne.()));
    	playerPanel.add(new JLabel("HP: "));
        playerPanel.add(healthLabel);*/

		tileBtns = new JButton[Board.BOARD_ROWS][Board.BOARD_COLS];

		deckPanel.setMaximumSize(new Dimension(100, 100));

		frame.setSize(1000, 750);
		frame.setLocationRelativeTo(null); // show gui in the middle of screen

		rebelButton = createSpawn(rebelName, rebelImage);
		royaleButton = createSpawn(royaleName, royaleImage);

		loadSpawn(rebelButton);
		genGrid();
		drawActionBtns();

		JPanel topPanel = new JPanel(new BorderLayout());
		JPanel botPanel = new JPanel(new BorderLayout());
		topPanel.add(playerPanel, BorderLayout.NORTH);
		topPanel.add(deckPanel, BorderLayout.CENTER);

		botPanel.add(actionPanel, BorderLayout.SOUTH);
		botPanel.add(statusPanel, BorderLayout.NORTH);


		frame.revalidate();
		frame.repaint();

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(gridPanel, BorderLayout.CENTER);
		frame.add(botPanel, BorderLayout.SOUTH);
	}


	private void drawActionBtns() {
		moveBtn = new JButton("Move");
		actionPanel.add(moveBtn);
		
		attackBtn = new JButton("Attack");
		
		actionPanel.add(attackBtn);
		endTurnBtn = new JButton("End Turn");
		
		actionPanel.add(endTurnBtn);
		
	}

	private void genGrid() {
		for (int i = 0; i < Board.BOARD_ROWS; i++) {
			for (int j = 0; j < Board.BOARD_COLS; j++) {
				tileBtns[i][j] = new JButton();

				if ((i % 5 <= 2) && j % 4 == 3)
					tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource(WALL_IMAGE)));
				else
					tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource(GRASS_IMAGE)));

				tileBtns[i][j].putClientProperty("row", i);
				tileBtns[i][j].putClientProperty("column", j);

				gridPanel.add(tileBtns[i][j]);
			}
		}
	}

	public void colourMove() {
		moveBtn.setBackground(Color.green);
	}

	private void decolourMove() {
		moveBtn.setBackground(null);
	}

	public void colourTile(JButton tile) {
		tile.setBackground(Color.blue);
		lastTile= tile;
	}

	private void decolourTile() {
		if(lastTile!=null) {
			lastTile.setBorder(null);
			lastTile.setBackground(null);
			lastTile= null;
		}

	}
	
	public void decolour() {
		decolourMove();
		decolourTile();
	}

	public void updateBar(int turn) {
		
		if(turn==0) {
			removeSpawn(royaleButton);
			loadSpawn(rebelButton);
			playerName.setText(nameOne);
			playerType.setText(typeOne);
		}
		else {
			System.out.println(rebelButton[2]);
			removeSpawn(rebelButton);
			loadSpawn(royaleButton);
			playerName.setText(nameTwo);
			playerType.setText(typeTwo);
		}		
		playerPanel.repaint();
		deckPanel.repaint();
	}

	public JButton[][] getTileBtns() {
		return tileBtns;
	}

	public JFrame getFrame() {
		return frame;
	}

	public String getImage() {
		return currentImage;
	}

	public void setImage(String image) {
		currentImage= image;
	}

	public void removeImage() {
		currentImage= null;
	}

	public JButton[] getRebelButton() {
		return rebelButton;
	}

	public String[] getRebelName() {
		return rebelName;
	}

	public String[] getRebelImage() {
		return rebelImage;
	}

	public JButton[] getRoyaleButton() {
		return royaleButton;
	}

	public String[] getRoyaleName() {
		return royaleName;
	}

	public String[] getRoyaleImage() {
		return royaleImage;
	}
	
	public String getGrass() {
		return GRASS_IMAGE;
	}

	public ArrayList<JButton> getSummonBtns() {
		return summonBtns;
	}
}


