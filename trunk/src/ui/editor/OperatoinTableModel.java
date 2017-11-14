package ui.editor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


import basic.entity.TreeNode;
import basic.volume.QuatityVolume;

import manu.others.Skill;
import manu.product.Activity;
import manu.product.ActivityStartCondition;
import manu.product.ActivitiyStartLogic;
import manu.product.Operation;

public class OperatoinTableModel implements TableModel{
	
	List<TreeNode> activities;
	List<TableModelListener> listeners;
	Operation operation;
	public OperatoinTableModel (Operation operation){
		this.activities=operation.getChildren();
		this.operation=operation;
		this.listeners=new ArrayList<TableModelListener>();
	}
	public void addNewRow(){
		operation.addChild(new Activity());
		fireTableModelListener();
	}
	
	public void fireTableModelListener(){
		for(TableModelListener l:listeners){
			l.tableChanged(new TableModelEvent(this));
		}
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return activities.size()+1;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch(columnIndex){
		case 0:
			return " ";
		case 1:
			return "Activity";
		case 2:
			return "Skill";
		case 3:
			return "Amount";
		case 4:
			return " ";
		
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex){
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return Skill.class;
		case 3:
			return QuatityVolume.class;
		case 4:
			return Activity.class;
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(rowIndex==activities.size()){
			return false;
		}
		else if(columnIndex==0||columnIndex==4)
			return false;
		else 
			return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex==activities.size()){
			if(columnIndex==0)
				return " Add ... ";
			return null;
		}
		if(columnIndex==0){
			return "  -  ";
		}
		switch(columnIndex){
		case 1:
			return activities.get(rowIndex).getName();
		case 2:
			return ((Activity)activities.get(rowIndex)).getSkill();
		case 3:
			return ((Activity)activities.get(rowIndex)).getVolume();
		case 4:
			return activities.get(rowIndex);
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(rowIndex==activities.size()||columnIndex==0){
			return ;
		}
		switch(columnIndex){
		case 1:
			activities.get(rowIndex).setName((String) aValue);
			break;
		case 2:
			((Activity)activities.get(rowIndex)).setSkill((Skill) aValue);
			break;
		case 3:
			((Activity)activities.get(rowIndex)).setVolume( (QuatityVolume) aValue);
			break;
		case 4:
			//activities.get(rowIndex).setVolume( (IntegerVolume) aValue);
			
			break;
		}
		
		
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);
		
	}
	public void removeRow(int row) {
		activities.remove(row);
		fireTableModelListener();
		
	}


}
