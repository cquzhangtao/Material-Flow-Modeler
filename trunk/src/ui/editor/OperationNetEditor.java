package ui.editor;

import java.awt.BorderLayout;

import basic.entity.GraphicNetworkTreeNodeEntity;
import basic.entity.IEntity;
import basic.entity.NetworkNode;


import manu.factory.Factory;
import manu.factory.Resource;
import manu.factory.ResourceGroup;
import manu.factory.TransportationLine;
import manu.product.Operation;
import manu.product.Product;


import ui.editor.listener.EditorListener;
import ui.editor.listener.GraphicElementMouseListener;
import ui.editor.listener.GraphicSaveListener;
import ui.editor.network.Action;
import ui.editor.network.DefaultGraphicElement;
import ui.editor.network.GraphicElement;
import ui.editor.network.GraphicElements;
import ui.editor.network.GraphicPanelParameter;
import ui.editor.network.GraphicPanelParameters;
import ui.editor.network.GraphicalFrame;

public class OperationNetEditor extends ObjectEditor{
	
	private GraphicElements graphicElements;

	public OperationNetEditor(Product object){
		super(object);
		final Product operations=object;
		GraphicPanelParameters parameters=new GraphicPanelParameters();
		parameters.add( new GraphicPanelParameter("Operation",30,30,"operation.png",Action.ADD_NODE,Operation.class));
		this.setLayout(new BorderLayout());
		graphicElements=createGraphicElements(operations);
		GraphicalFrame graphicalFrame = new GraphicalFrame(graphicElements,parameters);
		this.add(graphicalFrame,BorderLayout.CENTER);
		graphicalFrame.addGraphicFrameSaveListener(new GraphicSaveListener(){

			@Override
			public void onSave() {
				saveToOperations(operations);
				for(EditorListener l:getEditorListener()){
					l.onChanged(null);
				}
				
			}});
		graphicalFrame.addGraphicElementMouseListener(new GraphicElementMouseListener(){

			@Override
			public void mouseClicked(Object obj) {
				for (EditorListener l : getEditorListener()) {
					l.onDoubleClicked(obj);
				}
				
			}
			@Override
			public void elementChanged(Object obj) {
				// TODO Auto-generated method stub
				saveToOperations(operations);
				for (EditorListener l : getEditorListener()) {
					l.onChanged(obj);
				}
			}});
			
		
		
		
	}
	public GraphicElements createGraphicElements(Product operations){
		GraphicElements graphicElements=new GraphicElements();
		graphicElements.setScale(operations.getLayoutScale());
		for(Operation operation:operations.getOperations()){
			DefaultGraphicElement element=new DefaultGraphicElement(operation);
			graphicElements.add(element);
		}
		for(Operation operation:operations.getOperations()){
			addLinks(graphicElements,operation);
		}
		return graphicElements;
	}
	
	public void addLinks(GraphicElements graphicElements,NetworkNode node){
		for(NetworkNode end:node.getSuccessors()){
			graphicElements.addLink(node.getUUID(), end.getUUID());
		}
	}
	public void saveToOperations(Product operations){
		operations.removeAllChild();
		operations.setLayoutScale(graphicElements.getScale());
		for(DefaultGraphicElement element:graphicElements.values()){
			if (element.getGraphicType() != GraphicElement.LINK) {
			GraphicNetworkTreeNodeEntity obj=element.getGraphicNetworkTreeNodeEntity();
			obj.removeAllPredecessors();
			obj.removeAllSucessors();
			element.addGraphicPropertyTo(obj);
			if(obj instanceof Operation){
				operations.addChild(obj);
			}
			}
		}
		for (DefaultGraphicElement element : graphicElements.values()) {
			if (element.getGraphicType() == GraphicElement.LINK) {
				NetworkNode n1=element.getPredecessors().get(0).getGraphicNetworkTreeNodeEntity();
				NetworkNode n2=element.getSuccessors().get(0).getGraphicNetworkTreeNodeEntity();
				n1.addSuccessor(n2);
				n2.addPredecessor(n1);
			}
		}
	}
	
	public static ObjectEditor getInstance(Product object){
		OperationNetEditor editor=(OperationNetEditor) hasInstance(object);
		if(editor==null){
			ObjectEditor ed=new OperationNetEditor(object);
			editors.add(ed);
			return ed;
		}
		return editor;
	}
	

}
