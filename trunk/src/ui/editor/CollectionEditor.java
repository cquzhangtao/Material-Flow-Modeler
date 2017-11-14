package ui.editor;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


import javax.swing.JSplitPane;

import org.jdesktop.swingx.JXTitledPanel;

import basic.entity.Entity;
import basic.entity.IEntity;
import basic.entity.TreeNode;

import ui.editor.listener.EditorListener;
import ui.editor.listener.GraphicElementMouseListener;
import ui.editor.listener.GraphicSaveListener;
import manu.factory.Factories;
import manu.factory.MaterialGroup;
import manu.factory.Materials;
import manu.factory.ResourceGroup;
import manu.factory.Resources;
import manu.factory.Storages;
import manu.factory.TransportationLines;
import manu.order.ManufactureOrders;
import manu.others.Properties;
import manu.others.Skills;
import manu.product.Operation;
import manu.product.Product;
import manu.product.Products;

public class CollectionEditor extends ObjectEditor{

	protected CollectionEditor(TreeNode object) {
		super(object);
		// TODO Auto-generated constructor stub
		this.setLayout(new BorderLayout());
		EntityEditor entityEditor=new EntityEditor((Entity) object);
		//OperationGanntChartPanel operationGanntChartPanel=new OperationGanntChartPanel(object);
		CollectionTableEditor resourcesTableEditor=new CollectionTableEditor(object);
		String title="";
		if(object instanceof Resources){
			 title = "Resource Groups";
		}else if(object instanceof Materials){
			 title = "Material Type Group";
		}else if(object instanceof MaterialGroup){
			title="Material Group";
		}else if(object instanceof Products){
			title="Products";
		}else if(object instanceof Skills){
			title="Resource Skills";
		}else if(object instanceof Properties){
			title="Materail Properties";
		}else if(object instanceof ManufactureOrders){
			title="Manufacture Orders";
		}else if(object instanceof Factories){
			title="Factories";
		}else if (object instanceof ResourceGroup) {
			title="Resources";
		}else if(object instanceof Storages){
			title="Storages";
		}else if(object instanceof TransportationLines){
			title="Transportation Lines";
		}
		JXTitledPanel p1=new JXTitledPanel("General");
		p1.add(entityEditor);
		JXTitledPanel p2=new JXTitledPanel(title);
		p2.add(resourcesTableEditor);
		final JSplitPane js=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		js.setTopComponent(p1);
		js.setBottomComponent(p2);
		js.setDividerSize(1);
		js.setDividerLocation(0.15);
		this.add(js,BorderLayout.CENTER);
		
	
		resourcesTableEditor.addGraphicSaveListener(new GraphicSaveListener(){

			@Override
			public void onSave() {
				// TODO Auto-generated method stub
				for (EditorListener l : getEditorListener()) {
					l.onChanged(null);
					
				}
			}});
		resourcesTableEditor.addGraphicElementMouseListener(new GraphicElementMouseListener(){

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
	

	public static ObjectEditor getInstance(Products object){
		return getInstance((TreeNode)object);
		
	}
	public static ObjectEditor getInstance(Skills object){
		return getInstance((TreeNode)object);
		
	}
	public static ObjectEditor getInstance(Properties object){
		return getInstance((TreeNode)object);
		
	}
	public static ObjectEditor getInstance(ManufactureOrders object){
		return getInstance((TreeNode)object);
		
	}
	public static ObjectEditor getInstance(Factories object){
		return getInstance((TreeNode)object);
		
	}
	
	public static ObjectEditor getInstance(Resources object){
		return getInstance((TreeNode)object);
	}
	public static ObjectEditor getInstance(Materials object){
		return getInstance((TreeNode)object);
		
	}
	public static ObjectEditor getInstance(MaterialGroup object){
		return getInstance((TreeNode)object);
	}
	
	public static ObjectEditor getInstance(ResourceGroup object){
		return getInstance((TreeNode)object);
	}
	public static ObjectEditor getInstance(Storages object){
		return getInstance((TreeNode)object);
		
	}
	public static ObjectEditor getInstance(TransportationLines object){
		return getInstance((TreeNode)object);
	}
	
	
	
	public static ObjectEditor getInstance(TreeNode object){
		CollectionEditor editor=(CollectionEditor) hasInstance(object);
		if(editor==null){
			ObjectEditor ed=new CollectionEditor(object);
			editors.add(ed);
			return ed;
		}
		return editor;
	}

}
