package basic.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.tree.TreePath;



public class TreeNodeEntity extends Entity implements TreeNode{
	private TreeNode parent;
	private List<TreeNode> children;
	private boolean changable;
	
	protected TreeNodeEntity(){
		super();
		changable=true;
		children=new ArrayList<TreeNode>();
	}
	
	@Override
	public Object clone(){
		TreeNodeEntity treeNodeEntity=new TreeNodeEntity();
		clone(treeNodeEntity);
		return treeNodeEntity;
	}
	

	public void clone(TreeNodeEntity treeNodeEntity){
		treeNodeEntity.parent=parent;
		parent.addChild(treeNodeEntity);
		for(TreeNode c:children){
			treeNodeEntity.children.add((TreeNode) ((TreeNode) c).clone());
		}
		treeNodeEntity.changable=changable;
		super.clone(treeNodeEntity);
	}
	
	public List<TreeNode> getBrothers(){
		List<TreeNode> nodes=new ArrayList<TreeNode>(getParent().getChildren());
		nodes.remove(this);
		return nodes;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	@Override
	public void addChild(TreeNode child) {
		children.add(child);
		child.setParent(this);
		
	}

	@Override
	public void removeChild(TreeNode child) {
		children.remove(child);
		
	}
	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return children.size()==0;
		//return false;
	}
	@Override
	public TreePath getTreePath() {
	    List<TreeNode> path = new LinkedList<TreeNode>();
	    for (TreeNode node = this; node != null; node = node.getParent()) {
	        path.add(0, node);
	    }
	    TreePath treePath = new TreePath(path.toArray());
	    return treePath;
	}

	@Override
	public TreeNode getChild(int index) {
		// TODO Auto-generated method stub
		return children.get(index);
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return children.size();
	}

	@Override
	public int getIndexofChild(Object child) {
		// TODO Auto-generated method stub
		return children.indexOf(child);
	}

	@Override
	public boolean isChangable() {
		// TODO Auto-generated method stub
		return changable;
	}

	@Override
	public void setChangable(boolean value) {
		// TODO Auto-generated method stub
		this.changable=value;
	}

	@Override
	public void removeAllChild() {
		// TODO Auto-generated method stub
		children.clear();
	}

	@Override
	public List<TreeNode> getNodesWithClass(Class className) {
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		TreeNode root=getRoot();
		nodes.addAll(root.find(className));
		
		return nodes;
	}
	public List<TreeNode> find(Class className){
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		for(TreeNode node:this.getChildren()){
			if(node.getClass()==className){
				nodes.add(node);
			}
			nodes.addAll(node.find(className));
		}
		return nodes;
	}

	@Override
	public TreeNode getRoot() {
		for(TreeNode node = this;node!=null;node=node.getParent()){
			if(node.getParent()==null){
				return node;
			}
		}
		return null;
	}

	@Override
	public List<TreeNode> getChildren() {
		// TODO Auto-generated method stub
		return children;
	}

}
