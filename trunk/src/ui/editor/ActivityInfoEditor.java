package ui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridBagLayoutInfo;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import manu.others.Skill;
import manu.product.Activity;

import org.jdesktop.swingx.JXTitledPanel;

import basic.entity.Entity;

import ui.editor.component.RoundJTextField;


public class ActivityInfoEditor extends JPanel{

	public ActivityInfoEditor(final Activity entity,List<Component> components){
		this.setLayout(new GridBagLayout());
		
//		public GridBagConstraints(int gridx, int gridy,
//                int gridwidth, int gridheight,
//                double weightx, double weighty,
//                int anchor, int fill,
//                Insets insets, int ipadx, int ipady) 
		int col=6;
		//GridBagConstraints.LINE_START
		double colwidth=1.0/col;
		Insets insets=new Insets(5, 10, 5, 0);
		Insets insets1=new Insets(5, 0, 5, 20);
		//GridBagConstraints c1=new GridBagConstraints(0,0,1,1,0.05,0.3,10,2,insets,0,0);
		//GridBagConstraints c2=new GridBagConstraints(1,0,3,1,0.15,0.3,10,2,insets,0,0);
		GridBagConstraints c3=new GridBagConstraints(0,0,1,1,0.05,0.1,21,0,insets,0,0);
		GridBagConstraints c4=new GridBagConstraints(1,0,1,1,0.1,0.2,10,2,insets1,0,0);
		GridBagConstraints c5=new GridBagConstraints(2,0,1,1,0.05,0.1,21,0,insets,0,0);
		GridBagConstraints c6=new GridBagConstraints(3,0,1,1,0.1,0.2,10,2,insets1,0,0);
	
		GridBagConstraints c7=new GridBagConstraints(4,0,1,1,0.05,0.1,22,0,insets,0,0);
		GridBagConstraints c8=new GridBagConstraints(5,0,2,2,0.65,1,10,1,insets1,0,0);
		GridBagConstraints c9=new GridBagConstraints(0,1,1,1,0.05,0.1,21,0,insets,0,0);
		GridBagConstraints c10=new GridBagConstraints(1,1,1,1,0.1,0.2,10,2,insets1,0,0);
		GridBagConstraints c11=new GridBagConstraints(2,1,1,1,0.05,0.1,21,0,insets,0,0);
		GridBagConstraints c12=new GridBagConstraints(3,1,2,1,0.1,0.2,10,2,insets1,0,0);
		GridBagConstraints c13=new GridBagConstraints(0,2,1,1,0.05,0.1,21,0,insets,0,0);
		GridBagConstraints c14=new GridBagConstraints(1,2,1,1,0.1,0.2,10,2,insets1,0,0);
		GridBagConstraints c15=new GridBagConstraints(2,2,1,1,0.05,0.1,21,0,insets,0,0);
		GridBagConstraints c16=new GridBagConstraints(3,2,2,1,0.1,0.2,10,2,insets1,0,0);
		GridBagConstraints c17=new GridBagConstraints(5,2,1,1,0.05,0.1,21,0,insets,0,0);
		GridBagConstraints c18=new GridBagConstraints(6,2,1,1,0.1,0.2,10,2,insets1,0,0);
		//GridBagConstraints c9=new GridBagConstraints(0,1,1,1,0.05,0.5,10,2,insets,0,0);
		//this.add(new JLabel("UUID : "),c1);
		//this.add(new JLabel(entity.getUUID()),c2);
		this.add(new JLabel("ID : "),c3);
		JTextField id = new JTextField();
		id.setHorizontalAlignment(JTextField.CENTER);
		id.setFont(id.getFont().deriveFont(15f));
		id.setFont(id.getFont().deriveFont(Font.BOLD));
		id.setForeground(Color.blue);
		id.setText(entity.getId());
		this.add(id,c4);
		this.add(new JLabel("Name : "),c5);
		JTextField txt = new JTextField();
		txt.setText(entity.getName());
		txt.setHorizontalAlignment(JTextField.CENTER);
		txt.setFont(txt.getFont().deriveFont(15f));
		txt.setFont(txt.getFont().deriveFont(Font.BOLD));
		txt.setForeground(Color.blue);
		this.add(txt,c6);
	
		this.add(new JLabel("Description : "),c7);
		this.add(new JScrollPane(new JTextArea(entity.getDescription())),c8);
		this.add(new JLabel("Skill:"),c9);
		final JComboBox<Skill> comboSkill=new JComboBox<Skill>(new DefaultComboBoxModel<Skill>(entity.getNodesWithClass(Skill.class).toArray(new Skill[0])));
		comboSkill.setSelectedItem(entity.getSkill());
		this.add(comboSkill,c10);
		this.add(new JLabel("Amount:"),c11);
		final JButton amountText=new JButton(entity.getVolume().getShortString());
		amountText.setOpaque(true);
		amountText.setBackground(Color.white);
		amountText.setFont(amountText.getFont().deriveFont(Font.PLAIN));
		this.add(amountText,c12);
		this.add(new JLabel("Activity Type:"),c13);
		this.add(components.get(0),c14);
		this.add(new JLabel("Time Type:"),c15);
		this.add(components.get(1),c16);
		this.add(new JLabel("Processing Time:"),c17);
		this.add(components.get(2),c18);
		
		comboSkill.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				entity.setSkill((Skill) comboSkill.getSelectedItem());
				entity.createProcessingTime();
			}});
		
		amountText.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				RandomVolumePopupEditor dialog = new RandomVolumePopupEditor(
						entity.getVolume());
				Point p = amountText.getLocationOnScreen();
				dialog.setLocation(p.x, p.y + amountText.getSize().height);

				dialog.setVisible(true);
				amountText.setText(entity.getVolume().getShortString());
			}});
	}
}
