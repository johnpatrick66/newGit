package myButt;

import javax.swing.*;
import java.awt.*;

public class myJButton {
	
	
	public static void main(String[] args) {
		JFrame f=new JFrame();
		f.setTitle("myJButton");
		f.setVisible(true);
		f.setSize(200,200);
		Container c=f.getContentPane();
		c.setLayout(null);
		
		JButton btn=new JButton("click me");
		btn.setBounds(10,10,150,50);
		Cursor cur=new Cursor (Cursor.HAND_CURSOR);
		btn.setCursor(cur);
		f.add(btn);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
