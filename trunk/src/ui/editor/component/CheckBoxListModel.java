package ui.editor.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class CheckBoxListModel<T> implements ListModel<T>{
	List<ListDataListener> listDataListeners;
	List<T> list;
	public CheckBoxListModel(List<T> list){
		this.list=list;
		listDataListeners=new ArrayList<ListDataListener>();
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public T getElementAt(int index) {
		// TODO Auto-generated method stub
		return list.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		listDataListeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		
		listDataListeners.remove(l);
		
	}

	
}
