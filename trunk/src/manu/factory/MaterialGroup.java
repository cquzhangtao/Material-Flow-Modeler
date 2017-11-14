package manu.factory;

import basic.entity.TreeNodeEntity;

public class MaterialGroup extends TreeNodeEntity{
	public static int count=0;
	
	public MaterialGroup(){
		super();
		setName("MaterialGoup"+(++count));
		addChild(new Material());
	}
}
