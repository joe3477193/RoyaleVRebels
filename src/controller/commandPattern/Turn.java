package controller.commandPattern;

import javax.swing.*;

public interface Turn {


    void executeTurn(String type, JButton[][] tileBtn,String image, int i, int j);
    
    void undoTurn();
}
