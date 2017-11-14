package ui.editor;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import basic.entity.IEntity;

import ui.editor.listener.EditorListener;



public class ObjectEditor extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IEntity object;
	protected static List<ObjectEditor> editors = new ArrayList<ObjectEditor>();
	private List<EditorListener> eidtorlisteners;

	protected ObjectEditor(IEntity object) {
		this.object = object;
		eidtorlisteners=new ArrayList<EditorListener>();

	}
	
	public void addEditorListener(EditorListener l){
		eidtorlisteners.add(l);
	}
	public List<EditorListener> getEditorListener(){
		return eidtorlisteners;
	}

	public static ObjectEditor getInstance(IEntity object) {
		ObjectEditor editor = hasInstance(object);
		if (editor == null) {
			ObjectEditor ed = new ObjectEditor(object);
			editors.add(ed);
			return ed;
		}
		return editor;
	}

	public void destoryInstance() {

		editors.remove(this);

	}

	protected static ObjectEditor hasInstance(IEntity object) {

		for (ObjectEditor ed : editors) {
			if (ed.object == object) {
				return ed;
			}
		}
		return null;
	}

	public String getName() {
		return object.getName();
	}

	public IEntity getObject() {
		return object;
	}

}
