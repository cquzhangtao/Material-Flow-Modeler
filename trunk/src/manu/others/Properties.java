package manu.others;

import basic.entity.TreeNodeEntity;
import basic.unit.UnitSystem;


public class Properties  extends TreeNodeEntity{
	
	public Properties(){
		super();
		this.setName("Properties");
		addChild(new Property(UnitSystem.Weight));
	}

}
