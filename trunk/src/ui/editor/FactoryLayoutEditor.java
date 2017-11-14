package ui.editor;

import java.awt.BorderLayout;

import basic.entity.Entity;
import basic.entity.GraphicNetworkTreeNodeEntity;
import basic.entity.IEntity;
import basic.entity.NetworkNode;


import manu.factory.Factory;
import manu.factory.Resource;
import manu.factory.ResourceGroup;
import manu.factory.TransportationLine;

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

public class FactoryLayoutEditor extends ObjectEditor {
	private GraphicElements graphicElements;
	private Factory factory;
	GraphicalFrame graphicalFrame ;

	public FactoryLayoutEditor(Factory object) {
		super(object);
		factory = object;
		GraphicPanelParameters parameters = new GraphicPanelParameters();
		parameters.add(new GraphicPanelParameter("Machine", 30, 30,
				"machine.png", Action.ADD_NODE, Resource.class));
		parameters.add(new GraphicPanelParameter("Worker", 10, 30,
				"worker.png", Action.ADD_NODE, Resource.class));
		parameters.add(new GraphicPanelParameter("Storage", 30, 30,
				"Storage.png", Action.ADD_NODE, manu.factory.Storage.class));
		parameters.add(new GraphicPanelParameter("Road", 6, 30, "Road.gif",
				Action.ADD_PATH, TransportationLine.class));
		this.setLayout(new BorderLayout());
		graphicElements = createGraphicElements(factory);
		graphicalFrame = new GraphicalFrame(graphicElements,
				parameters);
		this.add(graphicalFrame, BorderLayout.CENTER);
		graphicalFrame
				.addGraphicFrameSaveListener(new GraphicSaveListener() {

					@Override
					public void onSave() {
						saveToFactory();
						for (EditorListener l : getEditorListener()) {
							l.onChanged(null);
						}

					}
				});
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
				saveToFactory();
				for (EditorListener l : getEditorListener()) {
					l.onChanged(obj);
				}
			}});
	}

	public static ObjectEditor getInstance(Factory object) {
		FactoryLayoutEditor editor = (FactoryLayoutEditor) hasInstance(object);
		if (editor == null) {
			ObjectEditor ed = new FactoryLayoutEditor(object);
			editors.add(ed);
			return ed;
		}
		return editor;
	}

	public GraphicElements createGraphicElements(Factory factory) {
		GraphicElements graphicElements = new GraphicElements();
		graphicElements.setScale(factory.getLayoutScale());
		for (ResourceGroup rp : factory.getResources().getResourceGroups()) {
			for (Resource r : rp.getResources()) {
				DefaultGraphicElement element = new DefaultGraphicElement(r);
				graphicElements.add(element);
			}
		}
		for (manu.factory.Storage st : factory.getStorages().getStorages()) {
			DefaultGraphicElement element = new DefaultGraphicElement(st);
			graphicElements.add(element);
		}

		for (TransportationLine line : factory.getTransportationLines()
				.getTransportationLines()) {
			DefaultGraphicElement element = new DefaultGraphicElement(line);
			graphicElements.add(element);
		}
		for (ResourceGroup rp : factory.getResources().getResourceGroups()) {
			for (Resource r : rp.getResources()) {
				
					addLinks(graphicElements,r);
	
			}
		}
		for (manu.factory.Storage st : factory.getStorages().getStorages()) {
		
				addLinks(graphicElements,st);
			
		}

		for (TransportationLine line : factory.getTransportationLines()
				.getTransportationLines()) {
	
				addLinks(graphicElements,line);
		
		}

		return graphicElements;
	}
	public void addLinks(GraphicElements graphicElements,NetworkNode node){
		for(NetworkNode end:node.getSuccessors()){
			graphicElements.addLink(node.getUUID(), end.getUUID());
		}
	}

	public void saveToFactory() {
		factory.clear();
		factory.setLayoutScale(graphicElements.getScale());
		for (DefaultGraphicElement element : graphicElements.values()) {

			if (element.getGraphicType() != GraphicElement.LINK) {
				GraphicNetworkTreeNodeEntity obj = element
						.getGraphicNetworkTreeNodeEntity();
				obj.removeAllPredecessors();
				obj.removeAllSucessors();
				element.addGraphicPropertyTo(obj);
				if (obj instanceof Resource) {
					factory.addResource(obj.getParent(), (Resource) obj);
				} else if (obj instanceof manu.factory.Storage) {
					factory.addStorage((manu.factory.Storage) obj);
				} else if (obj instanceof TransportationLine) {
					factory.addTransportationLine((TransportationLine) obj);
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

}
