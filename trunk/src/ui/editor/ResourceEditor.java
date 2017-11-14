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

public class ResourceEditor extends ObjectEditor{

	protected ResourceEditor(final Resource object) {
		super(object);
		// TODO Auto-generated constructor stub
		this.setLayout(new BorderLayout());
		EntityEditor entityEditor=new EntityEditor(object);
		JXTitledPanel jtp1=new JXTitledPanel("General");
		JXTitledPanel jtp2=new JXTitledPanel("Available Times");
		JXTitledPanel jtp3=new JXTitledPanel("Unavailable Times");
		JXTitledPanel jtp4=new JXTitledPanel("Batch Processing");
		jtp1.add(entityEditor);
//		jtp2.add(panel2);
//		jtp3.add(panel3);
//		jtp4.add(panel4);
		
		String str=EditorUtilities.getListString(object.getSkills());
		if(str.isEmpty()){
			str="Select...";
		}
	final JButton skillBut=new JButton(str);
	skillBut.setOpaque(true);
	skillBut.setBackground(Color.white);
	entityEditor.add1(new JLabel("Skills:"),skillBut);
	final JComboBox<ResourceGroup> groupCombo=new JComboBox<ResourceGroup> ();
	if(object.getParent()!=null){
		groupCombo.setModel(new DefaultComboBoxModel(object.getParent().getParent().getChildren().toArray(new ResourceGroup[0])));
		groupCombo.setSelectedItem(object.getParent());
	}
	
	final JButton groupBut=new JButton(str);
	groupBut.setOpaque(true);
	groupBut.setBackground(Color.white);
	
	entityEditor.add2(new JLabel("Group:"),groupCombo);
	//this.add(skillBut,BorderLayout.NORTH);
	
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

	skillBut.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			MultiSelectPopupEditor<Skill> skills=new MultiSelectPopupEditor<Skill>(TreeNode2Skill(object.getNodesWithClass(Skill.class)),object.getSkills());

			Point p = skillBut.getLocationOnScreen();
			skills.setLocation(p.x, p.y + skillBut.getSize().height);
			skills.pack();
			skills.setSize(skillBut.getWidth(), skills.getHeight());
			skills.setVisible(true);
			String str=EditorUtilities.getListString(object.getSkills());
			if(str.isEmpty()){
				str="Select...";
			}
			skillBut.setText(str);
			
		}});
	
	groupCombo.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
		
			object.setParent((TreeNode) groupCombo.getSelectedItem());
			for (EditorListener l : getEditorListener()) {
				l.onChanged(object);
				
			}
			
		}});
	
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
	
	
	private List<Skill> TreeNode2Skill(List<TreeNode> data){
		List<Skill> skills=new ArrayList<Skill>();
		for(TreeNode node:data){
			skills.add((Skill) node);
		}
		return skills;
	}
	
	public static ObjectEditor getInstance(Resource object){
		ResourceEditor editor=(ResourceEditor) hasInstance(object);
		if(editor==null){
			ObjectEditor ed=new ResourceEditor(object);
			editors.add(ed);
			return ed;
		}
		return editor;
	}

}
