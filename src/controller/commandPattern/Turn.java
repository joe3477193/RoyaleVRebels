package controller.commandPattern;

import javax.swing.*;

public interface Turn {

    TurnType returnLastMove();

    void executeTurn(JButton[][] tileBtn,String image, int i, int j);
}
