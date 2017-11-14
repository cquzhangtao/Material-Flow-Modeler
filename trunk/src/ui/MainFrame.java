package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import manu.factory.Factories;
import manu.factory.Factory;
import manu.factory.FactoryExplore;
import manu.factory.MaterialGroup;
import manu.factory.Materials;
import manu.factory.Resource;
import manu.factory.ResourceGroup;
import manu.factory.Resources;
import manu.factory.Storages;
import manu.factory.TransportationLines;
import manu.order.ManufactureOrders;
import manu.others.Properties;
import manu.others.Skill;
import manu.others.Skills;
import manu.product.Activity;
import manu.product.Operation;
import manu.product.Product;
import manu.product.Products;

import ui.editor.ActivityEditor;
import ui.editor.FactoryLayoutEditor;
import ui.editor.ObjectEditor;
import ui.editor.OperationEditor;
import ui.editor.OperationNetEditor;
import ui.editor.CollectionEditor;
import ui.editor.ResourceEditor;
import ui.editor.SkillEditor;
import ui.editor.component.ClosableTabbedPane;
import ui.editor.component.FactoryTree;
import ui.editor.component.VerticalTabbedPane;
import ui.editor.listener.DefaultTreeDoubleClickedListener;
import ui.editor.listener.MenuAndToolBarActionListener;
import ui.editor.listener.TabbedPaneChangedListener;
import ui.utilities.ImageUtilities;

public class MainFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,JButton> buttons;
	public MainFrame(){
		setTitle("Material Flow Simulation");
		setSize(new Dimension(1000,700));
		FactoryTree factoryTree=new FactoryTree();
		FactoryExplore factoryExplore=new FactoryExplore();
		buttons=new HashMap<String,JButton>();
		ClosableTabbedPane rightTabbedPane=new ClosableTabbedPane();
		MenuAndToolBarActionListener mbListener=new MenuAndToolBarActionListener(factoryExplore,factoryTree,buttons,rightTabbedPane);
		setJMenuBar(createMenuBar(mbListener));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container container=this.getContentPane();
		container.setLayout(new BorderLayout());
		container.add(createToolBar(mbListener),BorderLayout.NORTH);
		JPanel mainPanel=new JPanel();
		
		mainPanel.setLayout(new BorderLayout());
		container.add(mainPanel,BorderLayout.CENTER);
		VerticalTabbedPane leftTabbedPane=new VerticalTabbedPane();
	
		JSplitPane splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftTabbedPane,rightTabbedPane);
		splitPane.setDividerLocation(300);
		mainPanel.add(splitPane,BorderLayout.CENTER);
		JPanel fxPanel=new JPanel(new BorderLayout());
		leftTabbedPane.addTab("Factory Explorer", fxPanel);
		leftTabbedPane.addTab("Simulation", new JPanel());
		rightTabbedPane.addTab("Welcome", new JPanel());
		fxPanel.add(new JScrollPane(factoryTree),BorderLayout.CENTER);
		
		factoryTree.setBorder(BorderFactory.createCompoundBorder(factoryTree.getBorder(), BorderFactory.createEmptyBorder(10, 10, 0, 10)));
		factoryTree.addTreeDoubleClickedListener(new DefaultTreeDoubleClickedListener(createEditorMap(),rightTabbedPane,factoryTree));
		rightTabbedPane.addTabbedPaneChangedListener(new TabbedPaneChangedListener(){

			@Override
			public void onTabClosed(Component component) {
				((ObjectEditor)component).destoryInstance();
				
			}});
		leftTabbedPane.setTabPlacement(JTabbedPane.LEFT);
		//leftTabbedPane.sett
	}
	
	private Map<Class,Class> createEditorMap(){
		Map<Class,Class> map=new HashMap<Class,Class>();
		map.put(Factory.class, FactoryLayoutEditor.class);
		map.put(Product.class,OperationNetEditor.class);
		map.put(Operation.class,OperationEditor.class);
		map.put(Activity.class,ActivityEditor.class);
		map.put(Resources.class,CollectionEditor.class);
		map.put(Materials.class,CollectionEditor.class);
		
		map.put(MaterialGroup.class,CollectionEditor.class);
		map.put(Products.class,CollectionEditor.class);
		map.put(ManufactureOrders.class,CollectionEditor.class);
		map.put(Skills.class,CollectionEditor.class);
		map.put(Properties.class,CollectionEditor.class);
		map.put(Factories.class,CollectionEditor.class);
		
		map.put(ResourceGroup.class,CollectionEditor.class);
		map.put(Storages.class,CollectionEditor.class);
		map.put(TransportationLines.class,CollectionEditor.class);
		map.put(Resource.class,ResourceEditor.class);
		map.put(Skill.class,SkillEditor.class);
		
		return map;
	}
	
	private JMenuBar createMenuBar(MenuAndToolBarActionListener mbListener){
		JMenuBar menuBar=new JMenuBar();
		JMenu file=new JMenu("File");
		menuBar.add(file);
		JMenu view=new JMenu("View");
		menuBar.add(view);
		JMenu factory=new JMenu("Factory");
		menuBar.add(factory);
		JMenu simulation=new JMenu("Simulation");
		menuBar.add(simulation);
		JMenu tool=new JMenu("Tool");
		menuBar.add(tool);
		JMenu window=new JMenu("Window");
		menuBar.add(window);
		JMenu help=new JMenu("Help");
		menuBar.add(help);
		return menuBar;
		
	}
	private JToolBar createToolBar(MenuAndToolBarActionListener mbListener){
		
		JToolBar toolBar=new JToolBar();

		addButton(toolBar,"Open","open.png",mbListener,true);
		addButton(toolBar,"New","newfile.png",mbListener,true);
		addButton(toolBar,"Close","close.png",mbListener,false);
		addButton(toolBar,"Save","Save.png",mbListener,false);
		addButton(toolBar,"Save As","Saveas.png",mbListener,false);
		addButton(toolBar,"Add Factory","addFactory.png",mbListener,false);
		addButton(toolBar,"Add Product","addProduct.png",mbListener,false);
		addButton(toolBar,"Add Order","addOrder1.png",mbListener,false);
		addButton(toolBar,"Add Skill","addskill.png",mbListener,false);
		addButton(toolBar,"Add Property","addproperty.png",mbListener,false);
		addButton(toolBar,"Delete","delete.png",mbListener,false);
		return toolBar;
	}
	private JButton addButton(JToolBar bar, String command, String image,
			ActionListener listener,boolean enable) {

		JButton button = new JButton();
		buttons.put(command, button);
		// button.setMargin(new Insets(5,18,5,18));
		button.setToolTipText(command);
		button.setActionCommand(command);
		button.addActionListener(listener);
		button.setEnabled(enable);
		bar.add(button);

		BufferedImage bg = null;
		try {
			bg = ImageUtilities.getScaledInstance(
					ImageIO.read(this.getClass().getResource(
							 image)), 22, 22);
			button.setIcon(new ImageIcon(bg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return button;

	}

}
