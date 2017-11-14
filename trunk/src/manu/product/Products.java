package manu.product;

import basic.entity.TreeNodeEntity;


public class Products extends TreeNodeEntity {
	
	
	public Products(){
		super();
		this.setName("Products");
		addChild(new Product());
	}

	public void addProduct(Product product) {
		addChild(product);
	}

}
