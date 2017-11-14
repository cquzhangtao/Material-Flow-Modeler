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


public class EntityEditor extends JPanel{

	public EntityEditor(final Entity entity){
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
		

	}
	public void add1(Component com1,Component com2){
		Insets insets=new Insets(5, 10, 5, 0);
		GridBagConstraints c8=new GridBagConstraints(0,1,1,1,0.05,0.1,21,0,insets,0,0);
		this.add(com1,c8);
		insets=new Insets(5, 0, 5, 20);
		GridBagConstraints c9=new GridBagConstraints(1,1,1,1,0.1,0.2,10,2,insets,0,0);
		this.add(com2,c9);
	}
	public void add2(Component com1,Component com2){
		Insets insets=new Insets(5, 10, 5, 0);
		GridBagConstraints c8=new GridBagConstraints(2,1,1,1,0.05,0.1,21,0,insets,0,0);
		this.add(com1,c8);
		insets=new Insets(5, 0, 5, 20);
		GridBagConstraints c9=new GridBagConstraints(3,1,1,1,0.1,0.2,10,2,insets,0,0);
		this.add(com2,c9);
		
	}
}
