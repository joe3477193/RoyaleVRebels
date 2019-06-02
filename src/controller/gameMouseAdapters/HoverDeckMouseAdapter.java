package controller.gameMouseAdapters;

import com.google.java.contract.Invariant;
import controller.gameController.GameController;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Invariant("c != null")
public class HoverDeckMouseAdapter extends MouseAdapter {

    private GameController c;

    public HoverDeckMouseAdapter(GameController c) {
        this.c = c;
    }

    public void mouseEntered(MouseEvent e) {
        c.hoverDeck(e);
    }
}
