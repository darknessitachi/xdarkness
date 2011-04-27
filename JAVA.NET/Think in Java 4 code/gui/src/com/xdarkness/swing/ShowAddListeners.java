package com.xdarkness.swing;

// Display the "addXXXListener" methods of any Swing class.
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ShowAddListeners extends JFrame {
	private JTextField name = new JTextField(25);
	private JTextArea results = new JTextArea(40, 65);
	private static Pattern PAddListener = Pattern
			.compile("(add\\w+?Listener\\(.*?\\))");
	private static Pattern PQualifier = Pattern.compile("\\w+\\.");

	public ShowAddListeners() {
		NameL nameListener = new NameL();
		name.addActionListener(nameListener);
		
		JPanel top = new JPanel();
		top.add(new JLabel("Swing class name (press Enter):"));
		top.add(name);
		add(BorderLayout.NORTH, top);
		
		add(new JScrollPane(results));
		
		// Initial data and test:
		name.setText("JTextArea");
		nameListener.actionPerformed(new ActionEvent("", 0, ""));
	}

	class NameL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String nm = name.getText().trim();
			if (nm.length() == 0) {
				results.setText("No match");
				return;
			}
			Class<?> kind;
			try {
				kind = Class.forName("javax.swing." + nm);
			} catch (ClassNotFoundException ex) {
				results.setText("No match");
				return;
			}
			Method[] methods = kind.getMethods();
			results.setText("");
			for (Method m : methods) {
				Matcher matcher = PAddListener.matcher(m.toString());
				if (matcher.find()){
					results.append(PQualifier.matcher(matcher.group(1))
							.replaceAll("")
							+ "\n");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		XSwing.run(new ShowAddListeners(), 500, 400);
	}
} // /:~
