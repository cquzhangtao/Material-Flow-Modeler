package manu.factory;

import basic.entity.TreeNodeEntity;
import manu.order.ManufactureOrder;
import manu.order.ManufactureOrders;
import manu.others.Others;
import manu.others.Property;
import manu.others.Skill;

import manu.product.Product;
import manu.product.Products;



public class FactoryExplore extends TreeNodeEntity {
	
	public FactoryExplore(){
		super();
		this.setName("Factory Explore");
		addChild(new Factories());
		addChild(new Products());
		addChild(new Materials());
		addChild(new ManufactureOrders());		
		addChild(new Others());
		setChangable(false);
		
	}
	public void addFactory(Factory factory){
		getChild(0).addChild(factory);
	}
	public void addProduct(Product product){
		getChild(1).addChild(product);
	}
	public void addOrder(ManufactureOrder manufactureOrder) {
		getChild(3).addChild(manufactureOrder);
		
	}
	public void addSkill(Skill skill){
		getChild(4).getChild(0).addChild(skill);
	}
	
	public void addProperty(Property property){
		getChild(4).getChild(1).addChild(property);
	}




}
