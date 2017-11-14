package ui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import ui.editor.listener.GraphicElementMouseListener;
import ui.editor.listener.GraphicSaveListener;
import ui.utilities.ImageUtilities;
import basic.Distribution.DistributionParameterEnum;
import basic.entity.IEntity;
import basic.volume.QuatityVolume;

import manu.others.Skill;
import manu.others.Skills;
import manu.product.Activity;
import manu.product.Operation;

public class OperationGanntChartPanel extends JPanel{
	private List<GraphicElementMouseListener> graphicListener;
	private List<GraphicSaveListener> graphicSaveListener;
	public OperationGanntChartPanel(final Operation operation){
		this.setLayout(new BorderLayout());
		graphicListener=new ArrayList<GraphicElementMouseListener>();
		graphicSaveListener=new ArrayList<GraphicSaveListener>();
		final JTable conditionTable=new JTable();
		conditionTable.setModel(new OperatoinTableModel(operation));
		this.add(new JScrollPane(conditionTable));
		
		conditionTable.setRowHeight(25);
		conditionTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		conditionTable.setDefaultRenderer(String.class, centerRenderer);
		conditionTable.setDefaultRenderer(Double.class, centerRenderer);
		conditionTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = conditionTable.rowAtPoint(e.getPoint());
				int col = conditionTable.columnAtPoint(e.getPoint());
				if (e.getClickCount() == 1) {
					
					if (row == conditionTable.getRowCount() - 1 && col == 0) {
						((OperatoinTableModel) conditionTable.getModel())
								.addNewRow();
					} else if (col == 0)
						((OperatoinTableModel) conditionTable.getModel())
								.removeRow(row);
					
					for(GraphicSaveListener l:graphicSaveListener){
						l.onSave();
					}

				}
				if(e.getClickCount()==2){
					
					if(col==4){
						for(GraphicElementMouseListener lis:graphicListener){
							lis.mouseClicked(conditionTable.getValueAt(row, col));
						}
					}
				}
				
				

			}

		});
		JComboBox<Skill> cobo=new JComboBox<Skill>();
		cobo.setModel(new DefaultComboBoxModel<Skill>(operation.getPossibleSkills().toArray(new Skill[0])));
		cobo.setRenderer(new ListCellRenderer<Skill>(){

			@Override
			public Component getListCellRendererComponent(
					JList<? extends Skill> list, Skill value, int index,
					boolean isSelected, boolean cellHasFocus) {
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
		
		
		
		conditionTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(cobo));
		conditionTable.getColumnModel().getColumn(3).setCellEditor(new RandomNumberInTableEditor());
		conditionTable.getColumnModel().getColumn(0).setMaxWidth(40);
		conditionTable.getColumnModel().getColumn(1).setMaxWidth(180);
		//conditionTable.getColumnModel().getColumn(1).setMinWidth(180);
		conditionTable.getColumnModel().getColumn(2).setMaxWidth(100);
		conditionTable.getColumnModel().getColumn(3).setMaxWidth(200);
		conditionTable.getColumnModel().getColumn(3).setMinWidth(200);
		//conditionTable.getColumnModel().getColumn(4).setMinWidth(300);
		TableCellRenderer renderer = new TableCellRenderer() {

					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						
						switch (column) {
						case 0:
							if (row < table.getRowCount() - 1)
								return new JLabel(new ImageIcon(ImageUtilities
										.resize(this.getClass().getResource(
												"delete3.png"), 8, 8)));
							else
								return new JLabel(new ImageIcon(ImageUtilities
										.resize(this.getClass().getResource(
												"add.png"), 12, 12)));
						case 1:
							if(value==null){
								return null;
							}
							return new JLabel((String) value,JLabel.CENTER);
						case 2:
							if(value==null){
								return null;
							}
							return new JLabel(((IEntity) value).getName(),JLabel.CENTER);
						case 3:
							if(value==null){
								return null;
							}
							return new JLabel(((QuatityVolume) value).getShortString(),JLabel.CENTER);
						case 4:
							if(value==null){
								return null;
							}
							return drawRandomBar(((Activity) value).getStartTime(),((Activity) value).getEndtime());
						}
						return null;
					}
				};
				conditionTable.getColumnModel().getColumn(0)
				.setCellRenderer(renderer);
				conditionTable.getColumnModel().getColumn(1)
				.setCellRenderer(renderer);
				conditionTable.getColumnModel().getColumn(2)
				.setCellRenderer(renderer);
				conditionTable.getColumnModel().getColumn(3)
				.setCellRenderer(renderer);
				conditionTable.getColumnModel().getColumn(4)
				.setCellRenderer(renderer);
	}

	protected Component drawRandomBar(final long startTime, final long endtime) {
		JPanel comp=new JPanel(){
			
			
			@Override
			public void paintComponent(Graphics g){
				g.setColor(Color.blue);
				int h=this.getHeight();
				int w=this.getWidth();
				double scale=w/100.0;
				g.fillRect((int)(startTime*scale), 2, (int)((endtime-startTime)*scale), h-4);
			}
			
		};
		//comp.setOpaque(true);
		return comp;
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
