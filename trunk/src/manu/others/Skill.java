package manu.others;

import basic.entity.TreeNodeEntity;

public class Skill extends TreeNodeEntity{
	private static int count=0;
	public Skill(){
		super();
		setName("Skill"+(++count));
	}
}
