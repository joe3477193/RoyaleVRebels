package controller.gameMouseAdapters;

import controller.gameController.GameController;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverTileMouseAdapter extends MouseAdapter {

    private GameController c;

    public HoverTileMouseAdapter(GameController c) {
        this.c = c;
    }

    public void mouseEntered(MouseEvent e) {
        c.hoverTile(e);
    }
}
