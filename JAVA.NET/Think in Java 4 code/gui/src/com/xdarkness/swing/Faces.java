package com.xdarkness.swing;

//: gui/Faces.java
// Icon behavior in JButtons.
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Faces extends JFrame {
	private static Icon[] faces;
	static {
		ClassLoader classLoader = Faces.class.getClassLoader();
		faces = new Icon[] {
				new ImageIcon(classLoader.getResource("Face0.gif")),
				new ImageIcon(classLoader.getResource("Face1.gif")),
				new ImageIcon(classLoader.getResource("Face2.gif")),
				new ImageIcon(classLoader.getResource("Face3.gif")),
				new ImageIcon(classLoader.getResource("Face4.gif")), };
	}
	private JButton jb, jb2 = new JButton("Disable");
	private boolean mad = false;

	public Faces() {
		jb = new JButton("JButton", faces[3]);
		setLayout(new FlowLayout());
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mad) {
					jb.setIcon(faces[3]);
					mad = false;
				} else {
					jb.setIcon(faces[0]);
					mad = true;
				}
				jb.setVerticalAlignment(JButton.TOP);
				jb.setHorizontalAlignment(JButton.LEFT);
			}
		});
		jb.setRolloverEnabled(true);
		jb.setRolloverIcon(faces[1]);
		jb.setPressedIcon(faces[2]);
		jb.setDisabledIcon(faces[4]);
		jb.setToolTipText("Yow!");
		add(jb);
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jb.isEnabled()) {
					jb.setEnabled(false);
					jb2.setText("Enable");
				} else {
					jb.setEnabled(true);
					jb2.setText("Disable");
				}
			}
		});
		add(jb2);
	}

	public static void main(String[] args) {
		XSwing.run(new Faces(), 250, 125);
	}
} // /:~
