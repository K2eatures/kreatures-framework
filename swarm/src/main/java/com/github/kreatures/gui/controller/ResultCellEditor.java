/**
 * 
 */
package com.github.kreatures.gui.controller;

import java.awt.Color;
import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellEditor;

/**
 * @author Cedric Perez Donfack
 *
 */
public class ResultCellEditor extends DefaultCellEditor{// AbstractCellEditor implements TableCellEditor {

	private JTextArea txtArea;
	private JScrollPane scrollPanel;
	
	/**
	 *Default Ctor: 
	 */
	public ResultCellEditor() {
		super(new JCheckBox());
		txtArea=new JTextArea();
		scrollPanel=new JScrollPane(txtArea);
		txtArea.setEditable(true);
		txtArea.setWrapStyleWord(true);
//		txtArea.setCaretColor(new Color(255, 0, 0));
		txtArea.setBackground(new Color(0, 255, 0));
		txtArea.setLineWrap(true);		
	}

	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	@Override
	public Object getCellEditorValue() {
		
		return txtArea;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		txtArea.setText((String)value);
		
		return scrollPanel;
	}

}
