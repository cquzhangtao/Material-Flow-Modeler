package manu.factory;

import java.util.ArrayList;
import java.util.List;

import basic.entity.TreeNode;
import basic.entity.TreeNodeEntity;




public class Storages extends TreeNodeEntity{
	
	public Storages(){
		super();
		this.setName("Storages");
	}
	
	public List<Storage> getStorages(){
		List<Storage> storages=new ArrayList<Storage>();
		for(TreeNode child: getChildren()){
			storages.add((Storage) child);
		}
		return storages;
	}

}
