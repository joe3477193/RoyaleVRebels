package controller;

import static java.awt.Cursor.DEFAULT_CURSOR;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import view.GameFrameView;


// Clicking on a piece to summon
public class SummonPieceActionListener implements ActionListener{
	
	JFrame frame; 
	public SummonPieceActionListener(JFrame frame){
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
        if (frame.getCursor().getType() != 0) {
        	frame.setCursor(DEFAULT_CURSOR);
            return;
        } else {
        	frame.setCursor(Toolkit.getDefaultToolkit().
                    createCustomCursor(new ImageIcon(this.getClass().getResource(GameFrameView.ONE_IMAGE)).getImage(),
                            new Point(0, 0), "general"));
            return;
        }
	}

}
