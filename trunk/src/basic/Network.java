package basic;

import java.util.List;

public interface Network <T> {
	public List<T> getRoots();
	public List<T> getLeaves();
	public void addNode(T node);
	public void removeNode(T node);
	public int nodeCount();
	public void addSide(T startNode,T endNode);
	public void removeSide(T startNode,T endNode);

}
