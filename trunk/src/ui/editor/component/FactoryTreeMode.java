package ui.editor.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import basic.entity.TreeNode;



import manu.factory.FactoryExplore;

public class FactoryTreeMode implements TreeModel{
	private FactoryExplore factoryExplore;
	private List<TreeModelListener> listeners;
	public FactoryTreeMode(FactoryExplore fx){
		factoryExplore=fx;
		listeners=new ArrayList<TreeModelListener>();
	}

	@Override
	public Object getRoot() {
		// TODO Auto-generated method stub
		return factoryExplore;
	}

	@Override
	public Object getChild(Object parent, int index) {
		// TODO Auto-generated method stub
		return ((TreeNode)parent).getChild(index);
	}

	@Override
	public int getChildCount(Object parent) {
		// TODO Auto-generated method stub
		return ((TreeNode)parent).getChildCount();
	}

	@Override
	public boolean isLeaf(Object node) {
		// TODO Auto-generated method stub
		return ((TreeNode)node).isLeaf();
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		TreeModelEvent event = new TreeModelEvent(newValue, path.getPath());
	    for (TreeModelListener listener : listeners) {
	        listener.treeStructureChanged(event);
	    }		
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		// TODO Auto-generated method stub
		return ((TreeNode)parent).getIndexofChild(child);
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		listeners.remove(l);
		
	}
	


}
