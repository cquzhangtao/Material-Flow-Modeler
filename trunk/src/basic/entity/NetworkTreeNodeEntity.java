package basic.entity;

import java.util.ArrayList;
import java.util.List;

public class NetworkTreeNodeEntity extends TreeNodeEntity implements
		NetworkNode {
	private List<NetworkNode> predecessors;
	private List<NetworkNode> successors;

	protected NetworkTreeNodeEntity() {
		super();
		predecessors = new ArrayList<NetworkNode>();
		successors = new ArrayList<NetworkNode>();
	}

	@Override
	public Object clone() {
		NetworkTreeNodeEntity networkTreeNodeEntity = new NetworkTreeNodeEntity();
		clone(networkTreeNodeEntity);
		return networkTreeNodeEntity;
	}

	public void clone(NetworkTreeNodeEntity networkTreeNodeEntity) {
		networkTreeNodeEntity.predecessors = new ArrayList<NetworkNode>(
				predecessors);
		networkTreeNodeEntity.successors = new ArrayList<NetworkNode>(
				successors);
		super.clone(networkTreeNodeEntity);
	}

	@Override
	public List<NetworkNode> getSuccessors() {
		// TODO Auto-generated method stub
		return successors;
	}

	@Override
	public List<NetworkNode> getPredecessors() {

		return predecessors;
	}

	@Override
	public void addSuccessor(NetworkNode successor) {
		if (!successors.contains(successor)) {
			successors.add(successor);
		}
	}

	@Override
	public void removeSuccessor(NetworkNode successor) {
		// TODO Auto-generated method stub
		successors.remove(successor);
	}

	@Override
	public void addPredecessor(NetworkNode predecessor) {
		if (!predecessors.contains(predecessor)) {
			predecessors.add(predecessor);
		}
	}

	@Override
	public void removePredecessor(NetworkNode predecessor) {
		// TODO Auto-generated method stub
		predecessors.remove(predecessor);
	}

	@Override
	public int indexOfPredecessor(NetworkNode predecessor) {
		// TODO Auto-generated method stub
		return predecessors.indexOf(predecessor);
	}

	@Override
	public int indexOfSuccessor(NetworkNode successor) {
		// TODO Auto-generated method stub
		return successors.indexOf(successor);
	}

	@Override
	public NetworkNode getSuccesor(int index) {
		// TODO Auto-generated method stub
		return successors.get(index);
	}

	@Override
	public NetworkNode getPredecessor(int index) {
		// TODO Auto-generated method stub
		return predecessors.get(index);
	}

	@Override
	public void removeAllPredecessors() {
		// TODO Auto-generated method stub
		predecessors.clear();
	}

	@Override
	public void removeAllSucessors() {
		// TODO Auto-generated method stub
		
		successors.clear();
	}

}
