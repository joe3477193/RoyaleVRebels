package xvy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class View extends JFrame {
	
	private JFrame frame;
	private List<Player> players;
    public static final int BOARD_ROWS = 8;
    public static final int BOARD_COLS = 12;
    private JPanel gridPanel;
    private JPanel selectPanel;
    private JLabel healthLabel;
    private JLabel combatPlabel;
    private static JButton[][] btn;
    private JButton item1;
    private JButton item2;
    private JButton item3;
    private JButton item4;
 
    protected static final String GRASS_IMAGE      = "images/grass.png";
    
   
    
	View(List<Player> players){
		this.players = players;
        frame = new JFrame("Royals vs Rebels");

        assembleBoard();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);      //show gui in the middle of screen
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	
	private void assembleBoard(){
		
		
        JPanel statsPanel = new JPanel(new GridLayout(1,6,0,0));
        selectPanel = new JPanel(new GridLayout(1,5,0,0));
        gridPanel = new JPanel(new GridLayout(BOARD_ROWS,BOARD_COLS,0,1));
        
        healthLabel = new JLabel("0");
        combatPlabel = new JLabel("0");
        statsPanel.add(new JLabel("Player Name: "));
        statsPanel.add(new JLabel(players.get(0).getName()));
        statsPanel.add(new JLabel("HP: "));
        statsPanel.add(healthLabel);
        statsPanel.add(new JLabel("CP: "));
        statsPanel.add(combatPlabel);
        
        btn = new JButton[BOARD_ROWS][BOARD_COLS];
        
        selectPanel.setMaximumSize(new Dimension(100,100));

        frame.setSize(925, 600);
        paintGrid();

        JPanel main = new JPanel(new  BorderLayout());
        main.add(statsPanel, BorderLayout.NORTH);
        main.add(selectPanel, BorderLayout.CENTER);
        
        frame.revalidate();
        frame.repaint();
      
        frame.add(main, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
	}

	private void paintGrid() {
        item1 = new JButton();
        item1.setName("item1");
        item1.addActionListener(new Controller(this));
        selectPanel.add(item1);

        item2 = new JButton();
        item2.setName("item2");
        item2.addActionListener(new Controller(this));
        selectPanel.add(item2);

        item3 = new JButton();
        item3.setName("item3");
        item3.addActionListener(new Controller(this));
        selectPanel.add(item3);

        item4 = new JButton();
        item4.setName("item4");
        item4.addActionListener(new Controller(this));
        selectPanel.add(item4);
        
        generateBoard();
		
	}
	
    public void generateBoard() {
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {

                btn[i][j] = new JButton();
                btn[i][j].setIcon(new ImageIcon(this.getClass().getResource(GRASS_IMAGE)));
                btn[i][j].putClientProperty("row", i);             
                btn[i][j].putClientProperty("column", j);          
                btn[i][j].addActionListener(new Controller(this));

                gridPanel.add(btn[i][j]);
            }
        }
    }
    
    public void linkModelView(ArrayList<GridRows> gr) {

        for (int i = 0; i < BOARD_ROWS; i++) {
            ArrayList<GridElement> ge = gr.get(i).getRow();
            for (int j = 0; j < BOARD_COLS; j++) {
               
            }
        }
    }
	
}
