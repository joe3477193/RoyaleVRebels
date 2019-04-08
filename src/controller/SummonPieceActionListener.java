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
	private String type;
	public SummonPieceActionListener(GameFrameView frame, String type){
		this.frame = frame;
		this.type = type;
	}

	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		JButton[] spawns= new JButton[]{frame.spawn_General, frame.spawn_Lieutenant, frame.spawn_Spearman, frame.spawn_Footman,
				frame.spawn_Archer, frame.spawn_Cannon
		};
		String[] Royalimage= new String[] {frame.RO_ONE_IMAGE, frame.RO_TWO_IMAGE, frame.RO_THREE_IMAGE, frame.RO_FOUR_IMAGE, frame.RO_FIVE_IMAGE,
				frame.RO_SIX_IMAGE
		};
		String[] Rebelimage= new String[] {frame.RE_ONE_IMAGE, frame.RE_TWO_IMAGE, frame.RE_THREE_IMAGE, frame.RE_FOUR_IMAGE, frame.RE_FIVE_IMAGE,
				frame.RE_SIX_IMAGE
		};
		String[] Royalname= new String[] {"General", "Liutenant", "Footman", "Cannon", "Archer", "Spearman"
		};
		String[] Rebelname= new String[] {"Rascal", "Scoundrel", "Hobo", "Scum", "Pegger", "Poker"
		};
		
		for(int i=0;i<spawns.length;i++) {
			if(source==spawns[i]) {
				placing = true;
				if(type == "rebel") {
					Image icon = new ImageIcon(this.getClass().getResource(Rebelimage[i])).getImage();
					if(frame.getFrame().getCursor().getName().equals(Rebelname[i])) {
						frame.getFrame().setCursor(DEFAULT_CURSOR);
					}
					else {
						frame.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), Rebelname[i]));
						System.out.println(source.getName());
					}
				}
				else {
					Image icon = new ImageIcon(this.getClass().getResource(Royalimage[i])).getImage();
					if(frame.getFrame().getCursor().getName().equals(Royalname[i])) {
						frame.getFrame().setCursor(DEFAULT_CURSOR);
					}
					else {
						frame.getFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0, 0), Royalname[i]));
						System.out.println(source.getName());
					}
					
				}

			}
		}
	}
}
