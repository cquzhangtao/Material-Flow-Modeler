package basic;

import java.util.List;

public interface Node<A> {
	
	public List<A> getSuccessors();
	public List<A> getPredecessors();
	public boolean isRoot();
	public boolean isLeaf();
	public void addSuccessor(A successor);
	public void removeSuccessor(A successor);
	public void addPredecessor(A predecessor);
	public void removePredecessor(A predecessor);
	public int indexOfPredecessor(A predecessor);
	public int indexOfSuccessor(A successor);
	public A getSuccesor(int index);
	public A getPredecessor(int index);

}
