package xvy;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Game extends JFrame{
	
	private List<Player> players;
	private int playercount=0;
	private View v;
	private Controller c;
	private Grid g;
	
	Game() {
		players = new ArrayList<Player>();
		new StartPlayerInfo(new StartPlayerInfoView(this));
	}
	
	void addPlayer(String name) {
		players.add(new Player(playercount,name));
		playercount++;
	}
	
	void showBoard() {
		this.g = new Grid(players);
        this.v = new View(players);
        this.c = new Controller(v, g);
        c.generateBoard();
	}
	
	public static void main(String args[]) {
		new Game();
	}

}
