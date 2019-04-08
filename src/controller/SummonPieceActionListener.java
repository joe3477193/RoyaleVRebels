package controller;

import static java.awt.Cursor.DEFAULT_CURSOR;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import model.*;
import view.GameFrameView;


// Clicking on a piece to summon
public class SummonPieceActionListener implements ActionListener{

	GameFrameView frame; 
	public boolean placing;

	public SummonPieceActionListener(GameFrameView frame){
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		JButton[] spawns= new JButton[]{frame.spawn_General, frame.spawn_Lieutenant, frame.spawn_Footman, frame.spawn_Cannon,
				frame.spawn_Archer, frame.spawn_Spearman
		};
		String[] image= new String[] {frame.ONE_IMAGE, frame.TWO_IMAGE, frame.THREE_IMAGE, frame.FOUR_IMAGE, frame.FIVE_IMAGE,
				frame.SIX_IMAGE
		};
		String[] name= new String[] {"General", "Liutenant", "Footman", "Cannon", "Archer", "Spearman"
		};

		for(int i=0;i<spawns.length;i++) {
			if(source==spawns[i]) {
				placing = true;
				Image icon = new ImageIcon(this.getClass().getResource(image[i])).getImage();
				if(frame.getFrame().getCursor().getName().equals(name[i])) {
					frame.getFrame().setCursor(DEFAULT_CURSOR);
				}
				else {
					frame.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), name[i]));
					System.out.println(source.getName());
				}
			}
		}
	}
}
