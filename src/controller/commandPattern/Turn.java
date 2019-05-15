package controller.commandPattern;

import javax.swing.*;

public interface Turn {

    TurnType returnLastMove();

    void executeTurn(JButton[][] tileBtn, int i, int j);

}
