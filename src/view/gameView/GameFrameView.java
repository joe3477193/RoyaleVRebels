package view.gameView;

import model.gameEngine.GameEngine;
import model.gameEngine.GameEngineFacade;
import model.player.Player;
import model.player.RoyalePlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.EventObject;

public class GameFrameView extends JFrame {

    public static final String STATUS = "Game status:  ";
    private static final int BUTTON_LENGTH = 7;
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

    private static final int ORIGINAL_ROW = 0;
    private static final int ORIGINAL_COL = 0;

    private static JButton[][] tileBtns;

    private JButton offensiveBtn;
    private JButton defensiveBtn;
    private JFrame frame;
    private JPanel actionPanel;
    private JPanel gridPanel;
    private JPanel playerPanel;
    private JPanel deckPanel;
    private JPanel menuPanel;
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

        frame = new JFrame("Royals vs Rebels");
        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        rebelButton = new JButton[BUTTON_LENGTH];
        rebelImage = new String[]{RE_ONE_IMAGE, RE_TWO_IMAGE, RE_THREE_IMAGE, RE_FOUR_IMAGE, RE_FIVE_IMAGE,
                RE_SIX_IMAGE, RE_SEVEN_IMAGE
        };
        rebelName = new String[]{"Leader", "Scoundrel", "Mobster", "Angryman", "Rascal", "Archer", "Boulder"
        };

        royaleButton = new JButton[BUTTON_LENGTH];
        royaleName = new String[]{"General", "Liutenant", "Infantry", "Balista", "Cannon", "Catapult", "Pitfall"
        };
        royaleImage = new String[]{RO_ONE_IMAGE, RO_TWO_IMAGE, RO_THREE_IMAGE, RO_FOUR_IMAGE, RO_FIVE_IMAGE,
                RO_SIX_IMAGE, RO_SEVEN_IMAGE
        };
        summonBtns = new ArrayList<>();
        statusLabel = new JLabel(STATUS);
    }

    private JButton[] createSpawn(String[] name, String[] image) {
        JButton[] button = new JButton[BUTTON_LENGTH];
        for (int i = 0; i < BUTTON_LENGTH; i++) {
            button[i] = new JButton(new ImageIcon(this.getClass().getResource(image[i])));
            button[i].setName(name[i]);
            summonBtns.add(button[i]);
        }
        return button;
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

    public JLabel getStatusLabel() {
        return statusLabel;
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

        playerPanel = new JPanel(new GridLayout(1, 6, 0, 0));
        deckPanel = new JPanel(new GridLayout(1, 5, 0, 0));
        gridPanel = new JPanel(new GridLayout(g.getMaxRows(), g.getMaxCols(), 0, 1));
        actionPanel = new JPanel(new GridLayout(1, 2));
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(time, BorderLayout.EAST);

        playerName = new JLabel(playerOne.getName());
        playerPanel.add(new JLabel("Player Name: "));
        playerPanel.add(playerName);

        playerType = new JLabel(playerOne.getFaction());
        playerPanel.add(new JLabel("Player Type: "));
        playerPanel.add(playerType);

        playerCP = new JLabel(String.valueOf(playerOne.getCP()));
        playerPanel.add(new JLabel("Player CP: "));
        playerPanel.add(playerCP);

        tileBtns = new JButton[GameEngineFacade.BOARD_MAX_ROWS][GameEngineFacade.BOARD_MAX_COLS];

        deckPanel.setMaximumSize(new Dimension(100, 100));

        frame.setSize(1000, 750);
        frame.setLocationRelativeTo(null); // show gui in the middle of screen

        rebelButton = createSpawn(rebelName, rebelImage);
        royaleButton = createSpawn(royaleName, royaleImage);

        // rebel goes first
        loadSpawn(rebelButton);
        genGrid();
        drawActionBtns();

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel botPanel = new JPanel(new BorderLayout());

        menuPanel = new JPanel(new BorderLayout());
        saveButton = new JButton("Save Game");
        quitButton = new JButton("Quit Game");
        castleHP = new JLabel("Castle HP: 500", SwingConstants.CENTER);
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

        updateBar(playerOne);

        if (g.getHasPerformed()) {
            colourEndTurn();
        }
    }

    private void drawActionBtns() {
        attackBtn = new JButton("Attack");
        actionPanel.add(attackBtn);

        undoTurnBtn = new JButton("Undo");
        undoTurnBtn.setVisible(false);
        actionPanel.add(undoTurnBtn);

        offensiveBtn = new JButton("OFFENSIVE");
        actionPanel.add(offensiveBtn);

        defensiveBtn = new JButton("DEFENSIVE");
        actionPanel.add(defensiveBtn);

        endTurnBtn = new JButton("End Turn");
        actionPanel.add(endTurnBtn);

    }

    private void genGrid() {
        for (int i = 0; i < GameEngineFacade.BOARD_MAX_ROWS; i++) {
            for (int j = 0; j < GameEngineFacade.BOARD_MAX_COLS; j++) {
                tileBtns[i][j] = new JButton();
                if (g.isWall(i, j)) {
                    tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource(WALL_IMAGE)));
                } else if (i == 0 && j % 2 != 0) {
                    tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource(CASTLE_IMAGE)));
                } else if (i == 0 && j % 4 == 0) {
                    tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource(CLWALL_IMAGE)));
                } else if (i == 0 && j % 4 == 2) {
                    tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource(CRWALL_IMAGE)));
                } else
                    tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource(GRASS_IMAGE)));

                tileBtns[i][j].putClientProperty("row", i);
                tileBtns[i][j].putClientProperty("column", j);

                gridPanel.add(tileBtns[i][j]);
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

    public void colourTile(int i, int j, String actionType) {
        try {
            String name = GRASS_IMAGE;
            if (actionType.equals("moveSpeed")) {
                name = BLUE_GRASS_IMAGE;
            } else if (actionType.equals("attackRange")) {
                name = RED_GRASS_IMAGE;
            } else if (actionType.equals("summon")) {
                name = PINK_GRASS_IMAGE;
            }
            tileBtns[i][j].setIcon(new ImageIcon(this.getClass().getResource(name)));
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

    public void updateBar(Player player) {

        if (player instanceof RoyalePlayer) {
            removeSpawn(rebelButton);
            loadSpawn(royaleButton);
        } else {
            removeSpawn(royaleButton);
            loadSpawn(rebelButton);
        }
        playerName.setText(player.getName());
        playerType.setText(player.getFaction());
        playerCP.setText(Integer.toString(player.getCP()));
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

    public void setTime(String text) {
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

    public void disposeFrame() {
        getFrame().dispose();
    }

    public void setCursor(String name) {
        Image icon = new ImageIcon(this.getClass().getResource(name)).getImage();
        getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(ORIGINAL_ROW, ORIGINAL_COL), "name"));
    }

    public void resetCursor() {
        getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public int[] findButtonCoordinates(EventObject e) {
        Object source = e.getSource();

        if (e instanceof ActionEvent) {

            JButton tile = (JButton) source;
            return new int[]{(int) tile.getClientProperty("row"), (int) tile.getClientProperty("column")};
//        } else {
//            TODO: MODEL CAN'T HAVE PROPERTY
//            ButtonModel tileModel = (ButtonModel) source;
//            return new int[]{(int) tileModel.}
//        }
        }
        return null;
    }

    public String getTargetRed() {
        return TARGET_RED;
    }

    public String getTargetGreen() {
        return TARGET_GREEN;
    }

    public JButton getTile(int i, int j) {
        return tileBtns[i][j];
    }

    public void setTileName(int i, int j, String name) {
        tileBtns[i][j].setName(name);
    }

    public String getImagePath(String name) {
        return IMAGE_PATH + name + ".png";
    }
}


