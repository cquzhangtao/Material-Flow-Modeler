package ui.editor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import manu.product.Activity;
import manu.product.ActivityStartCondition;
import manu.product.ActivitiyStartLogic;


public class ActivityConditionTableMode implements TableModel{
	
	List<ActivityStartCondition> condtions;
	List<TableModelListener> listeners;
	public ActivityConditionTableMode (List<ActivityStartCondition> conditions){
		this.condtions=conditions;
		this.listeners=new ArrayList<TableModelListener>();
	}
	public void addNewRow(){
		condtions.add(new ActivityStartCondition());
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
		return condtions.size()+1;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch(columnIndex){
		case 0:
			return " ";
		case 1:
			return "Related Activity";
		case 2:
			return "Start Logic";
		case 3:
			return "Value";
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex){
		case 0:
			return String.class;
		case 1:
			return Activity.class;
		case 2:
			return ActivitiyStartLogic.class;
		case 3:
			return Double.class;
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(rowIndex==condtions.size()){
			return false;
		}
		else if(columnIndex==0||columnIndex==3&&getValueAt(rowIndex,2)==ActivitiyStartLogic.START_WITH)
			return false;
		else 
			return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex==condtions.size()){
			if(columnIndex==0)
				return " Add ... ";
			return null;
		}
		if(columnIndex==0){
			return "  -  ";
		}
		switch(columnIndex){
		case 1:
			return condtions.get(rowIndex).getRelatedActivty();
		case 2:
			return condtions.get(rowIndex).getStartLogic();
		case 3:
			return condtions.get(rowIndex).getValue();
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(rowIndex==condtions.size()||columnIndex==0){
			return ;
		}
		switch(columnIndex){
		case 1:
			condtions.get(rowIndex).setRelatedActivty((Activity) aValue);
			break;
		case 2:
			condtions.get(rowIndex).setStartLogic((ActivitiyStartLogic) aValue);
			break;
		case 3:
			condtions.get(rowIndex).setValue((Double) aValue);
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
		condtions.remove(row);
		fireTableModelListener();
		
	}

}
