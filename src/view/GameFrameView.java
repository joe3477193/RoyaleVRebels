package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.GameController;
import model.Board;
import model.Player;





public class GameFrameView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel bottomPanel;
	private JPanel gridPanel;
	private JPanel statsPanel;
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

	public static final int BUTTON_LENGTH = 6;

	public static final String GRASS_IMAGE      = "../images/grass.png";
	public static final String WALL_IMAGE      = "../images/wall.jpg";

	public static final String RO_ONE_IMAGE      = "../images/man.png";
	public static final String RO_TWO_IMAGE      = "../images/megastrong.png";
	public static final String RO_THREE_IMAGE      = "../images/strong.png";
	public static final String RO_FOUR_IMAGE      = "../images/watchout.png";
	public static final String RO_FIVE_IMAGE      = "../images/watchout.png";
	public static final String RO_SIX_IMAGE      = "../images/watchout.png";

	public static final String RE_ONE_IMAGE      = "../images/archer.png";
	public static final String RE_TWO_IMAGE      = "../images/berserker.png";
	public static final String RE_THREE_IMAGE      = "../images/gilgamesh.png";
	public static final String RE_FOUR_IMAGE      = "../images/rem.png";
	public static final String RE_FIVE_IMAGE      = "../images/rin.png";
	public static final String RE_SIX_IMAGE      = "../images/saber.png";

	private JButton[] rebelButton;
	private String[] rebelName;
	private String[] rebelImage;
	private JButton[] royaleButton;
	private String[] royaleName;
	private String[] royaleImage;

	private String currentImage;

	private JButton lastTile;
	private JButton[] summonButtons;


	public GameFrameView() {

		frame = new JFrame("Royals vs Rebels");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); // show gui in the middle of screen
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

		rebelButton = createSpawn(rebelName, rebelImage);
		royaleButton = createSpawn(royaleName, royaleImage);
		summonButtons = rebelButton;
	}

	private JButton[] createSpawn(String name[], String image[]) {
		JButton[] button = new JButton[6];
		for(int i=0;i<BUTTON_LENGTH;i++) {
			button[i]= new JButton(new ImageIcon(this.getClass().getResource(image[i])));
			button[i].setName(name[i]);
			button[i].addActionListener(new GameController());
		}
		return button;
	}

	private void loadSpawn(JButton button[]) {
		for(JButton icon:button) {
			deckPanel.add(icon);
		}
	}

	private void removeSpawn(JButton button[]) {
		for(JButton icon:button) {
			deckPanel.remove(icon);
		}
	}


	public void assembleBoard(Player playerOne, Player playerTwo, Board b) {

		nameOne= playerOne.getName();
		typeOne= playerOne.getFaction();
		nameTwo= playerTwo.getName();
		typeTwo= playerTwo.getFaction();
		
		statsPanel = new JPanel(new GridLayout(1, 6, 0, 0));
		deckPanel = new JPanel(new GridLayout(1, 5, 0, 0));
		gridPanel = new JPanel(new GridLayout(b.getRows(), b.getCols(), 0, 1));
		bottomPanel = new JPanel(new GridLayout(1, 2));

		playerName = new JLabel(playerOne.getName());
		statsPanel.add(new JLabel("Player Name: "));
		statsPanel.add(playerName);

		playerType = new JLabel(playerOne.getFaction());
		statsPanel.add(new JLabel("Player Type: "));
		statsPanel.add(playerType);

		/*healthLabel = new JLabel(Integer.toString(playerOne.()));
    	statsPanel.add(new JLabel("HP: "));
        statsPanel.add(healthLabel);*/

		tileBtns = new JButton[Board.BOARD_ROWS][Board.BOARD_COLS];

		deckPanel.setMaximumSize(new Dimension(100, 100));

		frame.setSize(925, 600);

		loadSpawn(rebelButton);
		genGrid(b);
		drawActionBtns();

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(statsPanel, BorderLayout.NORTH);
		topPanel.add(deckPanel, BorderLayout.CENTER);

		frame.revalidate();
		frame.repaint();

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(gridPanel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);
	}


	public void drawActionBtns() {
		moveBtn = new JButton("Move");
		bottomPanel.add(moveBtn);
		
		attackBtn = new JButton("Attack");
		
		bottomPanel.add(attackBtn);
		endTurnBtn = new JButton("End Turn");
		
		bottomPanel.add(endTurnBtn);
		
	}

	public void genGrid(Board b) {
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
				tileBtns[i][j].addActionListener(new GameController());

				gridPanel.add(tileBtns[i][j]);
			}
		}
	}

	public void colourMove() {
		moveBtn.setBackground(Color.green);
	}

	public void decolourMove() {
		moveBtn.setBackground(null);
	}

	public void colourTile(JButton tile) {
		tile.setBackground(Color.blue);
		lastTile= tile;
	}

	public void decolourTile() {
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
			summonButtons = rebelButton;
			playerName.setText(nameOne);
			playerType.setText(typeOne);
		}
		else {
			removeSpawn(rebelButton);
			loadSpawn(royaleButton);
			summonButtons = royaleButton;
			playerName.setText(nameTwo);
			playerType.setText(typeTwo);
		}		
		statsPanel.repaint();
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
	
	public void moveAddActionL(ActionListener l) {
		moveBtn.addActionListener(l);
	}
	
	public void endAddActionL(ActionListener l) {
		endTurnBtn.addActionListener(l);
	}

	public void addController(ActionListener gameController) {

		for (JButton btn : getSummonButtons()) {
			btn.addActionListener(gameController);
		}

		for (JButton[] tileRows : getTileBtns()) {
			for (JButton tile : tileRows ) {
				tile.addActionListener(gameController);
			}
		}

		moveBtn.addActionListener(gameController);
		attackBtn.addActionListener(gameController);
		endTurnBtn.addActionListener(gameController);
	}

	public JButton[] getSummonButtons() {
		return summonButtons;
	}

	public void initSummonButtons() {
		summonButtons = getRebelButton();
	}
}


