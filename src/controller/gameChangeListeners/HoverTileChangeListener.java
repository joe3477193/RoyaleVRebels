package controller.gameChangeListeners;

import controller.gameController.GameController;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HoverTileChangeListener implements ChangeListener {

    private GameController c;

    public HoverTileChangeListener(GameController c) {
        this.c = c;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        c.hoverTile(e);
    }
}
