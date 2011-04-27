package com.xdarkness.swing;

//: gui/SubmitSwingProgram.java
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.xdarkness.util.Countries;

public class SubmitSwingProgram extends JFrame {

	private static final long serialVersionUID = 3433899132299217555L;
	private JButton b = new JButton("Add Data"), c = new JButton("Clear Data");
	private JTextArea t = new JTextArea(20, 40);
	private Map<String, String> m = new HashMap<String, String>();

	private JLabel label = new JLabel("A Label");
	private JTextField txt = new JTextField(10);

	private ActionListener bl = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String name = ((JButton) e.getSource()).getText();
			txt.setText(name);
		}
	};

	public SubmitSwingProgram() {
		super("Hello Swing");

		setLayout(new FlowLayout());

		add(txt);

		add(label);

		// Use up all the data:
		m.putAll(Countries.capitals());
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Map.Entry me : m.entrySet())
					t.append(me.getKey() + ": " + me.getValue() + "\n");
			}
		});
		b.setCursor(new Cursor(Cursor.HAND_CURSOR));
		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.setText("");
			}
		});
		setLayout(new FlowLayout());
		add(new JScrollPane(t));
		add(b);
		add(c);
	}

	static SubmitSwingProgram ssp;

	public static void main(String[] args) throws Exception {

		ssp = new SubmitSwingProgram();

		XSwing.run(ssp, 475, 475);

		TimeUnit.SECONDS.sleep(1);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ssp.label.setText("Hey! This is Different!");
			}
		});
	}
} // /:~
