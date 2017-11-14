package manu.factory;

import basic.entity.TreeNode;
import basic.entity.TreeNodeEntity;



public class Factory extends TreeNodeEntity {
	private static int count=0;
	private double layoutScale=1;
	public Factory(){
		super();
		this.setName("Factory"+(++count));

		addChild(new Resources());
		addChild(new Storages());
		addChild(new TransportationLines());
		setChangable(false);
	}
	public Resources getResources(){
		return (Resources) getChild(0);
	}
	public Storages getStorages(){
		return (Storages) getChild(1);
	}
	public TransportationLines getTransportationLines(){
		return (TransportationLines) getChild(2);
	}
	
	public void clear(){
		((Resources)getChild(0)).removeResourcesKeepGroup();
		getChild(1).removeAllChild();
		getChild(2).removeAllChild();
	}
	
	public void addResource(TreeNode group,Resource resource){
		((Resources) getChild(0)).addResource(group, resource);
	}
	public void addStorage(Storage stroage){
		((Storages) getChild(1)).addChild(stroage);
	}
	public void addTransportationLine(TransportationLine line){
		((TransportationLines) getChild(2)).addChild(line);
	}
	public double getLayoutScale() {
		return layoutScale;
	}
	public void setLayoutScale(double layoutScale) {
		this.layoutScale = layoutScale;
	}

}
