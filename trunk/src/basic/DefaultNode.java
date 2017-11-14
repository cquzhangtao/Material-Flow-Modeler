package basic;

import java.util.ArrayList;
import java.util.List;

import basic.entity.SpatialEntity;


public class DefaultNode<A> extends SpatialEntity implements Node<A>{

	private List<A> successors;
	private List<A> predecessors;
	
	public DefaultNode(){
		successors=new ArrayList<A>();
		predecessors=new ArrayList<A>();
	}
	
	@Override
	public List<A> getSuccessors() {
		return successors;
	}

	@Override
	public List<A> getPredecessors() {
		return predecessors;
	}

	@Override
	public boolean isRoot() {
		// TODO Auto-generated method stub
		return predecessors.size()==0;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return successors.size()==0;
	}

	@Override
	public void addSuccessor(A successor) {
		// TODO Auto-generated method stub
		successors.add(successor);
	}

	@Override
	public void removeSuccessor(A successor) {
		// TODO Auto-generated method stub
		successors.remove(successor);
	}

	@Override
	public void addPredecessor(A predecessor) {
		// TODO Auto-generated method stub
		predecessors.add(predecessor);
	}

	@Override
	public void removePredecessor(A predecessor) {
		// TODO Auto-generated method stub
		predecessors.remove(predecessor);
	}

	@Override
	public int indexOfPredecessor(A predecessor) {
		// TODO Auto-generated method stub
		return predecessors.indexOf(predecessor);
	}

	@Override
	public int indexOfSuccessor(A successor) {
		// TODO Auto-generated method stub
		return successors.indexOf(successor);
	}

	@Override
	public A getSuccesor(int index) {
		// TODO Auto-generated method stub
		return successors.get(index);
	}

	@Override
	public A getPredecessor(int index) {
		// TODO Auto-generated method stub
		return predecessors.get(index);
	}

}
