package ui.editor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import basic.Distribution.DistributionParameter;
import basic.Distribution.DistributionParameterEnum;

import manu.product.Activity;
import manu.product.ActivityStartCondition;
import manu.product.ActivitiyStartLogic;

public class DistruibutionTableMode implements TableModel{
	
	List<DistributionParameter> parameterNames;
	Map<DistributionParameterEnum, DistributionParameter> parameters;
	List<TableModelListener> listeners;
	public DistruibutionTableMode (Map<DistributionParameterEnum, DistributionParameter> parameters){
		this.parameters=parameters;
		this.parameterNames=new ArrayList<DistributionParameter>( parameters.values());
		this.listeners=new ArrayList<TableModelListener>();
	}
	public void addNewRow(){
		DistributionParameter d = new DistributionParameter();
		List<DistributionParameterEnum> availabe=new  ArrayList<DistributionParameterEnum>();
		for(DistributionParameterEnum para:DistributionParameterEnum.values()){
			if(!parameters.keySet().contains(para)){
				availabe.add(para);
			}
		}
		if(availabe.size()==0)
			return;
		d.setName(availabe.get(0));
		parameterNames.add(d);
		parameters.put(d.getName(), d);
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
		return parameterNames.size()+1;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch(columnIndex){
		case 0:
			return " ";
		case 1:
			return "Parameter";
		case 2:
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
			return DistributionParameterEnum.class;
		case 2:
			return Double.class;
		
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex==0||rowIndex==parameterNames.size())
			return false;
		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex==parameterNames.size()){
			if(columnIndex==0)
				return " Add ... ";
			return null;
		}
		if(columnIndex==0){
			return "  -  ";
		}
		switch(columnIndex){
		case 1:
			return parameterNames.get(rowIndex).getName();
		case 2:
			return parameterNames.get(rowIndex).getValue();
		
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(rowIndex==parameterNames.size()||columnIndex==0||aValue==null){
			return ;
		}
		switch(columnIndex){

		case 1:
			if(parameters.containsKey(aValue)){
				
			}else{
			parameterNames.get(rowIndex).setName((DistributionParameterEnum)aValue) ;
			}

			break;
		case 2:
			parameterNames.get(rowIndex).setValue((Double) aValue);
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
		parameters.remove(parameterNames.get(row).getName());
		parameterNames.remove(row);
		
		fireTableModelListener();
		
	}

}
