package manu.product;

import java.util.ArrayList;
import java.util.List;

import basic.entity.GraphicNetworkTreeNodeEntity;
import basic.entity.TreeNode;

import manu.others.Skill;



public class Operation extends GraphicNetworkTreeNodeEntity{
	
	public Operation(){
		super();
	}
	
	public List<TreeNode> getActivities(){
		return getChildren();
	}



	public List<TreeNode> getPossibleSkills() {
		return getNodesWithClass(Skill.class);
	}
	

}
