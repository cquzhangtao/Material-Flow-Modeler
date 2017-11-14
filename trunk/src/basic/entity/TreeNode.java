package basic.entity;

import java.util.List;

import javax.swing.tree.TreePath;



public interface TreeNode extends IEntity{
	public TreeNode getChild(int index);
	public int getChildCount();
	public boolean isLeaf();
	public int getIndexofChild(Object child);
	public TreeNode getParent();
	public void setParent(TreeNode parent);
	public void addChild(TreeNode child);
	public void removeChild(TreeNode child);
	public boolean isChangable();
	public void setChangable(boolean value);
	public void removeAllChild();
	public TreePath getTreePath();
	public List<TreeNode> getNodesWithClass(Class className);
	public TreeNode getRoot();
	public List<TreeNode> getChildren();
	public List<TreeNode> find(Class className);
	public List<TreeNode> getBrothers();
}
