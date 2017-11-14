package manu.product;

import java.util.ArrayList;
import java.util.List;

import basic.entity.TreeNode;
import basic.entity.TreeNodeEntity;




public class Product extends TreeNodeEntity {
	private double layoutScale=1;
	private static int count=0;
	public Product(){
		super();
		this.setName("Product"+(++count));
	}
	
	public List<Operation>getOperations(){
		List<Operation>ops=new ArrayList<Operation>();
		
		for(TreeNode node:getChildren()){
			ops.add((Operation) node);
		}
		
		return ops;
	}

	public double getLayoutScale() {
		// TODO Auto-generated method stub
		return layoutScale;
	}
	public void setLayoutScale(double scale){
		layoutScale=scale;
	}
}
