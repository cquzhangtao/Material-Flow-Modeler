package ui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSplitPane;

import org.jdesktop.swingx.JXTitledPanel;

import ui.editor.listener.EditorListener;
import ui.editor.util.EditorUtilities;

import manu.factory.Resource;
import manu.factory.ResourceGroup;
import manu.others.Skill;
import manu.product.Activity;
import basic.entity.IEntity;
import basic.entity.TreeNode;

public class SkillEditor extends ObjectEditor{

	protected SkillEditor(final Skill object) {
		super(object);
		// TODO Auto-generated constructor stub
		this.setLayout(new BorderLayout());
		EntityEditor entityEditor=new EntityEditor(object);
		JXTitledPanel jtp1=new JXTitledPanel("General");
		JXTitledPanel jtp2=new JXTitledPanel("Preparation Activities");
		JXTitledPanel jtp3=new JXTitledPanel("Recovery Activities");
		JXTitledPanel jtp4=new JXTitledPanel("Others");
		jtp1.add(entityEditor);
//		jtp2.add(panel2);
//		jtp3.add(panel3);
//		jtp4.add(panel4);
		
		
	
	
	
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
	
	

	
	public static ObjectEditor getInstance(Skill object){
		SkillEditor editor=(SkillEditor) hasInstance(object);
		if(editor==null){
			ObjectEditor ed=new SkillEditor(object);
			editors.add(ed);
			return ed;
		}
		return editor;
	}

}
