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
import basic.volume.TimeVolume;

import ui.utilities.ImageUtilities;

import manu.factory.Resource;
import manu.others.Skill;
import manu.product.Activity;
import manu.product.ProcessingTime;
import manu.product.ProcessingTimeLogic;

public class ProcessingTimeEditor extends JPanel implements ActionListener {
	private JButton processingTimeBut;
	JComboBox<ProcessingTimeLogic> typeCombo;
	private ProcessingTime processTime;
	JTable table;
	private MouseAdapter mouseListener;
	private TableCellRenderer renderer;
	private Activity activity;

	public ProcessingTimeEditor(Activity activity) {
		super();
		this.activity=activity;
		this.processTime = activity.getProcessingTime();
		this.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		p.setLayout(layout);
		this.add(p, BorderLayout.NORTH);
		JLabel lable = new JLabel("Type");
		//p.add(lable);
		typeCombo = new JComboBox<ProcessingTimeLogic>();
		typeCombo.setModel(new DefaultComboBoxModel<ProcessingTimeLogic>(
				ProcessingTimeLogic.values()));
		
		typeCombo.addActionListener(this);
		typeCombo.setFont(typeCombo.getFont().deriveFont(Font.PLAIN));

		//p.add(typeCombo);
		lable = new JLabel("Processing Time");
		//p.add(lable);
		processingTimeBut = new JButton(processTime.getProcessingTime().getShortString());
		processingTimeBut.setOpaque(true);
		processingTimeBut.setBackground(Color.white);
		processingTimeBut.setFont(processingTimeBut.getFont().deriveFont(Font.PLAIN));
		//p.add(ptext);
		processingTimeBut.addActionListener(this);

		table = new JTable();
		 table.setRowHeight(22);
		this.add(new JScrollPane(table), BorderLayout.CENTER);

		mouseListener = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					int row = table.rowAtPoint(e.getPoint());
					int col = table.columnAtPoint(e.getPoint());
					if (row == table.getRowCount() - 1 && col == 0) {
						((ProcessingTimeTableModel) table.getModel())
								.addNewRow();
					} else if (col == 0)
						((ProcessingTimeTableModel) table.getModel())
								.removeRow(row);

				}

			}

		};


		renderer = new TableCellRenderer() {

					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						if(value==null)
							return new JLabel("");
						if (processTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resources_Independent) {
							return null;
						} else if (processTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resource_Dependent||processTime.getProcessingTimeLogic() == ProcessingTimeLogic.Proportion_Dependent) {
							switch (column) {
							case 0:
								if (row < table.getRowCount() - 1) {
									JLabel label = new JLabel(
											new ImageIcon(
													ImageUtilities
															.resize(this
																	.getClass()
																	.getResource(
																			"delete1.png"),
																	12, 12)));
									label.setEnabled(false);
									return label;
								} else {
									JLabel label = new JLabel(new ImageIcon(
											ImageUtilities.resize(
													this.getClass()
															.getResource(
																	"add.png"),
													12, 12)));
									label.setEnabled(false);
									return label;
								}
								
							case 1:
								if(value==null)
									return new JLabel("");
								return new JLabel(((IEntity) value).getName(),JLabel.CENTER);
							case 2:
								return new JLabel(String.valueOf((Integer)value),JLabel.CENTER);
							case 3:
								if(value==null)
									return new JLabel("");
								return new JLabel(((TimeVolume)value).getShortString(),JLabel.CENTER);
							}
						} else if (processTime.getProcessingTimeLogic() == ProcessingTimeLogic.Amount_Dependent) {
							switch (column) {
							case 0:
								if (row < table.getRowCount() - 1)
									return new JLabel(
											new ImageIcon(
													ImageUtilities
															.resize(this
																	.getClass()
																	.getResource(
																			"delete1.png"),
																	12, 12)));
								else
									return new JLabel(new ImageIcon(
											ImageUtilities.resize(
													this.getClass()
															.getResource(
																	"add.png"),
													12, 12)));
							case 1:
								if(value==null){
									return new JLabel("");
								}
								return new JLabel(((IEntity) value).getName(),JLabel.CENTER);
							case 2:
								return new JLabel(String.valueOf((Integer)value),JLabel.CENTER);
							case 3:
								if(value==null)
									return new JLabel("");
								return new JLabel(((TimeVolume)value).getShortString(),JLabel.CENTER);
							}
						} else {
							return null;
						}

						return null;
					}
				};
				
				updateTable();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == typeCombo) {
			processTime.setProcessingTimeLogic((ProcessingTimeLogic) typeCombo
					.getSelectedItem());
			
			updateTable();
		} else if (e.getSource() == processingTimeBut) {
			RandomVolumePopupEditor dialog = new RandomVolumePopupEditor(
					processTime.getProcessingTime());
			Point p = processingTimeBut.getLocationOnScreen();
			dialog.setLocation(p.x, p.y + processingTimeBut.getSize().height);

			dialog.setVisible(true);
			processingTimeBut.setText(processTime.getProcessingTime().getShortString());
		}

	}

	private void updateTable() {
		processingTimeBut.setText(processTime.getProcessingTime().getShortString());
		typeCombo.setSelectedItem(processTime.getProcessingTimeLogic());
		table.setModel(new ProcessingTimeTableModel(processTime));
		 table.getColumnModel().getColumn(0).setMaxWidth(50);
//		table.getColumnModel().getColumn(1).setMaxWidth(180);
//		table.getColumnModel().getColumn(1).setMinWidth(180);
//		table.getColumnModel().getColumn(2).setMaxWidth(100);
//		table.getColumnModel().getColumn(2).setMinWidth(100);
		if (processTime.getProcessingTimeLogic() == ProcessingTimeLogic.Amount_Dependent) {
			table.addMouseListener(mouseListener);
		} else {
			table.removeMouseListener(mouseListener);
		}
		if (processTime.getProcessingTimeLogic() == ProcessingTimeLogic.Resources_Independent){
			processingTimeBut.setEnabled(true);
		}else{
			processingTimeBut.setEnabled(false);
		}
		
		table.getColumnModel().getColumn(0)
		.setCellRenderer(renderer);
		table.getColumnModel().getColumn(1)
		.setCellRenderer(renderer);
		table.getColumnModel().getColumn(2)
		.setCellRenderer(renderer);
		table.getColumnModel().getColumn(3)
		.setCellRenderer(renderer);
		table.getColumnModel().getColumn(3).setCellEditor(new RandomNumberInTableEditor());
		JComboBox<Resource>combo=new JComboBox<Resource>();
		
		combo.setModel(new DefaultComboBoxModel<Resource>(activity.getResourcesWithSkill().toArray(new Resource[0])));
		table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(combo));
	}
	


	public JComboBox<ProcessingTimeLogic> getTypeCombo() {
		return typeCombo;
	}

	public JButton getProcessingTimeBut() {
		return processingTimeBut;
	}

}
