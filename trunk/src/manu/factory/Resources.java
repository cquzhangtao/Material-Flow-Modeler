package manu.factory;

import java.util.ArrayList;
import java.util.List;

import basic.entity.TreeNode;
import basic.entity.TreeNodeEntity;




public class Resources extends TreeNodeEntity{
	public Resources(){
		super();
		this.setName("Resources");
		addChild(new ResourceGroup());
		
	}
	
	public List<ResourceGroup> getResourceGroups(){
		List<ResourceGroup> resourceGroups=new ArrayList<ResourceGroup>();
		for(TreeNode child: getChildren()){
			resourceGroups.add((ResourceGroup) child);
		}
		return resourceGroups;
	}
	
	public void removeResourcesKeepGroup(){
		for(TreeNode child: getChildren()){
			child.removeAllChild();;
		}
	}
	
	
	public void addResource(TreeNode group,Resource resource){
		if(this.getIndexofChild(group)==-1){
			if(this.getChildCount()==0){
				this.addChild(new ResourceGroup());
			}
			this.getChild(0).addChild(resource);
		}else{
			group.addChild(resource);
		}
	}
	

}
