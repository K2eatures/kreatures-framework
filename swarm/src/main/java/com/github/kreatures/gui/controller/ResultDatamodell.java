package com.github.kreatures.gui.controller;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.github.kreatures.gui.modell.ResultData;

public class ResultDatamodell extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8068946709804196583L;
	/**
	 * List of rows 
	 */
	private List<List<String>> rows;
	/**
	 * List of column names
	 */
	private List<String> columnNames;
	
//	/**
//	 * Default Ctor of the TableModel.
//	 * Its creates a new column name and new data object without elements 
//	 */
//	public ResultDatamodell() {
//		this(new ArrayList<>(),new ArrayList<>());
//	}

	/**
	 * Ctor: takes new column name and data
	 * @param columnNames list of column name
	 * @param rows list of rows
	 */
	public ResultDatamodell(ResultData resultData) {
		this.rows=resultData.getRowsData();
		this.columnNames=resultData.getColNames();
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<List<String>> rows) {
		this.rows = rows;
	}

	/**
	 * @param columnNames the columnNames to set
	 */
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	@Override
	public int getRowCount() {

		return rows.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public String getColumnName(int col) {
        return columnNames.get(col);
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		List<String> row=rows.get(rowIndex);
		return row==null?"":row.get(columnIndex);
	}

	@Override
	public boolean isCellEditable(int row, int col){
		return false; 
	}
	 /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    @SuppressWarnings("unchecked")
	@Override
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
	
	@Override
	public void setValueAt(Object value, int rowIndex, int colIndex) {
		List<String>row= rows.get(rowIndex);
		row.get(colIndex);
		fireTableCellUpdated(rowIndex, colIndex);
	}

}
