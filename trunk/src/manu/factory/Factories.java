package manu.factory;

import basic.entity.TreeNodeEntity;


public class Factories extends TreeNodeEntity{
	
	public Factories(){
		super();
		this.setName("Factories");
		addChild(new Factory());
		
	}
	
}
