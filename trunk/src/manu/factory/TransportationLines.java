package manu.factory;

import java.util.ArrayList;
import java.util.List;

import basic.entity.TreeNode;
import basic.entity.TreeNodeEntity;




public class TransportationLines extends TreeNodeEntity {
	public TransportationLines(){
		super();
		this.setName("Trans Lines");
		
	}
	public List<TransportationLine> getTransportationLines(){
		List<TransportationLine> transportationLines=new ArrayList<TransportationLine>();
		for(TreeNode child: getChildren()){
			transportationLines.add((TransportationLine) child);
		}
		return transportationLines;
	}
}
