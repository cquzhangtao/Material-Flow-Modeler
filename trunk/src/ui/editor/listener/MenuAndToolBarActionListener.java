package ui.editor.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;

import basic.unit.UnitSystem;

import ui.editor.component.ClosableTabbedPane;
import ui.editor.component.FactoryTreeMode;

import manu.factory.Factory;
import manu.factory.FactoryExplore;
import manu.order.ManufactureOrder;
import manu.others.Property;
import manu.others.Skill;
import manu.product.Product;

public class MenuAndToolBarActionListener implements ActionListener {

	JTree factoryTree;
	FactoryExplore factoryExplore;
	Map<String, JButton> buttons;
	File filePath = null;
	boolean newFile = false;
	ClosableTabbedPane rightTabbedPane;
	public MenuAndToolBarActionListener(FactoryExplore factoryExplore,
			JTree factoryTree, Map<String, JButton> buttons, ClosableTabbedPane rightTabbedPane) {
		this.factoryTree = factoryTree;
		this.factoryExplore = factoryExplore;
		this.buttons = buttons;
		this.rightTabbedPane=rightTabbedPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("New")) {
			if (filePath != null || newFile) {
				if (JOptionPane.showOptionDialog(null, "Do you want to save?",
						"Save", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null) == JOptionPane.YES_OPTION) {
					saveFile();
				}
			}
			factoryTree.removeAll();
			removeWindows();
			factoryTree.setModel(new FactoryTreeMode(factoryExplore));
			enableButton(true);
			newFile = true;

		} else if (e.getActionCommand().equals("Save")) {
			saveFile();
		} else if (e.getActionCommand().equals("Save As")) {
			JFileChooser saveFile = new JFileChooser();
			saveFile.setFileFilter(new FileNameExtensionFilter("Simulation Model (*.mod)", "mod"));
			if (saveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				filePath = saveFile.getSelectedFile();
				String file_name = filePath.toString();
				if (!file_name.endsWith(".mod"))
				    file_name += ".mod";
				saveFile(new File(file_name));
				newFile = false;
			}
		} else if (e.getActionCommand().equals("Close")) {
			if (JOptionPane.showOptionDialog(null, "Do you want to save?",
					"Save", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, null) == JOptionPane.YES_OPTION) {
				saveFile();
			}
			factoryTree.removeAll();
			removeWindows();
			newFile = false;
			filePath=null;
			factoryTree.setModel(null);
			enableButton(false);
		}
		
		else if (e.getActionCommand().equals("Open")) {
			if (filePath != null || newFile) {
				if (JOptionPane.showOptionDialog(null, "Do you want to save?",
						"Save", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null) == JOptionPane.YES_OPTION) {
					saveFile();
				}
			}
			JFileChooser openFile = new JFileChooser();
			openFile.setFileFilter(new FileNameExtensionFilter("Simulation Model  (*.mod)", "mod"));

			if (openFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				removeWindows();
				filePath = openFile.getSelectedFile();
				openFile(filePath);
				enableButton(true);
			}

		}

		else if (e.getActionCommand().equals("Add Factory")) {
			Factory factory = new Factory();
			factoryExplore.addFactory(factory);
			// factoryTree.getModel().valueForPathChanged(factory.getTreePath(),
			// factory);
			factoryTree.setSelectionPath(factory.getTreePath());
			factoryTree.expandPath(factory.getTreePath());
			factoryTree.updateUI();

		} else if (e.getActionCommand().equals("Add Product")) {
			Product product = new Product();
			factoryExplore.addProduct(product);
			// factoryTree.getModel().valueForPathChanged(product.getTreePath(),
			// product);
			factoryTree.setSelectionPath(product.getTreePath());
			factoryTree.expandPath(product.getTreePath());
			factoryTree.updateUI();

		} else if (e.getActionCommand().equals("Add Order")) {
			ManufactureOrder order = new ManufactureOrder();
			factoryExplore.addOrder(order);
			// factoryTree.getModel().valueForPathChanged(order.getTreePath(),
			// order);
			factoryTree.setSelectionPath(order.getTreePath());
			factoryTree.updateUI();

		} else if (e.getActionCommand().equals("Add Skill")) {
			Skill skill = new Skill();
			factoryExplore.addSkill(skill);
			// factoryTree.getModel().valueForPathChanged(order.getTreePath(),
			// order);
			factoryTree.setSelectionPath(skill.getTreePath());
			factoryTree.updateUI();

		} else if (e.getActionCommand().equals("Add Property")) {
			Property property = new Property(UnitSystem.Weight);
			factoryExplore.addProperty(property);
			// factoryTree.getModel().valueForPathChanged(order.getTreePath(),
			// order);
			factoryTree.setSelectionPath(property.getTreePath());
			factoryTree.updateUI();

		}

	}

	private void enableButton(boolean b) {
		buttons.get("Add Factory").setEnabled(b);
		buttons.get("Add Product").setEnabled(b);
		buttons.get("Add Order").setEnabled(b);
		buttons.get("Delete").setEnabled(b);
		buttons.get("Save").setEnabled(b);
		buttons.get("Add Skill").setEnabled(b);
		buttons.get("Add Property").setEnabled(b);
		buttons.get("Save As").setEnabled(b);
		buttons.get("Close").setEnabled(b);
	}

	private void saveFile() {
		if (filePath == null) {
			JFileChooser saveFile = new JFileChooser();
			saveFile.setFileFilter(new FileNameExtensionFilter("Simulation Model (*.mod)", "mod"));
			if (saveFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				filePath = saveFile.getSelectedFile();
				String file_name = filePath.toString();
				if (!file_name.endsWith(".mod"))
				    file_name += ".mod";
				saveFile(new File(file_name));
				newFile = false;
			}

		} else {
			saveFile(filePath);
			newFile = false;
		}
	}

	private void saveFile(File filePath) {

		ObjectOutputStream objectOutputStream;
		try {
			FileOutputStream outStream = new FileOutputStream(filePath);
			objectOutputStream = new ObjectOutputStream(outStream);
			objectOutputStream.writeObject(factoryExplore);
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void openFile(File filePath){
		try {
		FileInputStream freader = new FileInputStream(filePath);
		ObjectInputStream objectInputStream = new ObjectInputStream(
				freader);
		factoryExplore=(FactoryExplore) objectInputStream
		.readObject();
		objectInputStream.close();
		factoryTree.setModel(new FactoryTreeMode(factoryExplore));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void removeWindows(){
		rightTabbedPane.removeAll();
	}

}
