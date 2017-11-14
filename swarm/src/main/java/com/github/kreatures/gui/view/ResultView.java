package com.github.kreatures.gui.view;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.github.kreatures.gui.controller.ResultCellEditor;
import com.github.kreatures.gui.controller.ResultCellRenderer;

import javax.swing.border.BevelBorder;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

public class ResultView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JRadioButton rdbtnAllIteration;
	private JRadioButton rdbtnUseIteration;
	private JPanel panelSd;
	private JButton btnClose;
	private JPanel panelWest;
	private JPanel panelEast;
	private JPanel panelCenter;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextArea txtAreaTest;
	
	/**
	 * Ctor: Default
	 * @throws HeadlessException
	 */
	public ResultView() throws HeadlessException {
		init();
	}

	/**
	 * Initialisaion of all components
	 */

	public void init() {
		
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelNd = new JPanel();
		FlowLayout fl_panelNd = (FlowLayout) panelNd.getLayout();
		fl_panelNd.setHgap(20);
		getContentPane().add(panelNd, BorderLayout.NORTH);

//		rdbtnAllIteration = new JRadioButton("All Iterations");
//		rdbtnAllIteration.setToolTipText("Show a tabelle with all iterations.");
//		panelNd.add(rdbtnAllIteration);

//		rdbtnUseIteration = new JRadioButton("Needed Iterations");
//		rdbtnUseIteration.setToolTipText("Show only iteration which corresponding to iterations in AbstractSwarm");
//		panelNd.add(rdbtnUseIteration);

		panelSd = new JPanel();
		getContentPane().add(panelSd, BorderLayout.SOUTH);

		btnClose = new JButton("close");
		panelSd.add(btnClose);

		panelWest = new JPanel();
		FlowLayout fl_panelWest = (FlowLayout) panelWest.getLayout();
		fl_panelWest.setVgap(20);
		getContentPane().add(panelWest, BorderLayout.WEST);
		
//		txtAreaTest = new JTextArea();
//		
//		
//		txtAreaTest.setText("ok\noui");
//		txtAreaTest.setSelectionColor(new Color(123, 104, 238));
//		txtAreaTest.setFont(new Font("Dialog", Font.PLAIN, 12));
//		txtAreaTest.setForeground(new Color(124, 252, 0));
//		txtAreaTest.setLineWrap(true);
//		panelWest.add(txtAreaTest);

		panelEast = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelEast.getLayout();
		flowLayout.setVgap(20);
		getContentPane().add(panelEast, BorderLayout.EAST);

		panelCenter = new JPanel();
		getContentPane().add(panelCenter, BorderLayout.CENTER);

		table = new JTable();
//		table.setPreferredSize(new Dimension(1000, 500));
		table.setPreferredScrollableViewportSize(new Dimension(850, 1000));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane = new JScrollPane(table);
		
		table.setFillsViewportHeight(true);
		panelCenter.add(scrollPane);
//		table.setCellEditor(new ResultCellEditor());
		table.setRowHeight(35);
		
		//table.setPreferredWidth(36);
		table.setDefaultRenderer(String.class, new ResultCellRenderer());//getColumnModel().getColumn(0).setCellRenderer(new ResultCellRenderer());
		table.setDefaultEditor(String.class, new ResultCellEditor());//DegetColumnModel().getColumn(0).setCellEditor(new ResultCellEditor());
		pack();
	}

	
	public ResultView(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	public void setTablemodel(TableModel tableModel,String simName) {
		this.setTitle(String.format("Results of the %s - KReatures simulation", simName));
		table.setModel(tableModel);
	}
}