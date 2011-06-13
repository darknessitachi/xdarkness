package com.xdarkness.framework.js.doc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CodeCounter extends JFrame {
	private long codeLines = 0;
	private long commentLines = 0;
	private long blankLines = 0;
	private File fileName;

	private JLabel pathLabel;
	private JTextField pathField;
	private JButton openButton;
	private JPanel panelNorth, panelSouth;

	private ArrayList<File> list = new ArrayList<File>();
	private String[] tableHeader = { "File Name", "Code Lines",
			"Comment Lines", "Blank Lines" };
	private Vector<String> vectorHeader = new Vector<String>();
	private Vector<Vector> values = new Vector<Vector>();
	private JTable table;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;

	public CodeCounter() {
		super("Count Codes");

		pathLabel = new JLabel("File Path: ");
		pathField = new JTextField(100);
		pathField.setEditable(false);
		openButton = new JButton("Open");

		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				openFile();

				if (fileName == null)
					return;

				analysisFileOrDir(fileName);

				for (File file : list) {
					analyze(file);
				}
			}
		});
		panelNorth = new JPanel();

		panelNorth.setLayout(new BorderLayout());
		panelNorth.add(pathLabel, BorderLayout.WEST);
		panelNorth.add(pathField, BorderLayout.CENTER);
		panelNorth.add(openButton, BorderLayout.EAST);

		for (int i = 0; i < tableHeader.length; i++)
			vectorHeader.add(tableHeader[i]);

		tableModel = new DefaultTableModel(values, vectorHeader);

		table = new JTable(tableModel);

		scrollPane = new JScrollPane(table);

		panelSouth = new JPanel();
		panelSouth.setLayout(new BorderLayout());
		panelSouth.add(scrollPane, BorderLayout.CENTER);

		setLayout(new BorderLayout());
		add(panelNorth, BorderLayout.NORTH);
		add(panelSouth, BorderLayout.CENTER);

		setSize(450, 400);
		setVisible(true);
	}

	public static void main(String args[]) {
		CodeCounter countCodes = new CodeCounter();
		countCodes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void openFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.CANCEL_OPTION)
			return;

		fileName = fileChooser.getSelectedFile();

		if (fileName == null || fileName.getName().equals(""))
			JOptionPane.showMessageDialog(this, "Invalid File Name",
					"Invalid File Name", JOptionPane.ERROR_MESSAGE);

		pathField.setText(fileName.toString());
	}

	private void zeroVariables() {
		this.codeLines = 0;
		this.commentLines = 0;
		this.blankLines = 0;
	}

	private void analysisFileOrDir(File fileName) {
		if (fileName.isFile()) {
			list.add(fileName);
			return;
		} else {
			File[] fileArray = fileName.listFiles();

			for (File inArray : fileArray) {
				if (inArray.isFile() && inArray.getName().matches(".*\\.js$")) {
					list.add(inArray);
				} else if (inArray.isDirectory()) {
					analysisFileOrDir(inArray);
				}
			}
		}
	}

	private void analyze(File fileName) {
		Vector<String> value = new Vector<String>();

		String[] strArray = fileName.toString().split("\\\\");

		value.add(strArray[strArray.length - 1]);

		BufferedReader br = null;
		boolean comment = false;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line = "";
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.matches("^[\\s&&[^\\n]]*$")) {
					blankLines++;
				} else if (line.startsWith("/*") && !line.endsWith("*/")) {
					commentLines++;
					comment = true;
				} else if (line.startsWith("/*") && line.endsWith("*/")) {
					commentLines++;
				} else if (true == comment) {
					commentLines++;
					if (line.endsWith("*/")) {
						comment = false;
					}
				} else if (line.startsWith("//")) {
					commentLines++;
				} else {
					codeLines++;
				}
			}

			value.add(String.valueOf(codeLines));
			value.add(String.valueOf(commentLines));
			value.add(String.valueOf(blankLines));

			values.add(value);

			this.table.revalidate();

			zeroVariables();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
