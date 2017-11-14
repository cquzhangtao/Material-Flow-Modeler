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
import ui.editor.util.EditorUtilities;
import ui.utilities.ImageUtilities;

import manu.factory.Material;
import manu.factory.MaterialGroup;
import manu.factory.Resource;
import manu.factory.Storage;
import manu.others.Property;
import manu.product.Activity;
import manu.product.ActivityType;
import manu.product.InvovedMaterialIn;
import manu.product.ProcessingTime;
import manu.product.ProcessingTimeLogic;

public class InvovedMaterialEditor extends JPanel implements ActionListener {

	JComboBox<ActivityType> typeCombo;
	private Activity activity;
	JTable table;
	private MouseAdapter mouseListener;
	private TableCellRenderer renderer;

	// Activity activity;

	public InvovedMaterialEditor(final Activity activity) {
		super();

		this.activity = activity;
		this.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		p.setLayout(layout);
		this.add(p, BorderLayout.NORTH);
		JLabel lable = new JLabel("Activity Type: ");
		//p.add(lable);
		typeCombo = new JComboBox<ActivityType>();
		typeCombo.setModel(new DefaultComboBoxModel<ActivityType>(ActivityType
				.values()));

		typeCombo.addActionListener(this);
		typeCombo.setFont(typeCombo.getFont().deriveFont(Font.PLAIN));
		//p.add(typeCombo);

		table = new JTable();
		table.setRowHeight(22);
		this.add(new JScrollPane(table), BorderLayout.CENTER);

		mouseListener = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					if (activity.getActivityType() == ActivityType.Normal) {
						return ;
					}
					int row = table.rowAtPoint(e.getPoint());
					int col = table.columnAtPoint(e.getPoint());
					if (row == table.getRowCount() - 1 && col == 0) {
						((MutableTableModel) table.getModel())
								.addNewRow();
					} else if (col == 0)
						((MutableTableModel) table.getModel())
								.removeRow(row);

				}

			}

		};

		renderer = new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				if (value == null)
					return new JLabel("");
	
				switch (column) {
				case 0:
					if (row < table.getRowCount() - 1) {
						JLabel label = new JLabel(new ImageIcon(
								ImageUtilities.resize(this.getClass()
										.getResource("delete1.png"), 12, 12)));
						if (activity.getActivityType() == ActivityType.Normal) {
							label.setEnabled(false);
						}
						return label;
					} else {
						JLabel label = new JLabel(new ImageIcon(
								ImageUtilities.resize(this.getClass()
										.getResource("add.png"), 12, 12)));
						if (activity.getActivityType() == ActivityType.Normal) {
							label.setEnabled(false);
						}
						return label;
					}

				case 1:
					if (value == null)
						return new JLabel("");
					return new JLabel(((IEntity) value).getName(),
							JLabel.CENTER);
				}
				if (value == null)
					return new JLabel("");
				if (activity.getActivityType() == ActivityType.Load) {
					switch (column) {
					case 2:
						
						return new JLabel(EditorUtilities.getListString((List) value),
								JLabel.CENTER);
					case 3:
					
						return new JLabel(
								((RandomVolume) value).getShortString(),
								JLabel.CENTER);
					case 4:
						return new JLabel(EditorUtilities.getListString((List) value),
								JLabel.CENTER);
					}
				} else if (activity.getActivityType() == ActivityType.Unload) {
					switch (column) {
					case 2:
						return new JLabel(
								((RandomVolume) value).getShortString(),
								JLabel.CENTER);
					case 3:
						return new JLabel(EditorUtilities.getListString((List) value),
								JLabel.CENTER);
					
					

					}
				}

				return null;
			}
		};
		typeCombo.setSelectedItem(activity.getActivityType());
		table.addMouseListener(mouseListener);
		updateTable();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == typeCombo) {
			activity.setActivityType((ActivityType) typeCombo.getSelectedItem());

			updateTable();
		}

	}

	private void updateTable() {

	
		if (activity.getActivityType() == ActivityType.Normal) {
			table.setEnabled(false);
			return;
		}
		table.setEnabled(true);
		if (activity.getActivityType() == ActivityType.Load) {
			
			table.setModel(new InvovedMaterialInTableModel(activity));
			
			
			JComboBox<Property> combo = new JComboBox<Property>();
			combo.setModel(new DefaultComboBoxModel<Property>(activity
					.getNodesWithClass(Property.class).toArray(new Property[0])));
			table.getColumnModel().getColumn(1)
					.setCellEditor(new DefaultCellEditor(combo));

			MultiSelectInTableEditor mg = new MultiSelectInTableEditor(
					activity.getNodesWithClass(MaterialGroup.class));

			table.getColumnModel().getColumn(2).setCellEditor(mg);
			RandomNumberInTableEditor vol = new RandomNumberInTableEditor();
			table.getColumnModel().getColumn(3).setCellEditor(vol);
			MultiSelectInTableEditor st = new MultiSelectInTableEditor(
					activity.getNodesWithClass(Storage.class));
			table.getColumnModel().getColumn(4).setCellEditor(st);
			table.getColumnModel().getColumn(4).setCellRenderer(renderer);
		}else if(activity.getActivityType() == ActivityType.Unload) {
			table.setModel(new InvovedMaterialOutTableModel(activity));
			//table.addMouseListener(mouseListener);
			JComboBox<Material> combo = new JComboBox<Material>();
			combo.setModel(new DefaultComboBoxModel<Material>(activity
					.getNodesWithClass(Material.class).toArray(new Material[0])));
			table.getColumnModel().getColumn(1)
					.setCellEditor(new DefaultCellEditor(combo));
			RandomNumberInTableEditor vol = new RandomNumberInTableEditor();
			table.getColumnModel().getColumn(2).setCellEditor(vol);
			MultiSelectInTableEditor st = new MultiSelectInTableEditor(
					activity.getNodesWithClass(Storage.class));
			table.getColumnModel().getColumn(3).setCellEditor(st);
		}else{
			
		}
			
			
		
		table.getColumnModel().getColumn(0).setMaxWidth(50);


		table.getColumnModel().getColumn(0).setCellRenderer(renderer);
		table.getColumnModel().getColumn(1).setCellRenderer(renderer);
		table.getColumnModel().getColumn(2).setCellRenderer(renderer);
		table.getColumnModel().getColumn(3).setCellRenderer(renderer);
	


	}


	public JComboBox<ActivityType> getTypeCombo() {
		return typeCombo;
	}

}
