package basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basic.entity.Entity;


public class DefaultNetwork<T extends DefaultNode<T>> extends Entity implements Network<T>{
	private Map<String,T> nodes;
	
	public DefaultNetwork(){
		nodes=new HashMap<String,T>();
	}

	@Override
	public List<T> getRoots() {
		List<T> roots=new ArrayList<T>();
		for(T node:nodes.values()){
			if(node.isRoot()){
				roots.add(node);
			}
		}
		return roots;
	}

	@Override
	public List<T> getLeaves() {
		List<T> leaves=new ArrayList<T>();
		for(T node:nodes.values()){
			if(node.isLeaf()){
				leaves.add(node);
			}
		}
		return leaves;
	}

	@Override
	public void addNode(T node) {
		// TODO Auto-generated method stub
		nodes.put(node.getUUID(), node);
	}

	@Override
	public void removeNode(T node) {
		// TODO Auto-generated method stub
		nodes.remove(node.getUUID());
		
	}

	@Override
	public int nodeCount() {
		// TODO Auto-generated method stub
		return nodes.size();
	}

	@Override
	public void addSide(T startNode, T endNode) {
		// TODO Auto-generated method stub
		startNode.addSuccessor(endNode);
		endNode.addPredecessor(startNode);
	}

	@Override
	public void removeSide(T startNode, T endNode) {
		// TODO Auto-generated method stub
		startNode.removeSuccessor(endNode);
		endNode.removePredecessor(startNode);
	}

	
}
