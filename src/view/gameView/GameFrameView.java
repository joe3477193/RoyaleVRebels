package view.gameView;

import model.gameEngine.GameEngine;
import model.gameEngine.GameEngineFacade;
import model.player.Player;
import model.player.RoyalePlayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.EventObject;

public class GameFrameView extends JFrame {

    public static final int ROW_PROPERTY_INDEX = 0;
    public static final int COL_PROPERTY_INDEX = 1;

    private static final int ORIGINAL_ROW = 0;
    private static final int ORIGINAL_COL = 0;
    private static final int BUTTON_LENGTH = 7;
    private static final int DECK_MAX_WIDTH = 100;
    private static final int DECK_MAX_HEIGHT = 100;
    private static final int FRAME_DEFAULT_WIDTH = 1000;
    private static final int FRAME_DEFAULT_HEIGHT = 750;
    private static final int PLAYER_ROW = 1;
    private static final int PLAYER_COL = 6;
    private static final int DECK_ROW = 1;
    private static final int DECK_COL = 5;
    private static final int ACTION_ROW = 1;
    private static final int ACTION_COL = 2;
    private static final int GRID_HGAP = 0;
    private static final int GRID_VGAP = 1;
    private static final int CASTLE_ROW = 0;
    private static final int CASTLE_COL_FACTOR = 2;
    private static final int WALL_COL_FACTOR = 4;

    private static final String STATUS = "Game status:  ";
    private static final String INDEX = "index";
    private static final String IMAGE_PATH = "../../images/";
    private static final String GRASS_IMAGE = IMAGE_PATH + "grass.png";
    private static final String WALL_IMAGE = IMAGE_PATH + "wall.jpg";
    private static final String RED_GRASS_IMAGE = IMAGE_PATH + "red_grass.png";
    private static final String BLUE_GRASS_IMAGE = IMAGE_PATH + "blue_grass.png";
    private static final String PINK_GRASS_IMAGE = IMAGE_PATH + "pink_grass.png";
    private static final String RO_ONE_IMAGE = IMAGE_PATH + "general.png";
    private static final String RO_TWO_IMAGE = IMAGE_PATH + "liutenant.png";
    private static final String RO_THREE_IMAGE = IMAGE_PATH + "infantry.png";
    private static final String RO_FOUR_IMAGE = IMAGE_PATH + "balista.png";
    private static final String RO_FIVE_IMAGE = IMAGE_PATH + "cannon.png";
    private static final String RO_SIX_IMAGE = IMAGE_PATH + "catapult.png";
    private static final String RO_SEVEN_IMAGE = IMAGE_PATH + "pitfall.png";
    private static final String RE_ONE_IMAGE = IMAGE_PATH + "leader.png";
    private static final String RE_TWO_IMAGE = IMAGE_PATH + "scoundrel.png";
    private static final String RE_THREE_IMAGE = IMAGE_PATH + "mobster.png";
    private static final String RE_FOUR_IMAGE = IMAGE_PATH + "angryman.png";
    private static final String RE_FIVE_IMAGE = IMAGE_PATH + "rascal.png";
    private static final String RE_SIX_IMAGE = IMAGE_PATH + "archer.png";
    private static final String RE_SEVEN_IMAGE = IMAGE_PATH + "boulder.png";
    private static final String CASTLE_IMAGE = IMAGE_PATH + "castle.jpg";
    private static final String CLWALL_IMAGE = IMAGE_PATH + "cwall.jpg";
    private static final String CRWALL_IMAGE = IMAGE_PATH + "crwall.jpg";
    private static final String TARGET_RED = IMAGE_PATH + "target.png";
    private static final String TARGET_GREEN = IMAGE_PATH + "target_green.png";
    private static final String TITLE = "Royals vs Rebels";
    private static final String NAME = "name";
    private static final String PLAYER_NAME = "Player Name: ";
    private static final String PLAYER_TYPE = "Player Type: ";
    private static final String PLAYER_CP = "Player CP: ";
    private static final String SAVE = "Save Game";
    private static final String QUIT = "Quit Game";
    private static final String CASTLE_HP = "Castle HP: ";
    private static final String ATTACK = "Attack";
    private static final String UNDO = "Undo";
    private static final String OFFENSIVE = "OFFENSIVE";
    private static final String DEFENSIVE = "DEFENSIVE";
    private static final String END_TURN = "End Turn";
    private static final String ROW = "row";
    private static final String COLUMN = "column";
    private static final String MOVE_TYPE = "move";
    private static final String ATTACK_TYPE = "attack";
    private static final String SUMMON_TYPE = "summon";
    private static final String FILE_TYPE = ".png";
    private static final String PLAYER = "Player ";
    private static final String VICTORY_TITLE = "GAME OVER!";
    private static final String VICTORY_MSG = " won!\n Press OK to go back to the main menu.";
    private static final String REBEL_PIECE_ONE = "Leader";
    private static final String REBEL_PIECE_TWO = "Scoundrel";
    private static final String REBEL_PIECE_THREE = "Mobster";
    private static final String REBEL_PIECE_FOUR = "Angryman";
    private static final String REBEL_PIECE_FIVE = "Rascal";
    private static final String REBEL_PIECE_SIX = "Archer";
    private static final String REBEL_PIECE_SEVEN = "Boulder";
    private static final String ROYALE_PIECE_ONE = "General";
    private static final String ROYALE_PIECE_TWO = "Liutenant";
    private static final String ROYALE_PIECE_THREE = "Infantry";
    private static final String ROYALE_PIECE_FOUR = "Balista";
    private static final String ROYALE_PIECE_FIVE = "Cannon";
    private static final String ROYALE_PIECE_SIX = "Catapult";
    private static final String ROYALE_PIECE_SEVEN = "Pitfall";

    private static JButton[][] tileBtns;

    private JButton offensiveBtn;
    private JButton defensiveBtn;
    private JFrame frame;
    private JPanel actionPanel;
    private JPanel gridPanel;
    private JPanel playerPanel;
    private JPanel deckPanel;
    private JLabel playerName, playerType, playerCP, time;
    private JButton saveButton;
    private JButton quitButton;
    private JButton undoTurnBtn;
    private JButton attackBtn;
    private JButton endTurnBtn;
    private JButton[] rebelButton;
    private String[] rebelName;
    private String[] rebelImage;
    private JButton[] royaleButton;
    private String[] royaleName;
    private String[] royaleImage;
    private String currentImage, nameOne, nameTwo;
    private JButton lastTile;
    private ArrayList<JButton> summonBtns;
    private JLabel statusLabel;
    private JLabel castleHP;

    private GameEngine g;

    public GameFrameView() {
        frame = new JFrame(TITLE);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        rebelButton = new JButton[BUTTON_LENGTH];
        rebelImage = new String[]{RE_ONE_IMAGE, RE_TWO_IMAGE, RE_THREE_IMAGE, RE_FOUR_IMAGE, RE_FIVE_IMAGE, RE_SIX_IMAGE, RE_SEVEN_IMAGE};
        rebelName = new String[]{REBEL_PIECE_ONE, REBEL_PIECE_TWO, REBEL_PIECE_THREE, REBEL_PIECE_FOUR, REBEL_PIECE_FIVE, REBEL_PIECE_SIX, REBEL_PIECE_SEVEN};
        royaleButton = new JButton[BUTTON_LENGTH];
        royaleName = new String[]{ROYALE_PIECE_ONE, ROYALE_PIECE_TWO, ROYALE_PIECE_THREE, ROYALE_PIECE_FOUR, ROYALE_PIECE_FIVE, ROYALE_PIECE_SIX, ROYALE_PIECE_SEVEN};
        royaleImage = new String[]{RO_ONE_IMAGE, RO_TWO_IMAGE, RO_THREE_IMAGE, RO_FOUR_IMAGE, RO_FIVE_IMAGE, RO_SIX_IMAGE, RO_SEVEN_IMAGE};
        summonBtns = new ArrayList<>();
        statusLabel = new JLabel(STATUS);
    }

    private JButton[] createSpawn(String[] name, String[] image) {
        JButton[] button = new JButton[BUTTON_LENGTH];
        for (int i = 0; i < BUTTON_LENGTH; i++) {
            button[i] = new JButton(new ImageIcon(this.getClass().getResource(image[i])));
            button[i].putClientProperty(INDEX, i);
            button[i].setName(name[i]);
            summonBtns.add(button[i]);
        }
        return button;
    }

    @Override
    public Cursor getCursor() {
        return frame.getCursor();
    }

    public void setCursor(String name) {
        Image icon = new ImageIcon(this.getClass().getResource(name)).getImage();
        frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(ORIGINAL_ROW, ORIGINAL_COL), NAME));
    }

    public void setCursor(Image icon, String cursorName) {
        frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(ORIGINAL_ROW, ORIGINAL_COL), cursorName));
    }

    public JButton getOffensiveBtn() {
        return offensiveBtn;
    }

    public JButton getDefensiveBtn() {
        return defensiveBtn;
    }

    public JButton getEndTurnBtn() {
        return endTurnBtn;
    }

    public JButton getAttackBtn() {
        return attackBtn;
    }

    public JButton getUndoBtn() {
        return undoTurnBtn;
    }

    private void loadSpawn(JButton[] button) {
        for (JButton icon : button) {
            deckPanel.add(icon);
        }
    }

    private void removeSpawn(JButton[] button) {
        for (JButton icon : button) {
            deckPanel.remove(icon);
        }
    }

    public void assembleBoard(GameEngine g) {
        this.g = g;
        Player playerOne = g.getRebelPlayer();
        Player playerTwo = g.getRoyalePlayer();
        nameOne = playerOne.getName();
        nameTwo = playerTwo.getName();
        time = new JLabel("");
        playerPanel = new JPanel(new GridLayout(PLAYER_ROW, PLAYER_COL));
        deckPanel = new JPanel(new GridLayout(DECK_ROW, DECK_COL));
        gridPanel = new JPanel(new GridLayout(g.getMaxRows(), g.getMaxCols(), GRID_HGAP, GRID_VGAP));
        actionPanel = new JPanel(new GridLayout(ACTION_ROW, ACTION_COL));
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(time, BorderLayout.EAST);
        playerName = new JLabel(playerOne.getName());
        playerPanel.add(new JLabel(PLAYER_NAME));
        playerPanel.add(playerName);
        playerType = new JLabel(playerOne.getFaction());
        playerPanel.add(new JLabel(PLAYER_TYPE));
        playerPanel.add(playerType);
        playerCP = new JLabel(String.valueOf(playerOne.getCP()));
        playerPanel.add(new JLabel(PLAYER_CP));
        playerPanel.add(playerCP);
        tileBtns = new JButton[GameEngineFacade.BOARD_ROW_LENGTH][GameEngineFacade.BOARD_COL_LENGTH];
        deckPanel.setMaximumSize(new Dimension(DECK_MAX_WIDTH, DECK_MAX_HEIGHT));
        frame.setSize(FRAME_DEFAULT_WIDTH, FRAME_DEFAULT_HEIGHT);
        // show gui in the middle of screen
        frame.setLocationRelativeTo(null);
        rebelButton = createSpawn(rebelName, rebelImage);
        royaleButton = createSpawn(royaleName, royaleImage);
        // rebel goes first
        loadSpawn(rebelButton);
        genGrid();
        drawActionBtns();
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel botPanel = new JPanel(new BorderLayout());
        JPanel menuPanel = new JPanel(new BorderLayout());
        saveButton = new JButton(SAVE);
        quitButton = new JButton(QUIT);
        castleHP = new JLabel(CASTLE_HP + g.getCastleHp(), SwingConstants.CENTER);
        menuPanel.add(saveButton, BorderLayout.EAST);
        menuPanel.add(castleHP, BorderLayout.CENTER);
        menuPanel.add(quitButton, BorderLayout.WEST);
        JPanel topBar = new JPanel(new BorderLayout());
        playerPanel.add(menuPanel);
        topBar.add(playerPanel, BorderLayout.NORTH);
        topBar.add(deckPanel, BorderLayout.CENTER);
        topPanel.add(menuPanel, BorderLayout.NORTH);
        topPanel.add(topBar, BorderLayout.CENTER);
        botPanel.add(actionPanel, BorderLayout.SOUTH);
        botPanel.add(statusPanel, BorderLayout.NORTH);
        frame.revalidate();
        frame.repaint();
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(botPanel, BorderLayout.SOUTH);
        updatePlayerInfo(playerOne);
        if (g.getPerformed()) {
            colourEndTurn();
        }
    }

    public void updatePlayerInfo(Player player) {
        if (player instanceof RoyalePlayer) {
            removeSpawn(rebelButton);
            loadSpawn(royaleButton);
        }
        else {
            removeSpawn(royaleButton);
            loadSpawn(rebelButton);
        }
        playerName.setText(player.getName());
        playerType.setText(player.getFaction());
        playerCP.setText(Integer.toString(player.getCP()));
        playerPanel.repaint();
        deckPanel.repaint();
    }

    private void drawActionBtns() {
        attackBtn = new JButton(ATTACK);
        actionPanel.add(attackBtn);
        undoTurnBtn = new JButton(UNDO);
        actionPanel.add(undoTurnBtn);
        undoTurnBtn.setVisible(false);
        offensiveBtn = new JButton(OFFENSIVE);
        actionPanel.add(offensiveBtn);
        defensiveBtn = new JButton(DEFENSIVE);
        actionPanel.add(defensiveBtn);
        endTurnBtn = new JButton(END_TURN);
        actionPanel.add(endTurnBtn);
    }

    private void genGrid() {
        for (int row = 0; row < GameEngineFacade.BOARD_ROW_LENGTH; row++) {
            for (int col = 0; col < GameEngineFacade.BOARD_COL_LENGTH; col++) {
                tileBtns[row][col] = new JButton();
                if (g.isWallTile(row, col)) {
                    tileBtns[row][col].setIcon(new ImageIcon(this.getClass().getResource(WALL_IMAGE)));
                }
                else if (row == CASTLE_ROW && col % CASTLE_COL_FACTOR != 0) {
                    tileBtns[row][col].setIcon(new ImageIcon(this.getClass().getResource(CASTLE_IMAGE)));
                }
                else if (row == CASTLE_ROW && col % WALL_COL_FACTOR == 0) {
                    tileBtns[row][col].setIcon(new ImageIcon(this.getClass().getResource(CLWALL_IMAGE)));
                }
                else if (row == CASTLE_ROW) {
                    tileBtns[row][col].setIcon(new ImageIcon(this.getClass().getResource(CRWALL_IMAGE)));
                }
                else tileBtns[row][col].setIcon(new ImageIcon(this.getClass().getResource(GRASS_IMAGE)));
                tileBtns[row][col].putClientProperty(ROW, row);
                tileBtns[row][col].putClientProperty(COLUMN, col);
                gridPanel.add(tileBtns[row][col]);
            }
        }
    }

    public void colourAttack() {
        attackBtn.setBackground((Color.green));
    }

    public void colourRedAttack() {
        attackBtn.setBackground((Color.RED));
    }

    public void colourTile(JButton tile) {
        tile.setBackground(Color.blue);
        lastTile = tile;
    }

    public void colourTile(int row, int col, String actionType) {
        try {
            String name = GRASS_IMAGE;
            if (actionType.equals(MOVE_TYPE)) {
                name = BLUE_GRASS_IMAGE;
            }
            else if (actionType.equals(ATTACK_TYPE)) {
                name = RED_GRASS_IMAGE;
            }
            else if (actionType.equals(SUMMON_TYPE)) {
                name = PINK_GRASS_IMAGE;
            }
            tileBtns[row][col].setIcon(new ImageIcon(this.getClass().getResource(name)));
        } catch (RuntimeException ignored) {
        }
    }

    private void colourRedTile(JButton tile) {
        tile.setBackground(Color.red);
        lastTile = tile;
    }

    public void colourRed(JButton tile) {
        colourRedTile(tile);
        colourRedAttack();
    }

    public void colourEndTurn() {
        endTurnBtn.setBackground(Color.green);
    }

    public void decolour() {
        if (lastTile != null) {
            lastTile.setBorder(null);
            lastTile.setBackground(null);
            lastTile = null;
        }
        attackBtn.setBackground(null);
    }

    public void decolourEndTurn() {
        endTurnBtn.setBackground(null);
    }

    public void updateStatus(String statusMsg) {
        statusLabel.setText(STATUS + statusMsg);
    }

    public JButton[][] getTileBtns() {
        return tileBtns;
    }

    public String getImage() {
        return currentImage;
    }

    public void setImage(String image) {
        currentImage = image;
    }

    public void removeImage() {
        currentImage = null;
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

    public void updateTime(String text) {
        time.setText(text);
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getQuitButton() {
        return quitButton;
    }

    public String[] getPlayerData() {
        return new String[]{nameOne, nameTwo};
    }

    public void setTileIcon(int row, int col, String name) {
        tileBtns[row][col].setIcon(new ImageIcon(this.getClass().getResource(name)));
        tileBtns[row][col].setName(name);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void disposeFrame() {
        frame.dispose();
    }

    public void resetCursor() {
        frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public int[] findButtonCoordinates(EventObject e) {
        return new int[]{(int) getSource(e).getClientProperty(ROW), (int) getSource(e).getClientProperty(COLUMN)};
    }

    public int findButtonIndex(EventObject e) {
        return (int) getSource(e).getClientProperty(INDEX);
    }

    public void showPieceInfo(EventObject e, String pieceInfo) {
        getSource(e).setToolTipText(pieceInfo);
    }

    public JButton getSource(EventObject e) {
        Object source = e.getSource();
        return (JButton) source;
    }

    public String getTargetRed() {
        return TARGET_RED;
    }

    public String getTargetGreen() {
        return TARGET_GREEN;
    }

    public JButton getTile(int row, int col) {
        return tileBtns[row][col];
    }

    public void setTileName(int row, int col, String name) {
        tileBtns[row][col].setName(name);
    }

    public String getImagePath(String name) {
        return IMAGE_PATH + name.toLowerCase() + FILE_TYPE;
    }

    public void updateCastleHp() {
        castleHP.setText(CASTLE_HP + g.getCastleHp());
        revalidate();
    }

    public void gameOver(String name) {
        JOptionPane.showMessageDialog(frame, PLAYER + name + VICTORY_MSG, VICTORY_TITLE, JOptionPane.INFORMATION_MESSAGE);
    }
}


