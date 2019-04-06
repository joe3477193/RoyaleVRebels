package model;

import java.util.ArrayList;

import view.GameFrame;

public class PlayerInfo {

    ArrayList<Player> players = new ArrayList<Player>();

    public PlayerInfo(String name1, String name2) {
        Player p1 = new Player(0, name1);
        Player p2 = new Player(1, name2);
        players.add(p1);
        players.add(p2);
    }
    
    public ArrayList<Player> getAllPlayers() {
        return players;
    }
}
