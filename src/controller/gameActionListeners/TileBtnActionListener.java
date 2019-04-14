package controller.gameActionListeners;

import controller.GameController;
import net.sf.oval.constraint.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TileBtnActionListener implements ActionListener{

	private GameController c;

	public TileBtnActionListener(@NotNull GameController c) {
		this.c= c;
	}

	@Override
	public void actionPerformed(@NotNull ActionEvent e) {
		c.clickTile(e);
	}
}
