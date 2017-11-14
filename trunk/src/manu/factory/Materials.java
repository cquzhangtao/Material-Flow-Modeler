package manu.factory;

import basic.entity.TreeNodeEntity;


public class Materials extends TreeNodeEntity {
	
	public Materials(){
		super();
		this.setName("Materials");
		addChild(new MaterialGroup());
	
	}

}
