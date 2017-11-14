package manu.others;

import java.util.List;

import basic.entity.TreeNode;
import basic.entity.TreeNodeEntity;



public class Skills  extends TreeNodeEntity{
	
	
	public Skills(){
		super();
		this.setName("Skills");
		
			addChild(new Skill());
		
	}
	

}
