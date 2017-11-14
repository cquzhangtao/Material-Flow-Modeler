package manu.factory;

import java.util.ArrayList;
import java.util.List;

import manu.others.Skill;

import basic.entity.GraphicNetworkTreeNodeEntity;

public class Resource extends GraphicNetworkTreeNodeEntity{
	private List<Skill>skills;
	
	public Resource(){
		super();
		skills=new ArrayList<Skill>();
		
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
}
