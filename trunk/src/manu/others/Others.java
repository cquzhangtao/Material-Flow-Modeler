package manu.others;

import basic.entity.TreeNodeEntity;


public class Others extends TreeNodeEntity {

	
	public Others(){
		super();
		this.setName("Others");
		addChild(new Skills());
		addChild(new Properties());
		setChangable(false);
	}


}
