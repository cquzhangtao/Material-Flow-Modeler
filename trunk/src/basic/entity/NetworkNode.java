package basic.entity;

import java.util.List;



public interface NetworkNode extends IEntity{
	public List<NetworkNode> getSuccessors();
	public List<NetworkNode> getPredecessors();
	public void addSuccessor(NetworkNode successor);
	public void removeSuccessor(NetworkNode successor);
	public void addPredecessor(NetworkNode predecessor);
	public void removePredecessor(NetworkNode predecessor);
	public int indexOfPredecessor(NetworkNode predecessor);
	public int indexOfSuccessor(NetworkNode successor);
	public NetworkNode getSuccesor(int index);
	public NetworkNode getPredecessor(int index);
	public void removeAllPredecessors();
	public void removeAllSucessors();
}
