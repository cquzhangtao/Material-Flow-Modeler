package ui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.NumberFormatter;

import ui.utilities.ImageUtilities;

import basic.Distribution.Distribution;
import basic.Distribution.DistributionParameterEnum;
import basic.entity.IEntity;
import basic.unit.IUnit;
import basic.volume.RandomVolume;

public class RandomNumberEditor<T extends IUnit> extends JPanel {
	JComboBox<T> unit;
	JComboBox<Distribution> distri ;
	JFormattedTextField seed;
	RandomVolume<T> volume;
	public RandomNumberEditor(RandomVolume<T> volume1) {
		this.volume=volume1;
		if(volume1.getUnit()==null){
			
		}
		this.setLayout(null);
		
		this.setBorder(BorderFactory.createEtchedBorder());
		JPanel p=new JPanel();
		p.setBounds(5, 5, 210, 100);
		p.setLayout(null);
		p.setBorder(BorderFactory.createEtchedBorder());
		this.add(p);
		JLabel label = new JLabel("Unit");
		label.setBounds(10, 10, 100, 22);
		p.add(label);
		label = new JLabel("Distribution");
		label.setBounds(10, 40, 100, 22);
		p.add(label);
		label = new JLabel("Seed");
		label.setBounds(10, 70, 100, 22);
		p.add(label);
		 unit = new JComboBox<T>();
		unit.setBounds(100, 10, 100, 22);
		unit.setModel(new DefaultComboBoxModel<T>((T[]) volume1.getUnit()
				.getAllUnits()));
		unit.setSelectedItem(volume1.getUnit());
		unit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				volume.setUnit((T) unit.getSelectedItem());
			}});
		p.add(unit);
		distri = new JComboBox<Distribution>();
		distri.setBounds(100, 40, 100, 22);
		distri.setModel(new DefaultComboBoxModel<Distribution>(Distribution
				.values()));
		distri.setSelectedItem(volume1.getRandomGenerator().getDistribution());
		distri.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				volume.getRandomGenerator().setDistribution((Distribution) distri.getSelectedItem());
				
			}});
		p.add(distri);
		 seed = new JFormattedTextField(new Integer(volume1
				.getRandomGenerator().getSeed()));
		seed.setBounds(100, 70, 100, 22);
		p.add(seed);
	
		seed.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				Runnable ru=new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						volume.getRandomGenerator().setSeed(((Number)seed.getValue()).intValue());	
					}
					
				};
				SwingUtilities.invokeLater(ru);
						}});

		final JTable conditionTable = new JTable();
		conditionTable.setModel(new DistruibutionTableMode(volume1
				.getRandomGenerator().getParameters()));
		JScrollPane sp = new JScrollPane(conditionTable);
		sp.setBounds(5, 110, 210, 150);
		this.add(sp);
		conditionTable.setRowHeight(22);
		conditionTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		conditionTable.setDefaultRenderer(DistributionParameterEnum.class, centerRenderer);
		conditionTable.setDefaultRenderer(Double.class, centerRenderer);
		conditionTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					int row = conditionTable.rowAtPoint(e.getPoint());
					int col = conditionTable.columnAtPoint(e.getPoint());
					if (row == conditionTable.getRowCount() - 1 && col == 0) {
						((DistruibutionTableMode) conditionTable.getModel())
								.addNewRow();
					} else if (col == 0)
						((DistruibutionTableMode) conditionTable.getModel())
								.removeRow(row);

				}

			}

		});
		JComboBox<DistributionParameterEnum> cobo=new JComboBox<DistributionParameterEnum>();
		cobo.setModel(new DefaultComboBoxModel<DistributionParameterEnum>(DistributionParameterEnum.values()));
		conditionTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cobo));
		conditionTable.getColumnModel().getColumn(0).setMaxWidth(30);
		conditionTable.getColumnModel().getColumn(0)
				.setCellRenderer(new TableCellRenderer() {

					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						// TODO Auto-generated method stub
						switch (column) {
						case 0:
							if (row < table.getRowCount() - 1)
								return new JLabel(new ImageIcon(ImageUtilities
										.resize(this.getClass().getResource(
												"delete.png"), 12, 12)));
							else
								return new JLabel(new ImageIcon(ImageUtilities
										.resize(this.getClass().getResource(
												"add.png"), 12, 12)));
						case 1:
							return new JLabel(((IEntity) value).getName());
						case 2:
							return null;
						}
						return null;
					}
				});
		
	}
	

}
