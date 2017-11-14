package ui.editor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import basic.volume.TimeVolume;

import manu.product.ActivityStartCondition;
import manu.product.ProcessingTime;
import manu.product.ProcessingTimeElement;
import manu.product.ProcessingTimeLogic;

public class ProcessingTimeTableModel implements TableModel {
	private ProcessingTime processingTime;
	private List<ProcessingTimeElement> processingTimeElements;
	List<TableModelListener> listeners;

	public ProcessingTimeTableModel(ProcessingTime processingTime) {
		this.processingTime = processingTime;
		this.listeners = new ArrayList<TableModelListener>();
		if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resources_Independent) {
			// do nothing
		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resource_Dependent) {
			processingTimeElements = processingTime.getProcessingTimeMap();
		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Amount_Dependent) {
			processingTimeElements = processingTime
					.getProcessingTimeMapResAmount();

		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Proportion_Dependent) {
			processingTimeElements = processingTime
					.getProcessingTimeMapResAmountDirectProportion();
		} else {

		}
	}

	public void addNewRow() {
		if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resources_Independent) {
			// do nothing
		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resource_Dependent) {
			// do nothing
		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Amount_Dependent) {
			ProcessingTimeElement ele = new ProcessingTimeElement();
			processingTimeElements.add(ele);
		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Proportion_Dependent) {
			// do nothing
		} else {

		}

		fireTableModelListener();
	}

	public void fireTableModelListener() {
		for (TableModelListener l : listeners) {
			l.tableChanged(new TableModelEvent(this));
		}
	}

	@Override
	public int getRowCount() {
		
		if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resources_Independent) {
			return 0;
		}
		//else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.RESOURCE_DEPENDENT) {
//			return processingTimeElements.size();
//		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.RESOURCE_AMOUNT_DEPENDENT) {
//			return processingTimeElements.size() + 1;
//		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.RESOURCE_AMOUNT_DIRECT_PROPORTION_DEPENDENT) {
//			return processingTimeElements.size();
//		} else {
//
//		}
		//return 0;
		return processingTimeElements.size()+1;
	}

	@Override
	public int getColumnCount() {

		return 4;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return " ";
		case 1:
			return "Resource";
		case 2:
			return "Amount";
		case 3:
			return "Processing Time";
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return Resource.class;
		case 2:
			return Integer.class;
		case 3:
			return TimeVolume.class;
		}
		return null;

	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0 || rowIndex == processingTimeElements.size()) {
			return false;
		}
		if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resources_Independent) {
			return false;
		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resource_Dependent) {
			if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2) {
				return false;
			}
			return true;

		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Amount_Dependent) {
			if (columnIndex == 0 || rowIndex == processingTimeElements.size()) {
				return false;
			}
			return true;
		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Proportion_Dependent) {
			if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2) {
				return false;
			}
			return true;
		} else {

		}
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex==processingTimeElements.size()){
			if(columnIndex==0)
				return " Add ... ";
			return null;
		}
		if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resources_Independent) {
			return null;

		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resource_Dependent) {
			switch (columnIndex) {
			case 0:
				return "";
			case 1:
				return processingTimeElements.get(rowIndex).getResource();
			case 2:
				return 0;
			case 3:
				return processingTimeElements.get(rowIndex).getTime();
			}
		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Amount_Dependent) {
	
			switch (columnIndex) {
			case 0:
				return  "  -  ";
			case 1:
				return processingTimeElements.get(rowIndex).getResource();
			case 2:
				return processingTimeElements.get(rowIndex).getAmount();
			case 3:
				return processingTimeElements.get(rowIndex).getTime();
			}
		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Proportion_Dependent) {
			switch (columnIndex) {
			case 0:
				return "";
			case 1:
				return processingTimeElements.get(rowIndex).getResource();
			case 2:
				return 1;
			case 3:
				return processingTimeElements.get(rowIndex).getTime();
			}

		} else {

		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(rowIndex==processingTimeElements.size()){
			return ;
		}
		if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resources_Independent) {
			// nothing
		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resource_Dependent) {

			switch (columnIndex) {
			case 0:
				break;
			case 1:
				 //processingTimeElements.get(rowIndex).setResource((manu.factory.Resource) aValue);
				 break;
			case 2:
				//processingTimeElements.get(rowIndex).setAmount((Integer) aValue);
				break;
			case 3:
				processingTimeElements.get(rowIndex).setTime((TimeVolume) aValue);
			}

		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Amount_Dependent) {
	
			switch (columnIndex) {
			case 0:
				break;
			case 1:
				 processingTimeElements.get(rowIndex).setResource((manu.factory.Resource) aValue);
				 break;
			case 2:
				processingTimeElements.get(rowIndex).setAmount((Integer) aValue);
				break;
			case 3:
				processingTimeElements.get(rowIndex).setTime((TimeVolume) aValue);
				break;
			}
		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Proportion_Dependent) {
			switch (columnIndex) {
			case 0:
				break;
			case 1:
				// processingTimeElements.get(rowIndex).setResource((manu.factory.Resource) aValue);
				 break;
			case 2:
				//processingTimeElements.get(rowIndex).setAmount((Integer) aValue);
				break;
			case 3:
				processingTimeElements.get(rowIndex).setTime((TimeVolume) aValue);
				break;
			}
		} else {

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
		if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resources_Independent) {
			return;
		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resource_Dependent) {
			return;
		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Amount_Dependent) {
			processingTimeElements.remove(row);
		} else if (processingTime.getProcessingTimeLogic() == ProcessingTimeLogic.Proportion_Dependent) {
			return;
		} else {
			return;
		}
		fireTableModelListener();

	}

}
