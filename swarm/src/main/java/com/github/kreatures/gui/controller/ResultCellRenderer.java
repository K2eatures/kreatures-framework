/**
 * 
 */
package com.github.kreatures.gui.controller;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

import com.github.kreatures.swarm.SwarmConst;
import com.github.kreatures.swarm.basic.MainAction;

/**
 * @author Cedric Perez Donfack
 *
 */
public class ResultCellRenderer extends JScrollPane implements TableCellRenderer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea txtArea;
	  
	   public ResultCellRenderer() {
		   txtArea = new JTextArea();
		   txtArea.setLineWrap(true);
		   txtArea.setWrapStyleWord(true);
//		   txtArea.setBorder(new TitledBorder("This is a JTextArea"));
	      getViewport().add(txtArea);
	   }
	  
	   @Override
	   public Component getTableCellRendererComponent(JTable table, Object value,
	                                  boolean isSelected, boolean hasFocus,
	                                  int row, int column){
	      if (isSelected) {
	         setForeground(table.getSelectionForeground());
	         setBackground(table.getSelectionBackground());
	         txtArea.setForeground(table.getSelectionForeground());
	         txtArea.setBackground(table.getSelectionBackground());
	      } else {
	         setForeground(table.getForeground());
	         setBackground(table.getBackground());
	         txtArea.setForeground(table.getForeground());
	         txtArea.setBackground(table.getBackground());
	      }
	  
	      String content=(String)value;
	      txtArea.setText(content);
	      if(content.contains(MainAction.ENTER_STATION.name())){
	    	  txtArea.setBackground(new Color(255, 255, 0));
	      }else if(content.contains(MainAction.LEAVE_STATION.name())) {
	    	  txtArea.setBackground(new Color(255, 255, 0));
	      }else if(content.contains(MainAction.WAIT.name())) {
	    	  txtArea.setBackground(new Color(255, 0, 0));
	      }else if(content.contains(MainAction.MOVE.name())) {
	    	  txtArea.setBackground(new Color(255, 255, 0));
	      }else if(content.length()>4){
	    	  txtArea.setBackground(new Color(0, 255, 0));
	      }
	    	  
	      txtArea.setCaretPosition(0);
	      return this;
	   }

}
