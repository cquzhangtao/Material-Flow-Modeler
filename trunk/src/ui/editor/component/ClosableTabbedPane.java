package ui.editor.component;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JTabbedPane;

import ui.editor.listener.TabbedPaneChangedListener;

public class ClosableTabbedPane extends JTabbedPane{
	 java.util.List<TabbedPaneChangedListener> listeners=new ArrayList<TabbedPaneChangedListener> ();
	@Override
	public void addTab(String title, Component component){
		super.addTab(title,component);
		int index=this.indexOfComponent(component);
		ButtonTabComponent bt = new ButtonTabComponent(this);
	       for(TabbedPaneChangedListener l:listeners){
           	bt.addTabbedPaneChangedListener(l);
           }
		this.setTabComponentAt(index,bt);
	}
	
	  public void addTabbedPaneChangedListener(TabbedPaneChangedListener l){
	    	listeners.add(l);
	    }

}
