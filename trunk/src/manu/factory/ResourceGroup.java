package manu.factory;

import java.util.ArrayList;
import java.util.List;

import basic.entity.TreeNode;
import basic.entity.TreeNodeEntity;



public class ResourceGroup extends TreeNodeEntity{
	public static int count=0;
	public ResourceGroup(){
		super();
		setName("Resource Group"+(++count));
	}
	
	public List<Resource> getResources(){
		List<Resource> resources=new ArrayList<Resource>();
		for(TreeNode child: getChildren()){
			resources.add((Resource) child);
		}
		return resources;
	}

}
