package ui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import basic.entity.IEntity;


import manu.product.Activity;
import manu.product.ActivitiyStartLogic;
import ui.utilities.ImageUtilities;

public class ActivityStartConditionEditor  extends JPanel{

	protected ActivityStartConditionEditor(Activity object) {
		
		this.setLayout(new BorderLayout());
		
		final JTable conditionTable =new JTable(new ActivityConditionTableMode(object.getStartConditions()));
		conditionTable.setRowHeight(22);
		this.add(new JScrollPane(conditionTable),BorderLayout.CENTER);
		conditionTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		conditionTable.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==1){
					int row=conditionTable.rowAtPoint(e.getPoint());
					int col=conditionTable.columnAtPoint(e.getPoint());
					if(row==conditionTable.getRowCount()-1&&col==0){
						((ActivityConditionTableMode)conditionTable.getModel()).addNewRow();
					}else if(col==0)
						((ActivityConditionTableMode)conditionTable.getModel()).removeRow(row);
					
				}
				
			}

		});
		conditionTable.getColumnModel().getColumn(0).setMaxWidth(50);
		
		TableCellRenderer renderer = new TableCellRenderer(){

			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				// TODO Auto-generated method stub
				switch(column){
				case 0:
					if(row<table.getRowCount()-1)
					return new JLabel(new ImageIcon(ImageUtilities.resize(this.getClass().getResource("delete2.jpg"),12,12)));
					else
						return new JLabel(new ImageIcon(ImageUtilities.resize(this.getClass().getResource("add.png"),12,12)));
				case 1:
					if(value==null){
						return null;
					}
					return new JLabel(((IEntity)value).getName(),JLabel.CENTER);
				case 2:
					if(value==null){
						return null;
					}
					return new JLabel(((ActivitiyStartLogic)value).name(),JLabel.CENTER);
				case 3:
					if(value==null){
						return null;
					}
					return new JLabel(String.format("%.2f", (Double)value),JLabel.CENTER);
				}
				return null;
			}};
		
			conditionTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
			conditionTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
			conditionTable.getColumnModel().getColumn(2).setCellRenderer(renderer);
			conditionTable.getColumnModel().getColumn(3).setCellRenderer(renderer);
			Activity[] data = object.getBrothers().toArray(new Activity[0]);
			JComboBox<Activity> combo=new JComboBox<Activity>();
			combo.setModel(new DefaultComboBoxModel<Activity>(data));
			conditionTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(combo));
			combo.setRenderer(new ListCellRenderer< Activity>(){

				@Override
				public Component getListCellRendererComponent(
						JList<? extends Activity> list, Activity value,
						int index, boolean isSelected, boolean cellHasFocus) {
					if(value==null){
						return new JLabel("");
					}
					JLabel label=new JLabel(value.getName());
					label.setOpaque(true);
					if(isSelected){
						label.setBackground(Color.LIGHT_GRAY);
					}else{
						label.setBackground(Color.white);
					}
					return label;
				}});
			
			JComboBox<ActivitiyStartLogic> combo1 = new JComboBox<ActivitiyStartLogic>();
			combo1.setModel(new DefaultComboBoxModel<ActivitiyStartLogic>(ActivitiyStartLogic.values()));
			conditionTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(combo1));
	}
	


}
