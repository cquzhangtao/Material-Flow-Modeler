package ui.editor;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import basic.entity.TreeNode;
import basic.unit.UnitSystem;
import basic.volume.QuatityVolume;

import manu.factory.Factories;
import manu.factory.Factory;
import manu.factory.Material;
import manu.factory.MaterialGroup;
import manu.factory.Materials;
import manu.factory.Resource;
import manu.factory.ResourceGroup;
import manu.factory.Resources;
import manu.factory.Storages;
import manu.factory.TransportationLines;
import manu.order.ManufactureOrder;
import manu.order.ManufactureOrders;
import manu.others.Properties;
import manu.others.Property;
import manu.others.Skill;
import manu.others.Skills;
import manu.product.Activity;
import manu.product.ActivityStartCondition;
import manu.product.ActivitiyStartLogic;
import manu.product.Operation;
import manu.product.Product;
import manu.product.Products;

public class CollectionTableModel implements TableModel, MutableTableModel {

	List<TreeNode> groups;
	List<TableModelListener> listeners;
	TreeNode resources;

	public CollectionTableModel(TreeNode resources) {
		this.groups = resources.getChildren();
		this.resources = resources;
		this.listeners = new ArrayList<TableModelListener>();
	}

	public void addNewRow() {
		if (resources instanceof Resources) {
			resources.addChild(new ResourceGroup());
		} 
		else if (resources instanceof ResourceGroup) {
			//resources.addChild(new Resource());
		}else if (resources instanceof Materials) {
			resources.addChild(new MaterialGroup());
		} else if (resources instanceof MaterialGroup) {
			resources.addChild(new Material());
		} else if (resources instanceof Products) {
			resources.addChild(new Product());
		} else if (resources instanceof Skills) {
			resources.addChild(new Skill());
		} else if (resources instanceof Properties) {
			resources.addChild(new Property(UnitSystem.Weight));
		} else if (resources instanceof ManufactureOrders) {
			resources.addChild(new ManufactureOrder());
		} else if (resources instanceof Factories) {
			resources.addChild(new Factory());
		}
		fireTableModelListener();
	}

	public void fireTableModelListener() {
		for (TableModelListener l : listeners) {
			l.tableChanged(new TableModelEvent(this));
		}
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return groups.size() + 1;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return " ";
		case 1:

			return "Entity ID";
		case 2:
			if (resources instanceof Resources)
				return "Resource Group Name";
			else if (resources instanceof ResourceGroup) {
				return "Resource Name";
			}
			else if (resources instanceof Materials) {
				return "Material Group Name";
			} else if (resources instanceof MaterialGroup) {
				return "Material Name";
			} else if (resources instanceof Products) {
				return "Product Name";
			} else if (resources instanceof Skills) {
				return "Skill Name";
			} else if (resources instanceof Properties) {
				return "Property Name";
			} else if (resources instanceof ManufactureOrders) {
				return "Order Name";
			} else if (resources instanceof Factories) {
				return "Factory Name";
			}else if (resources instanceof Storages) {
				return "Storage Name";
			}else if (resources instanceof TransportationLines) {
				return "Line Name";
			}
			break;
		case 3:
			if (resources instanceof Resources)
				return "Resource Count";
			else if (resources instanceof ResourceGroup) {
				return "";
			}
			else if (resources instanceof Materials) {
				return "Material Count";
			} else if (resources instanceof MaterialGroup) {
				return " ";
			} else if (resources instanceof Products) {
				return "";
			} else if (resources instanceof Skills) {
				return "";
			} else if (resources instanceof Properties) {
				return "";
			} else if (resources instanceof ManufactureOrders) {
				return "";
			} else if (resources instanceof Factories) {
				return "";
			}else if (resources instanceof ResourceGroup) {
				return "";
			}else if (resources instanceof Storages) {
				return "";
			}else if (resources instanceof TransportationLines) {
				return "";
			}

		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return TreeNode.class;

		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (rowIndex == groups.size() || columnIndex == 0 || columnIndex == 3) {
			return false;
		}
		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex == groups.size()) {
			if (columnIndex == 0)
				return " Add ... ";
			return null;
		}
		if (columnIndex == 0) {
			return "  -  ";
		}
		switch (columnIndex) {
		case 1:
			return groups.get(rowIndex).getId();
		case 2:
			return groups.get(rowIndex).getName();
		case 3:
			return groups.get(rowIndex);

		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (rowIndex == groups.size() || columnIndex == 0) {
			return;
		}
		switch (columnIndex) {
		case 1:
			groups.get(rowIndex).setId((String) aValue);
			break;
		case 2:
			groups.get(rowIndex).setName((String) aValue);
			break;
		case 3:

			break;
		case 4:
			// activities.get(rowIndex).setVolume( (IntegerVolume) aValue);

			break;
		}

	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		listeners.add(l);

	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		listeners.remove(l);

	}

	public void removeRow(int row) {
		groups.remove(row);
		fireTableModelListener();

	}

}
