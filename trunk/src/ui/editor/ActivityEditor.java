package ui.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXTitledPanel;

import basic.entity.IEntity;

import manu.product.Activity;

public class ActivityEditor extends ObjectEditor{

	
	protected ActivityEditor(Activity object) {
		super(object);
		// TODO Auto-generated constructor stub
		this.setLayout(new BorderLayout());
		List<Component> comps=new ArrayList<Component>();
		object.createProcessingTime();
		ProcessingTimeEditor panel2=new ProcessingTimeEditor(object);;
		ActivityStartConditionEditor panel3=new ActivityStartConditionEditor(object);
		InvovedMaterialEditor panel4=new InvovedMaterialEditor(object);
		comps.add(panel4.getTypeCombo());
		comps.add(panel2.getTypeCombo());
		comps.add(panel2.getProcessingTimeBut());
		
		JPanel panel1=new ActivityInfoEditor(object,comps);
//		Border border=BorderFactory.createEtchedBorder();
//		panel1.setBorder(BorderFactory.createTitledBorder(border, "General"));
//		panel2.setBorder(BorderFactory.createTitledBorder(border, "Processing Time"));
//		panel3.setBorder(BorderFactory.createTitledBorder(border, "Start Date&Time"));
//		panel4.setBorder(BorderFactory.createTitledBorder(border, "Materials"));

	JXTitledPanel jtp1=new JXTitledPanel("General");
	JXTitledPanel jtp2=new JXTitledPanel("Processing Time");
	JXTitledPanel jtp3=new JXTitledPanel("Start Date&Time");
	JXTitledPanel jtp4=new JXTitledPanel("Materials");
	jtp1.add(panel1);
	jtp2.add(panel2);
	jtp3.add(panel3);
	jtp4.add(panel4);
	

		// put three panels into a horizontal split pane,
		// with 2 resizeable dividers
		final JSplitPane splitTop = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		final JSplitPane splitMiddle = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		final JSplitPane splitbottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitbottom.setDividerSize(1);
		splitTop.setDividerSize(1);
		splitMiddle.setDividerSize(1);
		splitbottom.setLeftComponent(jtp3);
		splitbottom.setRightComponent(jtp4);
		splitMiddle.setTopComponent( splitTop );
		splitMiddle.setBottomComponent( splitbottom );
		splitTop.setLeftComponent( jtp1 );
		splitTop.setRightComponent( jtp2 );
	
		this.add(splitMiddle,BorderLayout.CENTER);
		
		Runnable run=new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				splitTop.setDividerLocation(0.5);
				splitMiddle.setDividerLocation(0.5);
				splitbottom.setDividerLocation(0.5);
			}
			
		};
		SwingUtilities.invokeLater(run);
		this.addComponentListener(new ComponentListener(){

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				splitTop.setDividerLocation(0.5);
				splitMiddle.setDividerLocation(0.5);
				splitbottom.setDividerLocation(0.5);
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				splitTop.setDividerLocation(0.5);
				splitMiddle.setDividerLocation(0.5);
				splitbottom.setDividerLocation(0.5);
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}});

	}

	
	
	
	
	public static ObjectEditor getInstance(Activity object){
		ActivityEditor editor=(ActivityEditor) hasInstance(object);
		if(editor==null){
			ObjectEditor ed=new ActivityEditor(object);
			editors.add(ed);
			return ed;
		}
		return editor;
	}
}
