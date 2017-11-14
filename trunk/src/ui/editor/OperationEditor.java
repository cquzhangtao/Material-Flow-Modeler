package ui.editor;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JSplitPane;

import org.jdesktop.swingx.JXTitledPanel;

import basic.entity.IEntity;

import ui.editor.listener.EditorListener;
import ui.editor.listener.GraphicElementMouseListener;
import ui.editor.listener.GraphicSaveListener;
import manu.product.Operation;
import manu.product.Product;

public class OperationEditor extends ObjectEditor{

	protected OperationEditor(Operation object) {
		super(object);
		// TODO Auto-generated constructor stub
		this.setLayout(new BorderLayout());
		EntityEditor entityEditor=new EntityEditor(object);
		OperationGanntChartPanel operationGanntChartPanel=new OperationGanntChartPanel(object);
		
		JXTitledPanel p1=new JXTitledPanel("General");
		p1.add(entityEditor);
		JXTitledPanel p2=new JXTitledPanel("Activities");
		p2.add(operationGanntChartPanel);
		final JSplitPane js=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		js.setTopComponent(p1);
		js.setBottomComponent(p2);
		js.setDividerSize(1);
		js.setDividerLocation(0.15);
		this.add(js,BorderLayout.CENTER);
		
		
		//this.add(operationGanntChartPanel,BorderLayout.CENTER);
		operationGanntChartPanel.addGraphicSaveListener(new GraphicSaveListener(){

			@Override
			public void onSave() {
				// TODO Auto-generated method stub
				for (EditorListener l : getEditorListener()) {
					l.onChanged(null);
					
				}
			}});
		operationGanntChartPanel.addGraphicElementMouseListener(new GraphicElementMouseListener(){

			@Override
			public void mouseClicked(Object obj) {
				for (EditorListener l : getEditorListener()) {
					l.onDoubleClicked(obj);
					
				}
				
			}

			@Override
			public void elementChanged(Object obj) {
				// TODO Auto-generated method stub
				
			}});
		
		this.addComponentListener(new ComponentListener(){

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				js.setDividerLocation(0.15);
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				js.setDividerLocation(0.15);
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}});
	}
	
	
	
	
	
	
	
	
	public static ObjectEditor getInstance(Operation object){
		OperationEditor editor=(OperationEditor) hasInstance(object);
		if(editor==null){
			ObjectEditor ed=new OperationEditor(object);
			editors.add(ed);
			return ed;
		}
		return editor;
	}

}
