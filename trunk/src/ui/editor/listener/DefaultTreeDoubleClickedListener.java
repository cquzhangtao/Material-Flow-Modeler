package ui.editor.listener;

import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.swing.JTabbedPane;

import basic.entity.Entity;
import basic.entity.IEntity;
import basic.entity.TreeNode;


import ui.editor.ObjectEditor;
import ui.editor.component.FactoryTree;


public class DefaultTreeDoubleClickedListener implements TreeDoubleClickedListener {
	
	private Map<Class,Class> editors;
	JTabbedPane tabbedPane;
	FactoryTree tree;
	public DefaultTreeDoubleClickedListener(Map<Class,Class> editors,JTabbedPane tabbedPane,FactoryTree tree){
		this.editors=editors;
		this.tabbedPane=tabbedPane;
		this.tree=tree;
	}
	

	@Override
	public void onTreeDoubleClicked(final TreeNode obj) {
		// TODO Auto-generated method stub
		
		Class editorClass=editors.get(obj.getClass());
		ObjectEditor editor = null;
		if(editorClass==null){
			
			//editor=ObjectEditor.getInstance((IEntity) obj);
			return;
		}else{
			Method method;
			try {
				method = editorClass.getDeclaredMethod("getInstance", obj.getClass());
				editor=(ObjectEditor) method.invoke(null, obj);
				editor.addEditorListener(new EditorListener(){

					@Override
					public void onChanged(Object object) {
						// TODO Auto-generated method stub
						System.out.println(obj.getName());
						System.out.println(obj.getChildCount());
						//tree.getModel().valueForPathChanged(obj.getTreePath(), obj);
						if(object!=null){
						tree.setSelectionPath(((TreeNode)object).getTreePath().getParentPath());
						tree.expandPath(((TreeNode)object).getTreePath().getParentPath());
						}
						tree.updateUI();
					}

					@Override
					public void onDoubleClicked(Object obj) {
						// TODO Auto-generated method stub
						onTreeDoubleClicked((TreeNode) obj);
					}});
			} catch (NoSuchMethodException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		int index = tabbedPane.indexOfComponent(editor);
		if(index==-1){
			tabbedPane.addTab(editor.getName(), editor);
			tabbedPane.setSelectedComponent(editor);
		
			
		}else{
			tabbedPane.setSelectedIndex(index);
		}
	}

}
