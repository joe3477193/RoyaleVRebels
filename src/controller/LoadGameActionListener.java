package controller;

import app.Game;
import app.GameImpl;
import model.board.GameEngine;
import view.GameFrameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class LoadGameActionListener implements ActionListener {
    private JFrame frame;
    private JTextField name1, name2;

    public LoadGameActionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameEngine g;
        GameFrameView gfv;
        try {
            FileInputStream gameFile = new FileInputStream(new File("saveGame.obj"));
            ObjectInputStream gameObject = new ObjectInputStream(gameFile);
            g = (GameEngine) gameObject.readObject();

            FileInputStream viewFile = new FileInputStream(new File("saveView.obj"));
            ObjectInputStream viewObject = new ObjectInputStream(viewFile);
            gfv = (GameFrameView) viewObject.readObject();

            Game gameImpl = new GameImpl(gfv, g);
            frame.dispose();
        }
        catch (FileNotFoundException e1) {
            System.out.println("File not found");
        } catch (IOException e2) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e3) {
            e3.printStackTrace();
        }
    }
}
