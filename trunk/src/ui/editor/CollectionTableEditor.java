package ui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXTitledPanel;

import basic.entity.IEntity;
import basic.entity.TreeNode;
import basic.volume.RandomVolume;
import basic.volume.TimeVolume;

import ui.editor.component.CheckBoxListModel;
import ui.editor.component.JComboCheckBox;
import ui.editor.listener.GraphicElementMouseListener;
import ui.editor.listener.GraphicSaveListener;
import ui.utilities.ImageUtilities;

import manu.factory.Factories;
import manu.factory.Material;
import manu.factory.MaterialGroup;
import manu.factory.Resource;
import manu.factory.ResourceGroup;
import manu.factory.Resources;
import manu.factory.Storage;
import manu.factory.Storages;
import manu.factory.TransportationLines;
import manu.order.ManufactureOrders;
import manu.others.Properties;
import manu.others.Property;
import manu.others.Skills;
import manu.product.Activity;
import manu.product.ActivityType;
import manu.product.InvovedMaterialIn;
import manu.product.ProcessingTime;
import manu.product.ProcessingTimeLogic;
import manu.product.Product;
import manu.product.Products;

public class CollectionTableEditor extends JPanel  {

	private TreeNode resources;
	JTable table;
	private MouseAdapter mouseListener;
	private TableCellRenderer renderer;
	private ArrayList<GraphicElementMouseListener> graphicListener;
	private ArrayList<GraphicSaveListener> graphicSaveListener;

	// Activity activity;

	public CollectionTableEditor(final TreeNode resources) {
		super();
		graphicListener=new ArrayList<GraphicElementMouseListener>();
		graphicSaveListener=new ArrayList<GraphicSaveListener>();
		this.resources = resources;
		this.setLayout(new BorderLayout());
		table = new JTable();
		table.setRowHeight(22);
		this.add(new JScrollPane(table), BorderLayout.CENTER);

		mouseListener = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				if (e.getClickCount() == 1) {
					if (!isLeaf1()) {
					if (row == table.getRowCount() - 1 && col == 0) {
						((MutableTableModel) table.getModel()).addNewRow();
					} else if (col == 0)
						((MutableTableModel) table.getModel()).removeRow(row);

					for(GraphicSaveListener l:graphicSaveListener){
						l.onSave();
					}
					}

				}
				if(e.getClickCount()==2){
					
					if(col==3){
						for(GraphicElementMouseListener lis:graphicListener){
							lis.mouseClicked(table.getValueAt(row, col));
						}
					}
				}

			}

		};

		renderer = new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				if (value == null){
					return new JLabel("");
				}

				if(column==0) {
				
					if (row < table.getRowCount() - 1) {
						JLabel label = new JLabel(new ImageIcon(
								ImageUtilities.resize(this.getClass()
										.getResource("delete1.png"), 12, 12)));
						if (isLeaf1()) {
							label.setEnabled(false);
						}

						return label;
					} else {
						JLabel label = new JLabel(new ImageIcon(
								ImageUtilities.resize(this.getClass()
										.getResource("add.png"), 12, 12)));
						if (isLeaf1()) {
							label.setEnabled(false);
						}
						return label;
					}

				}else if(column==3){
					if (isLeaf()) {
						return new JLabel("Enter",JLabel.CENTER);
					}
					return new JLabel(String.valueOf(((TreeNode)value).getChildCount()),JLabel.CENTER);
				}
				return new JLabel(value.toString(),JLabel.CENTER);
			}
		};
		
			table.addMouseListener(mouseListener);
		
		
		updateTable();

	}
	private boolean isLeaf(){
		return resources instanceof Skills||resources instanceof Properties||
				resources instanceof ManufactureOrders||resources instanceof Factories||
				resources instanceof Products||resources instanceof ResourceGroup||
				resources instanceof Storages||resources instanceof TransportationLines||resources instanceof MaterialGroup;
	}
	private boolean isLeaf1(){
		return resources instanceof ResourceGroup||
				resources instanceof Storages||resources instanceof TransportationLines;
	}

	private void updateTable() {

		table.setModel(new CollectionTableModel(resources));

		table.getColumnModel().getColumn(0).setMaxWidth(50);

		table.getColumnModel().getColumn(0).setCellRenderer(renderer);
		table.getColumnModel().getColumn(1).setCellRenderer(renderer);
		table.getColumnModel().getColumn(2).setCellRenderer(renderer);
		table.getColumnModel().getColumn(3).setCellRenderer(renderer);

	}
	public void addGraphicElementMouseListener(
			GraphicElementMouseListener graphicElementMouseListener) {
		graphicListener.add(graphicElementMouseListener);
		
	}
	public void addGraphicSaveListener(
			GraphicSaveListener graphicFrameSaveListener) {
		graphicSaveListener.add(graphicFrameSaveListener);
		
	}

}
