package controller.gameMouseAdapters;

import com.google.java.contract.Invariant;
import controller.gameController.GameController;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Invariant("c != null")
public class HoverTileMouseAdapter extends MouseAdapter {

    private GameController c;

    public HoverTileMouseAdapter(GameController c) {
        this.c = c;
    }

    public void mouseEntered(MouseEvent e) {
        c.hoverTile(e);
    }
}
