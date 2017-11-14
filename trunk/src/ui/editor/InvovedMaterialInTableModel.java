package ui.editor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import basic.unit.IUnit;
import basic.volume.RandomVolume;
import basic.volume.TimeVolume;

import manu.factory.MaterialGroup;
import manu.factory.Storage;
import manu.others.Property;
import manu.product.Activity;
import manu.product.ActivityStartCondition;
import manu.product.ActivityType;
import manu.product.InvovedMaterialIn;
import manu.product.ProcessingTime;
import manu.product.ProcessingTimeElement;
import manu.product.ProcessingTimeLogic;

public class InvovedMaterialInTableModel implements TableModel ,MutableTableModel{
	private Activity activity;
	private List<InvovedMaterialIn> involvedMaterails;
	List<TableModelListener> listeners;

	public InvovedMaterialInTableModel(Activity activity) {
		this.activity = activity;
		this.listeners = new ArrayList<TableModelListener>();
		involvedMaterails = activity.getInvovedInMaterialIn();
		
	}

	public void addNewRow() {

			InvovedMaterialIn ele = new InvovedMaterialIn();
			involvedMaterails.add(ele);
		fireTableModelListener();
	}

	public void fireTableModelListener() {
		for (TableModelListener l : listeners) {
			l.tableChanged(new TableModelEvent(this));
		}
	}

	@Override
	public int getRowCount() {

		return involvedMaterails.size()+1;
	}

	@Override
	public int getColumnCount() {

		return 5;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return " ";
		case 1:
			return "Property";
		case 2:
			return "Group";
		case 3:
			return "Amount";
		case 4:
			return "Stroge";
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return Property.class;
		case 2:
			return List.class;
		case 3:
			return RandomVolume.class;
		case 4:
			return List.class;
		}
		return null;

	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0 || rowIndex == involvedMaterails.size()) {
			return false;
		}

		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex==involvedMaterails.size()){
			if(columnIndex==0)
				return " Add ... ";
			return null;
		}

			switch (columnIndex) {
			case 0:
				return "";
			case 1:
				return involvedMaterails.get(rowIndex).getProperty();
			case 2:
				return involvedMaterails.get(rowIndex).getGroups();
			case 3:
				return involvedMaterails.get(rowIndex).getAmount();
			case 4:
				return involvedMaterails.get(rowIndex).getPlaces();
			}
		
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(rowIndex==involvedMaterails.size()){
			return ;
		}
		switch (columnIndex) {
		
		case 1:
			 involvedMaterails.get(rowIndex).setProperty((Property) aValue);
			 break;
		case 2:
			involvedMaterails.get(rowIndex).setGroups((List<MaterialGroup>) aValue);
			break;
		case 3:
			 involvedMaterails.get(rowIndex).setAmount((RandomVolume<? extends IUnit>) aValue);
			 break;
		case 4:
			 involvedMaterails.get(rowIndex).setPlaces((List<Storage>) aValue);
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

			involvedMaterails.remove(row);
	
		fireTableModelListener();

	}

}
