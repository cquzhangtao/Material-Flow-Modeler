package manu.product;

import basic.entity.TreeNodeEntity;

public class BOM extends TreeNodeEntity {
	
	public BOM(){
		super();
		this.setName("BOM");
	}


	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return true;
	}


}
