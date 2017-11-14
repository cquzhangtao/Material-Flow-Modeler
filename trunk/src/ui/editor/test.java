package ui.editor;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;

import org.jdesktop.swingx.JXTitledPanel;

import ui.editor.component.JComboCheckBox;


import basic.entity.Entity;
import basic.unit.TimeUnitEnum;
import basic.unit.UnitEnum;
import basic.volume.RandomVolume;




import manu.others.Skills;
import manu.product.Activity;
import manu.product.Operation;
import manu.product.ProcessingTime;

public class test {
	public static void main(String []arg0){
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		//frame.add(new OperationGanntChartPanel(new Operation()));
		RandomVolume<TimeUnitEnum> a = new RandomVolume<TimeUnitEnum>();
		a.setUnit(TimeUnitEnum.Day);
		//frame.add(new RandomNumberEditor<TimeUnitEnum>(a));
		//frame.setSize(800, 600);
		
		//JXTitledPanel jxtp = new JXTitledPanel("aaaaaaaaa");
		ActivityInfoEditor bbb = new ActivityInfoEditor((Activity) new Entity(),null);
		//bbb.setTitle("aaaaaa");
		//frame.add(bbb);
		//JComboCheckBox coboStroge=new JComboCheckBox(new DefaultComboBoxModel(UnitEnum.values()));
		frame.add(new InvovedMaterialEditor(new Activity()));
		frame.setVisible(true);
		frame.pack();
		
	}
}
