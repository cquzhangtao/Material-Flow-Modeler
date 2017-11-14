package ui.editor.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import basic.entity.IEntity;
import basic.entity.TreeNode;


import ui.editor.listener.TreeDoubleClickedListener;


public class FactoryTree extends JTree {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TreeDoubleClickedListener> listeners;
	public FactoryTree() {
		this.setModel(new FactoryTreeMode(null));
		this.setRowHeight(22);
		listeners=new ArrayList<TreeDoubleClickedListener>();
		MouseListener ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selRow = FactoryTree.this.getRowForLocation(e.getX(),
						e.getY());
				TreePath selPath = FactoryTree.this.getPathForLocation(
						e.getX(), e.getY());
				if (selRow != -1) {
					if (e.getClickCount() == 1) {
						// mySingleClick(selRow, selPath);
					} else if (e.getClickCount() == 2) {
						treeDoubleClicked(selPath.getLastPathComponent());
					}
				}
			}
		};
		this.addMouseListener(ml);
		this.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {

				// TODO Auto-generated method stub
				for (TreePath path : e.getPaths()) {
					Object obj = path.getLastPathComponent();
					obj = null;
				}
			}
		});

		this.setCellRenderer(new TreeCellRenderer() {

			@Override
			public Component getTreeCellRendererComponent(JTree tree,
					Object value, boolean selected, boolean expanded,
					boolean leaf, int row, boolean hasFocus) {
				String name = ((IEntity) value).getName();
				JLabel label = new JLabel(name);
				label.setOpaque(true);

				if (selected) {
					label.setBackground(Color.LIGHT_GRAY);
				} else {
					label.setBackground(Color.white);
				}
				return label;
			}
		});
	}

	private void treeDoubleClicked(Object node) {
		for(TreeDoubleClickedListener l:listeners){
			l.onTreeDoubleClicked((TreeNode) node);
		}
	}
	
	public void addTreeDoubleClickedListener(TreeDoubleClickedListener l){
		listeners.add(l);
	}
}
