package controller.gameMouseAdapters;

import controller.gameController.GameController;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverDeckMouseAdapter extends MouseAdapter {

    private GameController c;

    public HoverDeckMouseAdapter(GameController c) {
        this.c = c;
    }

    public void mouseEntered(MouseEvent e) {
        c.hoverDeck(e);
    }
}
