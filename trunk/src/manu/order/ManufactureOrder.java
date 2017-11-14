package manu.order;

import basic.entity.TreeNodeEntity;


public class ManufactureOrder extends TreeNodeEntity{
	private static int count=0;
	
	public ManufactureOrder(){
		super();
		this.setName("Order"+(++count));
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return true;
	}



}
